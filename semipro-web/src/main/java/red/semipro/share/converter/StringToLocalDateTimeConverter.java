package red.semipro.share.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public LocalDateTime convert(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        return LocalDateTime.parse(s, dateTimeFormatter);
    }
}
