package com.ochobits.selenium.downloadfile;

import com.ochobits.selenium.AbstractBrowserInit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import java.io.File;
import java.util.HashMap;

public class DownloadFileTest extends AbstractBrowserInit {

    private static final String DOWNLOAD_FILE_PATH = "/home/esteban/Documents/SeleniumTestDownload";


    @BeforeAll
    public static void downloadSetup() {
        HashMap<String, Object> chromePreferences = new HashMap<>();
        //1 Definir ruta de descarga
        chromePreferences.put("download.default_directory", DOWNLOAD_FILE_PATH);

        //2 Desactivar cuadro de descarga
        chromePreferences.put("download.prompt_for_download", false);

        //3 Permite descargas automaticsa multiples sin pedir confirmacion
        chromePreferences.put("profile.default_content_settings.popup", 0);
        chromePreferences.put("profile.content_settings.exception.automatic_downloads.*.setting", 1);

        //4 Desactiva la verificación del inspector de descargas inseguras de Google/Brave
        chromePreferences.put("safebrowsing.enabled", true);

        //SE INICIALIZA BRAVE CON LAS PREFERENCIAS PARA DESCARGA
        chromiumBrave(chromePreferences);

        driver.get("https://the-internet.herokuapp.com/download");
    }

    @Test
    public void downlodFile() throws InterruptedException {
        driver.findElement(By.cssSelector("div.example>a")).click();
        Thread.sleep(2000);

        File folder = new File(DOWNLOAD_FILE_PATH);
        File[] listFiles = folder.listFiles();


        Assertions.assertTrue(listFiles.length > 0, "!!! File not downloaded");
    }

    @Test
    public void downlodFileError() throws InterruptedException {

//        try{
//            driver.findElement(By.cssSelector("div.example2>a")).click();
//        } catch (Exception e) {
//            Assertions.fail( "!!Error: El boton o enlace de descarga no existe");
//        }

        var elemtos = driver.findElements(By.cssSelector("div.example2>a"));
        Thread.sleep(2000);

//        File folder = new File(DOWNLOAD_FILE_PATH);
//        File[] listFiles = folder.listFiles();

        Assertions.assertFalse(elemtos.isEmpty(), "!!Error: El boton o enlace de descarga no existe");
        elemtos.get(0).click();

    }

}
