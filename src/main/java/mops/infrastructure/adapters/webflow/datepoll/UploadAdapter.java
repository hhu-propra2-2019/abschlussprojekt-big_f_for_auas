package mops.infrastructure.adapters.webflow.datepoll;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.ConfigDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.DatePollDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.EntriesDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.EntryDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.MetaInfDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.UploadDto;
import mops.infrastructure.adapters.webflow.dtos.PublicationDto;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public final class UploadAdapter {

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);

    public UploadDto initializeDto() {
        return new UploadDto();
    }

    // Die ganze Klasse ist wenig performant, was aber nicht dramatisch ist,
    // denn insgesamt ist der Zeitverlust sehr erträglich und die Methode wird maximal einmal pro Umfrage aufgerufen.
    public boolean processPollFile(MultipartFile pollFile,
                                   MessageContext context,
                                   DatePollDto datePollDto) {
        try (InputStream pollFileInputStream = pollFile.getInputStream();
             InputStream metaInfStream = pollFile.getInputStream();
             InputStream configStream = pollFile.getInputStream();
             InputStream publicationStream = pollFile.getInputStream();) {

            JsonNode node = mapper.readTree(pollFileInputStream);

            datePollDto.setMetaInfDto(mapper.readValue(metaInfStream, MetaInfDto.class));
            datePollDto.setConfigDto(mapper.readValue(configStream, ConfigDto.class));

            // Sicher nicht die effizienteste Lösung, aber es funktioniert
            EntryDto[] entryDtos =  mapper.readValue(node.get("entries").toString(), EntryDto[].class);
            EntriesDto entriesDto = new EntriesDto();
            entriesDto.setEntries(Arrays.stream(entryDtos).collect(Collectors.toSet()));
            if (entryDtos.length > 0) {
                entriesDto.setProposedEntry(entryDtos[0]);
            }
            datePollDto.setEntriesDto(entriesDto);

            //PublicationDto publicationDto = mapper.readValue(publicationStream, PublicationDto.class);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
