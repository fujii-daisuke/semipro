package red.semipro.share.converter;

import org.springframework.core.convert.converter.Converter;
import red.semipro.domain.enums.Gender;

public class StringToGenderConverter implements Converter<String, Gender> {

    @Override
    public Gender convert(String s) {
        return Gender.getGender(s);
    }

}
