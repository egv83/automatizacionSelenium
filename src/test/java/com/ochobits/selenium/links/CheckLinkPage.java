package com.ochobits.selenium.links;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CheckLinkPage {

    private WebDriver driver;

    public CheckLinkPage(WebDriver driver){
        this.driver = driver;
    }

    public boolean checkPageLink(){
        List<WebElement> link = driver.findElements(By.tagName("a"));
        String url = "";
        List<String> brokenLinks = new ArrayList<>();
        List<String> okLinks = new ArrayList<>();

        int responseCode = 200;

        HttpURLConnection httpURLConnection = null;
        Iterator<WebElement> it = link.iterator();

        while (it.hasNext()){
            url = it.next().getAttribute("href");
            if(url == null || url.isEmpty()){
                System.out.println(url + "url no esta configurada o vacia");
                continue;
            }

            try{
                httpURLConnection = (HttpURLConnection) (new URL(url).openConnection());
                httpURLConnection.setRequestMethod("HEAD");
                httpURLConnection.connect();
                responseCode = httpURLConnection.getResponseCode();

                if(responseCode > 400){
                    System.out.println("LINK NO VALIDO: "+ url);
                    brokenLinks.add(url);
                }else{
                    System.out.println("LINK VALIDO: "+url);
                    okLinks.add(url);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("LIKS VALIDOS: "+okLinks.size());
        System.out.println("LIKS NO VALIDOS: "+brokenLinks.size());

        if(brokenLinks.size()>0){
            System.out.println("***** ERROR ----- BROKEN LINK *****");
            for(String linkB : brokenLinks){
                System.out.println(linkB);
            }
            return false;
        }else {
            return true;
        }

    }

}
