package red.semipro.batch.eventondatasync.process;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.batch.eventondatasync.process.subprocess.OwnerConvertSubProcess;
import red.semipro.batch.eventondatasync.process.subprocess.TicketConvertSubProcess;
import red.semipro.domain.eventon.model.Event;
import red.semipro.domain.model.address.Prefecture;
import red.semipro.domain.model.eventon.EventonSeminar;

@Component
@RequiredArgsConstructor
public class EventConvertProcess {

    private final OwnerConvertSubProcess ownerConvertSubProcess;
    private final TicketConvertSubProcess ticketConvertSubProcess;

    public EventonSeminar execute(@Nonnull final Event event) {

        return EventonSeminar.builder()
            .eventId(event.getEvent_id())
            .title(event.getTitle())
            .summary(event.getSummary())
            .contents(event.getContents())
            .imagePath(event.getImage_path())
            .startedAt(Optional.ofNullable(event.getStarted_at())
                .map(startedAt -> LocalDateTime
                    .parse(startedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .orElse(null))
            .endedAt(Optional.ofNullable(event.getEnded_at())
                .map(
                    endedAt -> LocalDateTime.parse(endedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .orElse(null))
            .openedAt(Optional.ofNullable(event.getOpened_at())
                .map(openedAt -> LocalDateTime
                    .parse(openedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .orElse(null))
            .entryStartedAt(Optional.ofNullable(event.getEntry_started_at())
                .map(entryStartedAt -> LocalDateTime
                    .parse(entryStartedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .orElse(null))
            .entryEndedAt(Optional.ofNullable(event.getEntry_ended_at())
                .map(entryEndedAt -> LocalDateTime
                    .parse(entryEndedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .orElse(null))
            .cancelAt(Optional.ofNullable(event.getCancel_at())
                .map(cancelAt -> LocalDateTime
                    .parse(cancelAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .orElse(null))
            .capacity(event.getCapacity())
            .eventUrl(event.getEvent_url())
            .url(event.getUrl())
            .cancelPolicy(event.getCancel_policy())
            .prefecture(Optional.ofNullable(event.getPrefecture_id())
                .map(prefectureId -> Prefecture.builder().id(prefectureId).build())
                .orElse(null))
            .address(event.getAddress())
            .place(event.getPlace())
            .lat(event.getLat())
            .lng(event.getLng())
            .accepted(event.getAccepted())
            .waiting(event.getWaiting())
            .embedCode(event.getEmbed_code())
            .updatedAt(Optional.ofNullable(event.getUpdated_at())
                .map(updatedAt -> LocalDateTime
                    .parse(updatedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .orElse(null))
            .tickets(ticketConvertSubProcess.execute(event.getTickets()))
            .owners(ownerConvertSubProcess.execute(event.getOwners()))
            .build();

    }
}
