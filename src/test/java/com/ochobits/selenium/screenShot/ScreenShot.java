package com.ochobits.selenium.screenShot;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShot {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {

        //PARA CHROMIUN
        //PARA DEFINIR LA VERSION DEL WEBDRIVER SOLO EN EL CASO DE LINUX//
        //WebDriverManager.chromedriver().driverVersion("153").setup();

        //CHROME OPTIONS
        //ChromeOptions options = new ChromeOptions();

        //PARA CHOMIUM SOLO EN EL CASO DE LINUX
        /*
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/usr/bin/brave-browser");




        // --- AGREGA ESTA LÍNEA CRÍTICA AQUÍ PARA CORREGIR EL ERROR 403 ---
        options.addArguments("--remote-allow-origins=*");

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");

        // --- NUEVOS ARGUMENTOS PARA EVITAR CAPTCHAS ---

        // 1. Desactiva la barra superior que dice "Un software de prueba automatizado está controlando este navegador"
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        // 2. Modifica el User-Agent para que parezca un usuario real en Linux en lugar de un bot
        /options.addArguments("user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

        // 3. Desactiva características de parpadeo que delatan la automatización
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

        //SI EL CAPTCHA PERSISRE CHROMIUN BRAVE
        options.addArguments("--user-data-dir=/home/esteban/.config/BraveSoftware/Brave-Browser/Default");
        */

        //PARA CHROME
        //driver = new ChromeDriver(options);


        //MOZILLA FIREFOX
        //OPTIONS
        /*
        FirefoxOptions options = new FirefoxOptions();

        // 1. Crear perfil para modificar User-Agent y preferencias de privacidad
        FirefoxProfile profile = new FirefoxProfile();

        // Cambiar User-Agent a un navegador Firefox real en Linux
        profile.setPreference("general.useragent.override",
                "Mozilla/5.0 (X11; Linux x86_64; rv:125.0) Gecko/20100101 Firefox/125.0");

        // 2. Ocultar la propiedad navigator.webdriver que delata automatización
        profile.setPreference("dom.webdriver.enabled", false);
        profile.setPreference("useAutomationExtension", false);

        // Asignar el perfil a las opciones
        options.setProfile(profile);

        // 3. (Opcional) Si necesitas ejecutarlo en modo sin interfaz gráfica
        // options.addArguments("-headless");

        driver = new FirefoxDriver(options);
         */

        //PARA CHROMIUN
        //driver = new ChromeDriver(options);

        //MICROSOFT EDGE
        driver = new EdgeDriver();


        driver.manage().window().maximize();
        driver.get("https://www.google.com/");
    }


    @Test
    public void screenShotTest() {
        WebElement searchBox = driver.findElement(By.name("q+"));
        searchBox.clear();

    }

    @RegisterExtension  // el el remplaso de @Rule en JUnit4, si el metofo esta como static actua como @ClassRule
    public TestWatcher miron = new TestWatcher() {

        @Override  //En JUnit 5 Description cambia a ExtentionContext
        public void testFailed(ExtensionContext context, Throwable throwable) {
            // 1. Tomar la captura de pantalla
            File screenShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String browserName = ((HasCapabilities) driver).getCapabilities().getBrowserName();

            /* 2. Obtener el nombre del método*/
            String nombre = context.getTestMethod()
                    .map(Method::getName)
                    .orElse("desconocido");

            try {
                FileUtils.copyFile(screenShotFile, new File(
                        browserName+" test_failed_" + nombre + "_" + getDate() + ".png")
                );
            } catch (Exception e) {
//                throw new RuntimeException(e);
                e.printStackTrace();
            }
            cerrarDriver();
        }

    };

    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        Date date = new Date();
        return format.format(date);

    }

    private void cerrarDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

}
