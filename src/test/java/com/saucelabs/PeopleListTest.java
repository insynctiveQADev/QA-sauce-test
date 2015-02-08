package com.saucelabs;

/**
 * Created by Iakov Volf on 2/8/2015.
 */

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import com.saucelabs.testng.SauceOnDemandTestListener;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.URL;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

@Listeners({SauceOnDemandTestListener.class})
public class PeopleListTest implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

    public SauceOnDemandAuthentication authentication;

    private WebDriver driver;

    /**
     * If the tests can rely on the username/key to be supplied by environment variables or the existence
     * of a ~/.sauce-ondemand file, then we don't need to specify them as parameters, just create a new instance
     * of {@link com.saucelabs.common.SauceOnDemandAuthentication} using the no-arg constructor.
     * @param username
     * @param key
     * @param os
     * @param browser
     * @param browserVersion
     * @param method
     * @throws Exception
     */
    @Parameters({"username", "key", "os", "browser", "browserVersion"})
    @BeforeMethod
    public void setUp(@Optional("ivolf") String username,
                      @Optional("90e3bb89-c21d-4885-85cf-f25494db06ff") String key,
                      @Optional String os,
                      @Optional String browser,
                      @Optional String browserVersion,
                      Method method) throws Exception {

        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(key)) {
            authentication = new SauceOnDemandAuthentication(username, key);
        } else {
            authentication = new SauceOnDemandAuthentication();
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        capabilities.setCapability("version", browserVersion);
        capabilities.setCapability("platform", Platform.valueOf(os));
        capabilities.setCapability("name", method.getName());
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"), capabilities);
    }

    @Override
    public String getSessionId() {
        SessionId sessionId = ((RemoteWebDriver)driver).getSessionId();
        return (sessionId == null) ? null : sessionId.toString();
    }


    @Test
    public void testLoginSuccess () throws Exception {


        driver.get("https://alphaex.insynctiveapps.com");
        WebDriverWait wait = new WebDriverWait(driver, 5); // wait for a maximum of 5 seconds
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login_Login_CD")));
        driver.findElement(By.id("login_UserName_I")).sendKeys("ppetrea@mystaffdesk.com");
        driver.findElement(By.id("PasswordLabel")).click();
        driver.findElement(By.id("login_Password_I")).sendKeys("123qwe");
        driver.findElement(By.id("login_Login_CD")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tds_body_newsTab_AT0T")));
        assertTrue(isLoggedIn());
        assertEquals("GETTING STARTED", driver.findElement(By.id("tds_body_newsTab_AT0T")).getText());

    }
    //@Test
    public void testLoginFallure () throws Exception {


        driver.get("https://alphaex.insynctiveapps.com");
        WebDriverWait wait = new WebDriverWait(driver, 5); // wait for a maximum of 5 seconds
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login_Login_CD")));
        driver.findElement(By.id("login_UserName_I")).sendKeys("ppetrea@mystaffdesk.com");
        driver.findElement(By.id("PasswordLabel")).click();
        driver.findElement(By.id("login_Password_I")).sendKeys("123qwe");
        driver.findElement(By.id("login_Login_CD")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tds_body_newsTab_AT0T")));
        assertTrue(isLoggedIn());
        assertEquals("GETTING STARTED", driver.findElement(By.id("tds_body_newsTab_AT0T")).getText());

    }

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

    public boolean isLoggedIn() {
        return driver.findElements(By.id("tds_body_newsTab_AT0T")).size() > 0;
    }
}
