package mops.infrastructure.adapters.webflow.datepoll;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mops.infrastructure.adapters.webflow.ErrorMessageHelper;
import mops.infrastructure.adapters.webflow.PublicationAdapter;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.ConfigDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.DatePollDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.EntriesDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.EntryDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.MetaInfDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.UploadDto;
import mops.infrastructure.adapters.webflow.dtos.PublicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@PropertySource(value = "classpath:flows/errormappings/emptymapping.properties", encoding = "UTF-8")
public final class UploadAdapter {

    private final transient ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);

    private final transient MetaInfAdapter metaInfAdapter;
    private final transient ConfigAdapter configAdapter;
    private final transient EntriesAdapter entriesAdapter;
    private final transient PublicationAdapter publicationAdapter;
    private final transient Environment errorEnvironment;

    @Autowired
    public UploadAdapter(MetaInfAdapter metaInfAdapter,
                         ConfigAdapter configAdapter,
                         EntriesAdapter entriesAdapter,
                         PublicationAdapter publicationAdapter,
                         Environment errorEnvironment) {
        this.metaInfAdapter = metaInfAdapter;
        this.configAdapter = configAdapter;
        this.entriesAdapter = entriesAdapter;
        this.publicationAdapter = publicationAdapter;
        this.errorEnvironment = errorEnvironment;
    }

    public UploadDto initializeDto() {
        return new UploadDto();
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    public boolean processPollFile(MultipartFile pollFile,
                                   MessageContext context,
                                   DatePollDto datePollDto) {
        try (InputStream pollFileInputStream = pollFile.getInputStream()) {

            final JsonNode jsonPoll = mapper.readTree(pollFileInputStream);

            parseMetaInf(jsonPoll, context).ifPresent(datePollDto::setMetaInfDto);
            parseConfig(jsonPoll, context).ifPresent(datePollDto::setConfigDto);
            parseEntries(jsonPoll, context).ifPresent(datePollDto::setEntriesDto);
            parsePublicationInformation(jsonPoll, context).ifPresent(datePollDto::setPublicationDto);

            if (!context.hasErrorMessages()) {
                return true;
            }

        } catch (IOException e) {
            ErrorMessageHelper.addMessage("FILE_PARSE_ERROR", context, errorEnvironment);
        }
        return false;
    }

    @SuppressWarnings("PMD.EmptyCatchBlock") //NOPMD
    private Optional<MetaInfDto> parseMetaInf(JsonNode node, MessageContext context) {
        try {
            final MetaInfDto metaInfDto = mapper.readValue(node.toString(), MetaInfDto.class);
            if (metaInfAdapter.validateDto(metaInfDto, context)) {
                return Optional.of(metaInfDto);
            }
        } catch (IOException e) {
        }
        return Optional.empty();
    }

    @SuppressWarnings("PMD.EmptyCatchBlock")
    private Optional<ConfigDto> parseConfig(JsonNode node, MessageContext context) {
        try {
            final ConfigDto configDto = mapper.readValue(node.toString(), ConfigDto.class);
            if (configAdapter.validateDto(configDto, context)) {
                return Optional.of(configDto);
            }
        } catch (IOException e) {
        }
        return Optional.empty();
    }

    @SuppressWarnings({"PMD.EmptyCatchBlock", "PMD.LawOfDemeter"})
    private Optional<EntriesDto> parseEntries(JsonNode node, MessageContext context) {
        try {
            final EntriesDto entriesDto = entriesAdapter.initializeDto();
            final EntryDto[] entryDtos =  mapper.readValue(node.get("entries").toString(), EntryDto[].class);
            entriesDto.getEntries().addAll(Arrays.stream(entryDtos).collect(Collectors.toSet()));
            if (entryDtos.length > 0) {
                entriesDto.setProposedEntry(entryDtos[0]);
            }
            if (entriesAdapter.validateDto(entriesDto, context)) {
                return Optional.of(entriesDto);
            }
        } catch (IOException e) {
        }
        return Optional.empty();
    }

    @SuppressWarnings({"PMD.EmptyCatchBlock", "PMD.LawOfDemeter"})
    private Optional<PublicationDto> parsePublicationInformation(JsonNode jsonPoll, MessageContext context) {
        try {
            final PublicationDto publicationDto = publicationAdapter.initializeDto();
            final String[] groups = mapper.readValue(jsonPoll.get("groups").toString(), String[].class);
            publicationDto.setGroups(String.join(",", groups));
            if (publicationAdapter.validateDto(publicationDto, context)) {
                return Optional.of(publicationDto);
            }
        } catch (IOException e) {
        }
        return Optional.empty();
    }
}
