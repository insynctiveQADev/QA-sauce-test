package com.saucelabs.pages;

import com.saucelabs.LoginData;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

/**
 * Created by Iakov Volf on 2/19/2015.
 */
public class LoginPage {
    @FindBy(id = "divTasks")
    public static WebElement taskButton;
    static WebDriver driver;
    @FindBy(id = "login_UserName_I")
    static WebElement loginUsernameField;
    @FindBy(id = "login_Password_I")
    static WebElement loginPasswordField;
    @FindBy(id = "login_Login_CD")
    static WebElement loginButton;

    public LoginPage() {

        driver = driver;

        //This initElements method will create all WebElements

        PageFactory.initElements(driver, this);

    }

    public static LoginPage navigateTo(WebDriver driver) {
        driver.get("https://alphaex.insynctiveapps.com");
        return PageFactory.initElements(driver,
                LoginPage.class);
    }

    public static void login() throws Exception {
        waitUntilElementIsLoaded(loginButton);
        fillLoginForm(new LoginData("bpetrovski@insynctive.com", "apple$$$2405"));
        clickToLogin();
    }

    public static void clickToLogin() {
        loginButton.click();
    }

    public static void fillLoginForm(LoginData loginData) {
        loginUsernameField.sendKeys(loginData.getUserName());
        loginPasswordField.click();
        loginPasswordField.sendKeys(loginData.getPass());
    }

    public static boolean exists(WebElement element) {
        try {
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException ignored) {
            return false;
        }
    }

    public static boolean isLoggedIn(WebElement element) {
        try {
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException ignored) {
            return false;
        }
    }

    public static boolean isNotLoggedIn() {
        return driver.findElements(By.xpath("//span[@class='js-auth-signin b-navbar__exit h-ml-10']")).size() > 0;
    }

    public static void waitUntilElementIsLoaded(WebElement element) throws IOException, InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElement(WebDriverWait wait, String element) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element)));
    }
}
