package mops.infrastructure.groupsync;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import mops.infrastructure.groupsync.dto.GroupSyncDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

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

    private final String apiRoot;
    private final WebClient webClient;

    // Der Status wird von der API zurückgegeben, um inkrementelle Updates zu ermöglichen
    // Da er lokal gespeichert wird, wird bei jedem Neustart der Anwendung ein voller Resync getriggert,
    // was auch sinnvoll erscheint, falls durch unvorhersehbare Fehler ein inkonsistenter Zustand entsteht
    private long lastStatus = 0;

    @Autowired
    public GroupSyncWebclient(Environment environment) {
        this.apiRoot = environment.getProperty("mops.gruppe2.api-root", "/");
        this.webClient = WebClient.create(apiRoot);
    }

    public Optional<GroupSyncDto> getGroupSyncDto() {
        try {
            String responseBody = queryApi();
            JsonNode node = mapper.readTree(responseBody);
            return parseGroupSyncDto(node);
        } catch (WebClientException e) {
            log.warn("Group Sync failed");
            log.warn(e.getMessage());
        } catch (JsonProcessingException e) {
            log.warn("Group Sync failed: JSON could not be parsed");
            log.warn(e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<GroupSyncDto> parseGroupSyncDto(JsonNode node) throws JsonProcessingException {
        if (node == null || node.isEmpty()) {
            log.warn("Group Sync failed: no JSON content");
            return Optional.empty();
        }
        Optional<GroupSyncDto> groupSyncDto =
                Optional.ofNullable(mapper.readValue(node.toString(), GroupSyncDto.class));

        groupSyncDto.ifPresentOrElse(
                g -> log.debug("Group Sync: ".concat(g.getGroupList().toString())),
                () -> log.debug("Group Sync: Object mapping failed for JSON:\n".concat(node.toPrettyString())));
        return groupSyncDto;
    }

    private String queryApi() {
        WebClient.RequestHeadersSpec<?> request =
                webClient.method(HttpMethod.GET)
                        .uri(apiRoot.concat(String.format("/api/updateGroups/%d", lastStatus)))
                        .body(BodyInserters.empty()).acceptCharset(StandardCharsets.UTF_8);
        // TODO: UnknownHostException usw. fangen
        // Das ist im obigen Try-Catch-Block nicht möglich und funktioniert mit
        // Spring Web Flux auf eine bestimmte Art, die ich nicht kenne.
        return request
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
