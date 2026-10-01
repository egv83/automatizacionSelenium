package com.ochobits.selenium.excel;

import com.ochobits.selenium.AbstractBrowserInit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class ExcelTest extends AbstractBrowserInit {

    private static WriteExcelFile writeExcelFile;
    private static ReadExcelFile readExcelFile;
    private By searchBoxLocator = By.name("search");
    private By searchBoxButton = By.xpath("//div[@id='search']//button[@class='btn btn-default btn-lg']");
    private By resultTextLocator = By.xpath("//*[@id='content']//div[@class='row']//div[@class='col-sm-6 text-right']");

    //LINUX
    private final String EXCEL_FILE_PATH = "/home/esteban/Documents/SeleniumTestDownload/store.xlsx";
//    private final String EXCEL_FILE_PATH = "SeleniumTestDownload/store.xlsx";

    //WINDOWS
//    private final String EXCEL_FILE_PATH = "H:\\PROYECTOS\\QA TEST\\SeleniumTestDownload\\store.xlsx";

    @BeforeAll
    public static void excelTestSetup() {
        readExcelFile = new ReadExcelFile();
        writeExcelFile = new WriteExcelFile();

        chromiumBrave(null);
        driver.get("https://opencart.abstracta.us/");
    }

    @Test
    public void testExcel() throws IOException, InterruptedException {

        String serachText = readExcelFile.getCellValues(EXCEL_FILE_PATH, "Hoja1", 0, 0);
        driver.findElement(searchBoxLocator).clear();
        driver.findElement(searchBoxLocator).sendKeys(serachText);
        driver.findElement(searchBoxButton).click();

        scrollPageByElementVisible(resultTextLocator);
//        driver.wait(2000);
        String resultTest = driver.findElement(resultTextLocator).getText();
        System.out.println("Resultado de la pagina es: " + resultTest);
        readExcelFile.readExcel(EXCEL_FILE_PATH, "Hoja1");
        writeExcelFile.writeCellValue(EXCEL_FILE_PATH, "Hoja1", 0, 1, resultTest);
        readExcelFile.readExcel(EXCEL_FILE_PATH, "Hoja1");

    }


    /**
     * VALIDA SI EXISTE EL XPATH
     *
     * @param xpath
     * @return
     */
    public static boolean existElementByPath(String xpath) {
//        Boolean elemento = false;
        Boolean status = false;
        try {
            WebElement webElement = driver.findElement(By.id(xpath));
            status = webElement.isDisplayed();
        } catch (Exception e) {
            throw new RuntimeException("Exception message: " + e);
        } finally {
            return status;
        }
    }

    public static boolean existElementBy(By byElement) {
        Boolean status = false;
        try {
            WebElement webElement = driver.findElement(byElement);
            status = webElement.isDisplayed();
        } catch (Exception e) {
            throw new RuntimeException("Exception message: " + e);
        } finally {
            return status;
        }
    }

    public static boolean existElementBy2(By byElement) {
        Boolean status = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
            status = webElement.isDisplayed();
        } catch (Exception e) {
            throw new RuntimeException("Exception message: " + e);
        } finally {
            return status;
        }
    }

    public static boolean existElementByWithJS(By byElement) {
        Boolean status = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            WebElement webElement = driver.findElement(byElement);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            Boolean isVisibleInViewport = (Boolean) js.executeScript(
                    "var rect = arguments[0].getBoundingClientRect();" +
                            "return (rect.top >= 0 && rect.left >= 0 && " +
                            "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                            "rect.right <= (window.innerWidth || document.documentElement.clientWidth));",
                    webElement
            );

            status = webElement.isDisplayed() && (isVisibleInViewport != null && isVisibleInViewport);
        } catch (Exception e) {
            throw new RuntimeException("Exception message: " + e);
        } finally {
            return status;
        }
    }

    public void scrollPageByXpathElementVisible(String xpath) {
        WebElement webElement = driver.findElement(By.xpath(xpath));
        try {
            while (!existElementByPath(xpath)) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0, 500);");
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception message: " + e);
        }
    }

    public void scrollPageByElementVisible(By byElement) {
        try {
            while (!existElementByWithJS(byElement)) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0, 500);");
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception message: " + e);
        }
    }

    public void scrollPageFindingElement(By byElement) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", byElement);
    }

}
