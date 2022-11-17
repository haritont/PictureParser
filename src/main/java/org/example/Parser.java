package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    private void downloadPicture(String urlPicture, int i){
        try {
            BufferedImage image;
            URL connect = new URL(urlPicture);
            image = ImageIO.read(connect);
            if (image != null){
                ImageIO.write(image, "jpg",new File("D:\\вуз файлы\\Практика\\PictureParser\\src\\main\\java\\org\\example\\picture\\"+text+i+".jpg"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String getUrlPicture(WebElement pictureWeb, int i) {
        try {
            String url = pictureWeb
                    .findElement(new By.ByXPath("//*[@id=\"islrg\"]/div[1]/div[" + i + "]/a[1]/div[1]/img"))
                    .getAttribute("src");
            if (url.matches("^data:.*")) return "";
            else return url;
        }
        catch (Exception e){
            return  "";
        }
    }
    private void pause(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void Work() {
        pause();
        driver.findElement(new By.ByXPath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input"))
                .sendKeys(text);
        pause();
        driver.findElement(new By.ByXPath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[2]/div[2]/div[5]/center/input[1]"))
                .click();
        pause();

        ArrayList<WebElement> items = (ArrayList<WebElement>) driver.findElements(new By.ByXPath("//*[@id=\"hdtb-msb\"]/div[1]/div/div"));
        for(WebElement item: items){
            if (item.getText().equals("Картинки")) {
                item.click();
                break;
            }
        }

        ArrayList<WebElement> pictures = (ArrayList<WebElement>) driver.findElements(new By.ByXPath("//*[@id=\"islrg\"]/div[1]/div"));
        for (int i = 1; i <= 100; i++) {
            WebElement pictureWeb = pictures.get(i - 1);

            JavascriptExecutor jsScroll = (JavascriptExecutor) driver;
            jsScroll.executeScript("arguments[0].scrollIntoView();", pictureWeb);
            pictures = (ArrayList<WebElement>) driver.findElements(new By.ByXPath("//*[@id=\"islrg\"]/div[1]/div"));

            String urlPicture = getUrlPicture(pictureWeb, i);
            if (!urlPicture.equals("")) downloadPicture(urlPicture, i);
        }
    }
    public void End(){
        driver.quit();
    }
}
