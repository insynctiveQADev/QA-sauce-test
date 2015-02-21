package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.pages.LoginPage;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import com.saucelabs.testng.SauceOnDemandTestListener;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.URL;

import static org.testng.Assert.assertTrue;


/**
 * Simple {@link org.openqa.selenium.remote.RemoteWebDriver} test that demonstrates how to run your Selenium tests with <a href="http://saucelabs.com/ondemand">Sauce OnDemand</a>.
 *
 * This test also includes the <a href="https://github.com/saucelabs/sauce-java/tree/master/testng">Sauce TestNG</a> helper classes, which will use the Sauce REST API to mark the Sauce Job as passed/failed.
 *
 * In order to use the {@link com.saucelabs.testng.SauceOnDemandTestListener}, the test must implement the {@link com.saucelabs.common.SauceOnDemandSessionIdProvider} and {@link com.saucelabs.testng.SauceOnDemandAuthenticationProvider} interfaces.
 * @author Ross Rowe
 */
@Listeners({SauceOnDemandTestListener.class})
public class LoginTest extends TestBase implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider  {

    public SauceOnDemandAuthentication authentication;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private WebDriver driver;

    /**
     * Creates a new {@link org.openqa.selenium.remote.RemoteWebDriver} instance to be used to run WebDriver tests using Sauce.
     *
     * @param username the Sauce username
     * @param key the Sauce access key
     * @param os the operating system to be used
     * @param browser the name of the browser to be used
     * @param browserVersion the version of the browser to be used
     * @param method the test method being executed
     * @throws Exception thrown if any errors occur in the creation of the WebDriver instance
     */
    @Parameters({"username", "key", "os", "browser", "browserVersion"})
    @BeforeMethod
    public void setUp(@Optional("ivolf") String username,
                      @Optional("90e3bb89-c21d-4885-85cf-f25494db06ff") String key,
                      @Optional("Windows 8.1") String os,
                      @Optional("chrome") String browser,
                      @Optional("39") String browserVersion,
                      Method method) throws Exception {

        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(key)) {
           authentication = new SauceOnDemandAuthentication(username, key);
        } else {
           authentication = new SauceOnDemandAuthentication("ivolf", "90e3bb89-c21d-4885-85cf-f25494db06ff");
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        capabilities.setCapability("version", browserVersion);
        capabilities.setCapability("platform", os);
        capabilities.setCapability("name", method.getName());
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getSessionId() {
        SessionId sessionId = ((RemoteWebDriver)driver).getSessionId();
        return (sessionId == null) ? null : sessionId.toString();
    }


    @Test
    public void loginSuccessiful() throws Exception {
        LoginPage.navigateTo(driver);
        LoginPage.login();
        assertTrue(LoginPage.isLoggedIn(LoginPage.taskButton));
    }

    @Test
    public void loginWithoutUserName() throws Exception {
        LoginPage.navigateTo(driver);
        WebDriverWait wait = new WebDriverWait(driver, 5); // wait for a maximum of 5 seconds
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login_Login_CD")));
        LoginPage.fillLoginForm(new LoginData("", "123456"));
        LoginPage.clickToLogin();

    }

    @Test
    public void loginWithoutPass() throws Exception {
        driver.get("https://alphaex.insynctiveapps.com");
        WebDriverWait wait = new WebDriverWait(driver, 5); // wait for a maximum of 5 seconds
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login_Login_CD")));
        LoginPage.fillLoginForm(new LoginData("ivolf@insynctive.com", ""));
        clickToLogin();

    }
    /**
     * Closes the WebDriver instance.
     *
     * @throws Exception thrown if an error occurs closing the WebDriver instance
     */
    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return authentication;
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
