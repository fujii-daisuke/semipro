package red.semipro.admin.app.searchseminar;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.domain.model.seminar.SeminarSearchCriteria;

/**
 * セミナー検索フォーム - converter
 */
@Component
@RequiredArgsConstructor
public class SearchSeminarFormConverter {

    /**
     * 検索条件フォームから検索条件モデルにコンバートする
     *
     * @param form 検索条件フォーム
     * @return 検索条件モデル
     */
    public SeminarSearchCriteria convert(SearchSeminarForm form) {
        return SeminarSearchCriteria.builder()
            .build();
    }
}
