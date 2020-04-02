package red.semipro.batch.endrecruitmentseminar;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EndRecruitmentSeminarTask {

    private final EndRecruitmentSeminarHelper endRecruitmentSeminarHelper;

    @Scheduled(cron = "${custom.application.cron.endrecruitmentseminar}")
    public void execute() {

        log.info("セミナー募集終了処理を開始します");

        endRecruitmentSeminarHelper.execute(LocalDateTime.now());

        log.info("セミナー募集終了処理を終了します");

    }
}
