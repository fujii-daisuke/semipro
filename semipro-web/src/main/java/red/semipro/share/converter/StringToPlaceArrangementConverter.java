package red.semipro.share.converter;

import org.springframework.core.convert.converter.Converter;
import red.semipro.domain.enums.PlaceArrangement;

public class StringToPlaceArrangementConverter implements Converter<String, PlaceArrangement> {

    @Override
    public PlaceArrangement convert(String s) {
        return PlaceArrangement.getPlaceArrangement(s);
    }
}
