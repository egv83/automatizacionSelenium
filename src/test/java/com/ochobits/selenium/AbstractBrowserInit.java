package com.ochobits.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.Map;

public abstract class AbstractBrowserInit {

    protected static WebDriver driver;

    //Con BeforeAll se ejecuta primero y debe se static
//    @BeforeAll
    public static void setUp() {
//        driver = new EdgeDriver();
//        driver.manage().window().maximize();
        chromiumBrave(null);
//        driver.get("https://www.google.com/");
    }

    //AfterAll se ejecuta primero al igual que BeforeAlly debe se static
    @AfterAll
    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected static void chromiumBrave(Map extraPrefs) {
        ChromeOptions options = new ChromeOptions();
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")) {
            options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        }else {
            options.setBinary("/usr/bin/brave-browser");
            //PERFIL DE USUARIO SI EL CAPTCHA PERSISRE CHROMIUN BRAVE
            // Perfil aislado exclusivo para Selenium en /tmp
            // Evita interferir con tus páginas/cursos abiertos en tu Brave personal
            options.addArguments("--user-data-dir=/tmp/brave_selenium_session");
        }

        // --- AGREGA ESTA LÍNEA CRÍTICA AQUÍ PARA CORREGIR EL ERROR 403 ---
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");

        // --- NUEVOS ARGUMENTOS PARA EVITAR CAPTCHAS ---
        // 1. Desactiva la barra superior que dice "Un software de prueba automatizado está controlando este navegador"
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        // 3. Desactiva características de parpadeo que delatan la automatización
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");


        //options.addArguments("--user-data-dir=/home/esteban/.config/BraveSoftware/Brave-Browser");
        // Especifica qué perfil usar dentro de esa raíz
        //options.addArguments("--profile-directory=Default");

        //PREFERENCIAS PERSONALIZADAS
        if (extraPrefs != null && !extraPrefs.isEmpty()) {
            options.setExperimentalOption("prefs", extraPrefs);
        }

        driver = new ChromeDriver(options);
//        driver.manage().window().maximize();

        //Se define el tamaño de la panatalla del navegador
        driver.manage().window().setSize(new Dimension(1280,1440));

    }

}
