package red.semipro.domain.eventon.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import red.semipro.common.HttpUrlConnector;
import red.semipro.domain.eventon.model.Event;
import red.semipro.domain.eventon.model.Response;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EventonRepositoryImpl implements EventonRepository {

    private static final String EVENTON_URL = "https://eventon.jp/api/events.json";

    public List<Event> search(@Nonnull final EventonCriteria criteria) {
        Response response = null;
        try {
            String result = HttpUrlConnector.callGet(createRequestParam(criteria));
            ObjectMapper mapper = new ObjectMapper();
            response = mapper.readValue(result, Response.class);
            log.debug("count: " + response.getCount());
            log.debug("start: " + response.getStart());
            log.debug("limit: " + response.getLimit());
            log.debug("size: " + response.getEvents().size());
//            for (Event event : response.getEvents()) {
//                log.debug("event_id: " + event.getEvent_id());
//                Seminar seminar = new Seminar(event);
//                log.debug("seminar_id: " + seminar.getProviderSeminarId());
//                seminarService.registerByProvider(seminar);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(response)
            .map(Response::getEvents)
            .orElse(null);
    }

    private String createRequestParam(@Nonnull final EventonCriteria criteria) {
        return EVENTON_URL
            + "?"
            + "keyword=" + URLEncoder.encode(criteria.getKeyword(), StandardCharsets.UTF_8)
            + "&start=" + criteria.getStart()
            + "&limit=" + criteria.getLimit()
            + "&order=" + criteria.getOrder();
    }
}
