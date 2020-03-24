package mops.infrastructure.adapters.webflow.datepoll;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.ConfigDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.ConfirmationDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.MetaInfDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.UploadDto;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public final class UploadAdapter {

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public UploadDto initializeDto() {
        return new UploadDto();
    }

    public boolean processPollFile(MultipartFile pollFile,
                                   MessageContext context,
                                   ConfirmationDto confirmationDto) {
        try (InputStream metaInfStream = pollFile.getInputStream();
             InputStream configStream = pollFile.getInputStream();
             InputStream entriesStream = pollFile.getInputStream();
             InputStream publicationStream = pollFile.getInputStream();) {

            confirmationDto.setMetaInfDto(mapper.readValue(metaInfStream, MetaInfDto.class));
            confirmationDto.setConfigDto(mapper.readValue(configStream, ConfigDto.class));
            //EntriesDto entriesDto = mapper.readValue(entriesStream, EntriesDto.class);
            //PublicationDto publicationDto = mapper.readValue(publicationStream, PublicationDto.class);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
