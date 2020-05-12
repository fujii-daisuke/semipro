package red.semipro;

import com.ibm.icu.text.Transliterator;
import org.junit.Test;

public class ICU4JTest {

    @Test
    public void fullToHalf() {
        Transliterator fullToHalf = Transliterator.getInstance("Fullwidth-Halfwidth");
        String target =
            "１２３４−５　ギンザビル７F";

        System.out.println(fullToHalf.transliterate(target));
    }
}
