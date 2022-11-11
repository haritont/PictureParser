package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Parser {
    private String url;
    private String text;

    private WebDriver driver;

    public Parser(){
        System.setProperty("webdriver.chrome.driver", "selenium\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void Start(){
        driver.get(url);
        Work();
    }
    private void downloadPicture(WebElement pictureWeb){
        try {
            BufferedImage image =null;
            URL connect = new URL(getUrlPicture(pictureWeb));
            image = ImageIO.read(connect);
            if (image != null){
                ImageIO.write(image, "jpg",new File("picture\\"+pictureWeb.getText()+".jpg"));
            }
        }
        catch (FileNotFoundException e) {
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String getUrlPicture(WebElement pictureWeb){
        return pictureWeb.getAttribute("src");
    }
    private void Work(){
        driver.findElement(new By.ByXPath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input"))
                .sendKeys(text);
        driver.findElement(new By.ByXPath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[4]/center/input[1]"))
                .click();
        driver.findElement(new By.ByXPath("//*[@id=\"hdtb-msb\"]/div[1]/div/div[2]/a"))
                .click();

        ArrayList<WebElement> pictures = (ArrayList<WebElement>) driver.findElements(new By.ByXPath("//*[@id=\"islrg\"]/div[1]"));
        for (int i=1;i<pictures.size();i++){
            WebElement pictureWeb = pictures.get(i-1);

            JavascriptExecutor jsScroll = (JavascriptExecutor) driver;
            jsScroll.executeScript("arguments[0].scrollIntoView();", pictureWeb);
            pictures = (ArrayList<WebElement>) driver.findElements(new By.ByXPath("//*[@id=\"islrg\"]/div[1]"));

            downloadPicture(pictureWeb);
        }
    }

    public void End(){
        driver.quit();
    }
}
