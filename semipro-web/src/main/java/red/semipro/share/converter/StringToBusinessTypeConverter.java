package red.semipro.share.converter;

import org.springframework.core.convert.converter.Converter;
import red.semipro.domain.enums.BusinessType;

public class StringToBusinessTypeConverter implements Converter<String, BusinessType> {

    @Override
    public BusinessType convert(String s) {
        return BusinessType.getBusinessType(s);
    }

}
