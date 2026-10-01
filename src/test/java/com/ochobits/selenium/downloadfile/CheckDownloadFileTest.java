package com.ochobits.selenium.downloadfile;

import com.ochobits.selenium.AbstractBrowserInit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckDownloadFileTest extends AbstractBrowserInit {

    @BeforeAll
    public static void downloadSetup() {
        chromiumBrave(null);

        driver.get("https://the-internet.herokuapp.com/download");
    }

    @Test
    public void checkDownloadTest() throws IOException {
        String link = driver.findElement(By.cssSelector("div.example>a")).getAttribute("href");
            HttpURLConnection httpConnection = (HttpURLConnection) (new URL(link).openConnection());
            httpConnection.setRequestMethod("HEAD");
            httpConnection.connect();

            String contentType = httpConnection.getContentType();
            int contenTypeLenght = httpConnection.getContentLength();

        System.out.println("Content Type: "+contentType);

        Assertions.assertEquals(contentType,"application/octet-stream");
        Assertions.assertNotEquals(contenTypeLenght,0);

    }


}
