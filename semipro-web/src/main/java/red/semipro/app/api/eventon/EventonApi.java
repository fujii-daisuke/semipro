package red.semipro.app.api.eventon;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import red.semipro.common.HttpUrlConnector;
import red.semipro.domain.model.eventon.Response;
import red.semipro.domain.service.seminar.SeminarServiceImpl;

@Component
@EnableScheduling
@Slf4j
public class EventonApi {

    private static final String URL = "https://eventon.jp/api/events.json";
    @Autowired
    private SeminarServiceImpl seminarService;
    
    @Scheduled(cron = "${custom.application.cron.eventon}", zone = "Asia/Tokyo")
    public void execute() {
        try {
            String result = HttpUrlConnector.callGet(createUrl());
            ObjectMapper mapper = new ObjectMapper();
            Response response = mapper.readValue(result, Response.class);
            log.debug("count: " + response.getCount());
            log.debug("start: " + response.getStart());
            log.debug("limit: " + response.getLimit());
            log.debug("size: " + response.getEvents().size());
            seminarService.eventonRegister(response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private String createUrl() throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        return sb.append(URL).append("?")
        .append("keyword="+URLEncoder.encode("セミナー", "UTF-8"))
        .append("&start=1")
        .append("&limit=100")
        .append("&order=started_at_desc")
        .toString();
    }
}
