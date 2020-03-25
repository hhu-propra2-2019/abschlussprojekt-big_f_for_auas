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
import org.springframework.binding.message.DefaultMessageContext;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public final class UploadService {

    private final transient ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);

    private final transient MetaInfAdapter metaInfAdapter;
    private final transient ConfigAdapter configAdapter;
    private final transient EntriesAdapter entriesAdapter;
    private final transient EntryAdapter entryAdapter;
    private final transient PublicationAdapter publicationAdapter;
    private final transient MessageSource messageSource;

    @Autowired
    public UploadService(MetaInfAdapter metaInfAdapter,
                         ConfigAdapter configAdapter,
                         EntriesAdapter entriesAdapter,
                         EntryAdapter entryAdapter,
                         PublicationAdapter publicationAdapter,
                         MessageSource messageSource) {
        this.metaInfAdapter = metaInfAdapter;
        this.configAdapter = configAdapter;
        this.entriesAdapter = entriesAdapter;
        this.entryAdapter = entryAdapter;
        this.publicationAdapter = publicationAdapter;
        this.messageSource = messageSource;
    }

    public UploadDto initializeDto() {
        return new UploadDto();
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    public boolean processPollFile(MultipartFile pollFile,
                                   MessageContext context,
                                   DatePollDto datePollDto) {
        try (InputStream pollFileInputStream = pollFile.getInputStream()) {

            if (pollFile.isEmpty()) {
                throw new IOException();
            }
            final JsonNode jsonPoll = mapper.readTree(pollFileInputStream);

            parseMetaInf(jsonPoll, context).ifPresent(datePollDto::setMetaInfDto);
            parseConfig(jsonPoll, context).ifPresent(datePollDto::setConfigDto);
            parseEntries(jsonPoll, context).ifPresent(datePollDto::setEntriesDto);
            parsePublicationInformation(jsonPoll, context).ifPresent(datePollDto::setPublicationDto);

            if (!context.hasErrorMessages()) {
                return true;
            }

        } catch (IOException e) {
            ErrorMessageHelper.addMessageWithSource("FILE_PARSE_ERROR", context, ErrorMessageHelper.DEFAULTERRORS);
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
            final EntryDto[] entryDtos =  mapper.readValue(node.get("entries").toString(), EntryDto[].class);
            final DefaultMessageContext emptyContext = new DefaultMessageContext();
            emptyContext.setMessageSource(messageSource);
            // Vorher auf ungültige Einträge prüfen, da sonst beim Hinzufügen ins Set in der
            // compareTo-Methode eine ParseException geworfen wird.
            if (Arrays.stream(entryDtos)
                    .anyMatch(Predicate.not(entry -> entryAdapter.validateDto(entry, emptyContext)))) {
                ErrorMessageHelper.addMessageWithSource(
                        "DATE_POLL_INVALID_ENTRIES", context, ErrorMessageHelper.DEFAULTERRORS);
                return Optional.empty();
            }
            final EntriesDto entriesDto = entriesAdapter.initializeDto();
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
            if (jsonPoll.get("ispublic") != null
                    && !jsonPoll.get("ispublic").asBoolean() && jsonPoll.get("groups") != null) {
                final String[] groups = mapper.readValue(jsonPoll.get("groups").toString(), String[].class);
                publicationDto.setGroups(String.join(",", groups));
            }
            if (publicationAdapter.validateDto(publicationDto, context)) {
                return Optional.of(publicationDto);
            }
        } catch (IOException e) {
        }
        return Optional.empty();
    }
}
