package red.semipro.share.converter;

import org.springframework.core.convert.converter.Converter;
import red.semipro.domain.enums.SeminarType;

public class StringToSeminarTypeConverter implements Converter<String, SeminarType> {

    @Override
    public SeminarType convert(String s) {
        return SeminarType.getSeminarType(s);
    }
}
