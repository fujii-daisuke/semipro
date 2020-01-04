package red.semipro;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {

    @Test
    public void encodeTest() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodeString = passwordEncoder.encode("password");
        System.out.println(encodeString);
    }
}
