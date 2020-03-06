package red.semipro.domain.service.seminar;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.seminar.SeminarSearchCriteria;
import red.semipro.domain.model.seminar.SeminarSummary;
import red.semipro.domain.repository.seminar.SeminarSummaryRepository;

/**
 * セミナーサマリー - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarSummaryService {

    private final SeminarSummaryRepository seminarSummaryRepository;

    /**
     * セミナーサマリー一覧を取得します
     *
     * @param criteria 検索条件
     * @param pageable ページネーション
     * @return セミナーサマリー一覧
     */
    public Page<SeminarSummary> search(SeminarSearchCriteria criteria, Pageable pageable) {

        long total = seminarSummaryRepository.countByCriteria(criteria);
        List<SeminarSummary> content;
        if (0 < total) {
            content = seminarSummaryRepository.findPageByCriteria(criteria, pageable);
        } else {
            content = Collections.emptyList();
        }
        return new PageImpl<>(content, pageable, total);
    }
}
