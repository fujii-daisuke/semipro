package red.semipro.batch.endrecruitmentseminar;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import red.semipro.domain.service.establish.EstablishService;

@Component
@RequiredArgsConstructor
@Slf4j
public class EndRecruitmentSeminarBatch {

    private final EstablishService establishService;

    @Scheduled(cron = "${custom.application.cron.endrecruitmentseminar}")
    public void execute() {

        log.info("セミナー募集終了処理を開始します");

        establishService.execute(LocalDate.now());

        log.info("セミナー募集終了処理を終了します");

    }
}
