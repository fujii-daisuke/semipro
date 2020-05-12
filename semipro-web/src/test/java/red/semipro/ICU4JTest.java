package red.semipro;

import com.ibm.icu.text.Transliterator;
import org.junit.Test;

public class ICU4JTest {

    @Test
    public void fullToHalf() {
        Transliterator fullToHalf = Transliterator.getInstance("Fullwidth-Halfwidth");
        String target =
            "全半“　”\\\" \\\"あがぱアガパｱｶﾞﾊﾟＡＢａｂABab１２３123「」（）()［］[]；;！!？?＃#／/－-・┣①⑪㌀㈱㌔¼⑴\"";

        System.out.println(fullToHalf.transliterate(target));
    }

    @Test
    public void hyphens() {
        String hyphonens = "-";
        String value = "1234−5";
        String target = "[ー‐‑–—―−ｰ]";

        System.out.println(value.replaceAll(target, hyphonens));

    }
}
