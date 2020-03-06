package red.semipro.share.converter;

import org.springframework.core.convert.converter.Converter;
import red.semipro.domain.enums.SeminarImageType;

public class StringToSeminarImageTypeConverter implements Converter<String, SeminarImageType> {

    @Override
    public SeminarImageType convert(String s) {
        return SeminarImageType.getSeminarImageType(s);
    }
}
