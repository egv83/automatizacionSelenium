package com.ochobits.selenium.uploadfile;

import com.ochobits.selenium.AbstractBrowserInit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.io.File;

public class UploadFileTest extends AbstractBrowserInit {

    private static final String DOWNLOAD_FILE_PATH = "/home/esteban/Documents/SeleniumTestDownload";

    @BeforeAll
    public static void uploadSetup() {

        chromiumBrave(null);

        driver.get("https://the-internet.herokuapp.com/upload");
    }

    @Test
    public void uploadFile() throws InterruptedException {
        File file = new File("/home/esteban/Documents/SeleniumTestDownload/test-upload.txt");
        String path = file.getAbsolutePath();

        driver.findElement(By.id("file-upload")).sendKeys(path);
        driver.findElement(By.id("file-submit")).click();

        Thread.sleep(3000);

        String textUploaded = driver.findElement(By.id("uploaded-files")).getText();
        Assertions.assertEquals("test-upload.txt", textUploaded);
    }

}
