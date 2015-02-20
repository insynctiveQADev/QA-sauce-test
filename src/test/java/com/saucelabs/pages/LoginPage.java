package com.saucelabs.pages;

import com.saucelabs.LoginData;
import org.openqa.selenium.By;
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
    WebDriver driver;

    @FindBy(id = "login_UserName_I")
    WebElement loginUsernameField;
    @FindBy(id = "login_Password_I")
    WebElement loginPasswordField;
    @FindBy(id = "login_Login_CD")
    WebElement loginButton;


    public LoginPage() {

        this.driver = driver;

        //This initElements method will create all WebElements

        PageFactory.initElements(driver, this);

    }

    public static LoginPage navigateTo(WebDriver driver) {
        return PageFactory.initElements(driver,
                LoginPage.class);
    }

    public void login() throws Exception {
        driver.get("https://alphaex.insynctiveapps.com");
        waitUntilElementIsLoaded(loginButton);
        FillLoginForm(new LoginData("bpetrovski@insynctive.com", "apple$$$2405"));
        clickToLogin();
    }

    public void waitForElement(WebDriverWait wait, String element) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element)));
    }

    public void clickToLogin() {
        loginButton.click();
    }

    protected void FillLoginForm(LoginData loginData) {
        loginUsernameField.sendKeys(loginData.getUserName());
        loginPasswordField.click();
        loginPasswordField.sendKeys(loginData.getPass());
    }

    public void waitUntilElementIsLoaded(WebElement element) throws IOException, InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(element));
    }
}
