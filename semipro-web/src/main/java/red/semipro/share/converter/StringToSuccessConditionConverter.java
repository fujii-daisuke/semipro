package red.semipro.share.converter;

import org.springframework.core.convert.converter.Converter;
import red.semipro.domain.enums.SuccessCondition;

public class StringToSuccessConditionConverter implements Converter<String, SuccessCondition> {

    @Override
    public SuccessCondition convert(String s) {
        return SuccessCondition.getSuccessCondition(s);
    }
}
