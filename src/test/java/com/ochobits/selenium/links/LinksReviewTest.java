package com.ochobits.selenium.links;

import com.ochobits.selenium.AbstractBrowserInit;

import org.junit.jupiter.api.*;

public class LinksReviewTest extends AbstractBrowserInit {

    private static CheckLinkPage page;

    //Con @BeforeAll se ejecuta antes de de toda la clase y debe se static, es decir antes de cualquir @Beforeach
    //Si uso BeforeEach este se inicializa, pasa lo mismo con @AfterAll
    @BeforeAll
    public static void beforeClass() {
        driver.get("https://eldeber.com.bo/");
        page = new CheckLinkPage(driver);

    }


    @Test
    public void checkPageURL() {
        Assertions.assertTrue(page.checkPageLink(), "Hay Broken links");
    }

}
