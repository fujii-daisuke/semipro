package red.semipro.business.util;

import java.io.File;

import org.junit.Test;

public class FileUploaderTest {

    @Test
    public void test() {
        File uploadFile = new File("upload/seminar/test.jpg");
        uploadFile.mkdirs();
    }
}
