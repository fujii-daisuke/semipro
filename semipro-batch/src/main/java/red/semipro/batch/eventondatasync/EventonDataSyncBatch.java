package red.semipro.batch.eventondatasync;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import red.semipro.batch.eventondatasync.process.EventConvertProcess;
import red.semipro.batch.eventondatasync.process.EventonRegisterProcess;
import red.semipro.batch.eventondatasync.process.EventonSearchProcess;
import red.semipro.domain.eventon.model.Event;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventonDataSyncBatch {

    private final EventonSearchProcess eventonSearchProcess;
    private final EventConvertProcess eventConvertProcess;
    private final EventonRegisterProcess eventonRegisterProcess;

    @Scheduled(cron = "${custom.application.cron.eventondatasync}")
    public void execute() {

        log.info("Eventonデータ連携処理を開始します");

        log.info("Eventonからセミナー情報を取得します");
        final List<Event> eventList = eventonSearchProcess.execute();
        if (Objects.isNull(eventList)) {
            log.info("連携データは０件の為、Eventonデータ連携処理を終了します");
            return;
        }

        log.info("取得したセミナー件数：" + eventList.size());
        log.info("セミプロDBに保存します");
        for (Event event: eventList) {
            try {
                eventonRegisterProcess.execute(eventConvertProcess.execute(event));
            } catch (Exception e) {
                log.error("セミプロDB登録時にエラーが発生しました. event_id: " + event.getEvent_id(), e);
            }
        }

        log.info("Eventonデータ連携処理を終了します");

    }
}
