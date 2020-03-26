package mops.infrastructure.groupsync;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import mops.infrastructure.groupsync.dto.GroupSyncInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


/**
 * GroupSyncController gibt, falls es eingelesen werden konnte, ein GroupSyncDto zurück.
 * Das Dto muss aber nicht gültig sein, d.h. beliebige Felder können null sein.
 */
@Component
@Log4j2
public final class GroupSyncWebclient {

    private final transient ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);

    private final transient String apiRoot;
    private final transient WebClient webClient;

    @Autowired
    public GroupSyncWebclient(Environment environment) {
        this.apiRoot = environment.getProperty("mops.gruppen2.api-root", "/");
        this.webClient = WebClient.create(apiRoot);
    }

    @SuppressWarnings("PMD.GuardLogStatement")
    public Optional<GroupSyncInputDto> getGroupSyncDto(long lastStatus) {
        try {
            final String responseBody = queryApi(lastStatus);
            if (responseBody == null) {
                throw new ConnectException();
            }
            final JsonNode node = mapper.readTree(responseBody);
            return parseGroupSyncDto(node);
        } catch (WebClientException e) {
            log.warn("Group Sync: Failed: " + e.getMessage());
        } catch (JsonProcessingException e) {
            log.warn("Group Sync: Failed: JSON could not be parsed: " + e.getMessage());
        } catch (ConnectException e) {
            log.warn("Group Sync: Failed: Connection failed. Comment out line 94"
                    + " of GroupSyncWebClient to get Stacktrace. ");
            log.warn("Hint: You can disable the synchronisation feature using mops.gruppen2.sync.enabled=false");
        }
        return Optional.empty();
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private Optional<GroupSyncInputDto> parseGroupSyncDto(JsonNode node) throws JsonProcessingException {
        if (node == null || node.isEmpty()) {
            log.warn("Group Sync: Failed: no JSON content");
            return Optional.empty();
        }
        final Optional<GroupSyncInputDto> groupSyncDto =
                Optional.ofNullable(mapper.readValue(node.toString(), GroupSyncInputDto.class));

        groupSyncDto.ifPresentOrElse(
                g -> log.debug("Group Sync: Successfully read PublicationDto: ".concat(g.getGroupList().toString())),
                () -> log.warn("Group Sync: Failed: Object mapping failed for JSON:\n".concat(node.toPrettyString())));
        return groupSyncDto;
    }

    @SuppressWarnings("PMD")
    private String queryApi(long lastStatus) {
        final WebClient.RequestHeadersSpec<?> request =
                webClient.method(HttpMethod.GET)
                        .uri(apiRoot.concat(String.format("/api/updateGroups/%d", lastStatus)))
                        .body(BodyInserters.empty()).acceptCharset(StandardCharsets.UTF_8);
        // TODO: UnknownHostException usw. korrekt behandeln und ausgeben
        return request
                .retrieve()
                .bodyToMono(String.class)
                // Wenn der Error kein HTTP-Status-Error ist, einfach mit leerem Mono weitermachen.
                // Wird dann im Try-Catch-Block gefangen. Etwas unsauber, aber ich habe mäßig viel Ahnung
                // von Reaktiver Programmierung und WebFlux und kann es entsprechend nicht besser
                .onErrorResume(e -> !(e instanceof WebClientResponseException), e -> Mono.empty())
                .block();
    }
}
