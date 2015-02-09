package com.saucelabs;

/**
 * Created by Iakov Volf on 2/9/2015.
 */

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import com.saucelabs.testng.SauceOnDemandTestListener;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.URL;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


@Listeners({SauceOnDemandTestListener.class})
public class V4SettingsTest extends TestBase implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

    public SauceOnDemandAuthentication authentication;
    private WebDriver driver;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    /**
     * Creates a new {@link org.openqa.selenium.remote.RemoteWebDriver} instance to be used to run WebDriver tests using Sauce.
     *
     * @param username       the Sauce username
     * @param key            the Sauce access key
     * @param os             the operating system to be used
     * @param browser        the name of the browser to be used
     * @param browserVersion the version of the browser to be used
     * @param method         the test method being executed
     * @throws Exception thrown if any errors occur in the creation of the WebDriver instance
     */
    @Parameters({"username", "key", "os", "browser", "browserVersion"})
    @BeforeMethod
    public void setUp(@Optional("ivolf") String username,
                      @Optional("90e3bb89-c21d-4885-85cf-f25494db06ff") String key,
                      @Optional("Windows 8.1") String os,
                      @Optional("firefox") String browser,
                      @Optional("") String browserVersion,
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
     *
     * @return
     */
    @Override
    public String getSessionId() {
        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
        return (sessionId == null) ? null : sessionId.toString();
    }

    @Test
    public void settingApps_5867() throws Exception {
        loginAsEmployee();
        openSettignsPage();
        clickToApps();
        assertEquals("Installed Apps", driver.findElement(By.id("install-title")).getText());
    }

    @Test
    public void testNewMenuSettingApps_5880() throws Exception {
        openSettignsPage();
        assertTrue(isElementPresent(By.id("linkAccount")));
        assertTrue(isElementPresent(By.id("linkPeopleSettings")));
        assertTrue(isElementPresent(By.id("linkPlatform")));
        assertTrue(isElementPresent(By.id("linkApps")));

        clickToApps();
        assertTrue(isElementPresent(By.id("appSearch")));

    }

    protected void openSettignsPage() {
        driver.get("https://alphaex.insynctiveapps.com/Insynctive.Hub/Protected/Invitations.aspx?page");
    }

    protected void clickToApps() {
        driver.findElement(By.id("lbl_Apps")).click();
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
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

    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return null;
    }
}

