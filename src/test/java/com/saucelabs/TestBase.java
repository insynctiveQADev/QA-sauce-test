package com.saucelabs;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.assertEquals;

/**
 * Created by Iakov Volf on 2/9/2015.
 */


public class TestBase {
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private WebDriver driver;


    public void login() throws Exception {


        driver.get("https://alphaex.insynctiveapps.com");
        WebDriverWait wait = new WebDriverWait(driver, 5); // wait for a maximum of 5 seconds
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login_Login_CD")));

        driver.findElement(By.id("login_UserName_I")).sendKeys("ivolf@insynctive.com");
        driver.findElement(By.id("login_Password_I")).click();
        driver.findElement(By.id("login_Password_I")).sendKeys("123456");
        driver.findElement(By.id("login_Login_CD")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@id='lTasks']/img")));

        try {
            assertEquals("GETTING STARTED", driver.findElement(By.id("tds_body_newsTab_AT0T")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isNotLoggedIn() {
        return driver.findElements(By.xpath("//span[@class='js-auth-signin b-navbar__exit h-ml-10']")).size() > 0;
    }

    public boolean isLoggedIn() {
        return driver.findElements(By.xpath("//a[@id='lTasks']/img")).size() > 0;
    }
}
