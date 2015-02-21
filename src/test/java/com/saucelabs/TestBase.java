package com.saucelabs;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * Created by Iakov Volf on 2/9/2015.
 */


public class TestBase {
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private WebDriver driver;





    protected void clickToLogin() {
        driver.findElement(By.id("login_Login_CD")).click();
    }


    protected void openSettignsPage() {
        driver.get("https://alphaex.insynctiveapps.com/Insynctive.Hub/Protected/Invitations.aspx?page");
    }

    protected void clickToApps() {
        driver.findElement(By.id("lbl_Apps")).click();
    }

    protected boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


}
