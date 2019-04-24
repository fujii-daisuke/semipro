package red.semipro.common;

import org.junit.Test;

public class CryptoTests {

    @Test
    public void test() throws Exception {
        Crypto crypto = new Crypto();
        String str = crypto.encrypto("13");
        System.out.println(str);
        
        Crypto crypto2 = new Crypto();
        String str2 = crypto2.decrypto(str);
        
        System.out.println(str2);
    }
}
