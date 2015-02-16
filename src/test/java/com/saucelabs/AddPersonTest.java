package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import com.saucelabs.testng.SauceOnDemandTestListener;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.URL;

import static junit.framework.Assert.fail;
import static org.testng.Assert.assertEquals;


/**
 * Simple {@link org.openqa.selenium.remote.RemoteWebDriver} test that demonstrates how to run your Selenium tests with <a href="http://saucelabs.com/ondemand">Sauce OnDemand</a>.
 * <p/>
 * This test also includes the <a href="https://github.com/saucelabs/sauce-java/tree/master/testng">Sauce TestNG</a> helper classes, which will use the Sauce REST API to mark the Sauce Job as passed/failed.
 * <p/>
 * In order to use the {@link com.saucelabs.testng.SauceOnDemandTestListener}, the test must implement the {@link com.saucelabs.common.SauceOnDemandSessionIdProvider} and {@link com.saucelabs.testng.SauceOnDemandAuthenticationProvider} interfaces.
 */
@Listeners({SauceOnDemandTestListener.class})
public class AddPersonTest extends TestBase implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

    public SauceOnDemandAuthentication authentication;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private WebDriver driver;

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
    public void addingPersonWithoutEmailAddress_492() throws Exception {
        driver.get("https://alphaex.insynctiveapps.com/Insynctive.Hub/Login.aspx?ReturnUrl=%2fInsynctive.Hub%2f");
        login();
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if (isElementPresent(By.id("login_UserName_I"))) break;
            } catch (Exception e) {
            }
            Thread.sleep(200);
        }

        driver.findElement(By.id("login_UserName_I")).clear();
        driver.findElement(By.id("login_UserName_I")).sendKeys("bpetrovski@insynctive.com");
        driver.findElement(By.id("PasswordLabel")).click();
        driver.findElement(By.id("login_Password_I")).clear();
        driver.findElement(By.id("login_Password_I")).sendKeys("apple$$$2405");

        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if (isElementPresent(By.xpath("//div[@id='body_body_mainTab_TPTCR_btnAddPerson_0_CD']/span"))) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }

        driver.findElement(By.xpath("//div[@id='body_body_mainTab_TPTCR_btnAddPerson_0_CD']/span")).click();
        driver.findElement(By.id("body_body_popupAddPerson_txtFirstName_I")).clear();
        driver.findElement(By.id("body_body_popupAddPerson_txtFirstName_I")).sendKeys("Test");
        driver.findElement(By.id("body_body_popupAddPerson_txtLastName_I")).clear();
        driver.findElement(By.id("body_body_popupAddPerson_txtLastName_I")).sendKeys("Automated");
        driver.findElement(By.xpath("//div[@id='body_body_popupAddPerson_btnAddHRAdmin_CD']/span")).click();
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if (isElementPresent(By.id("body_lblPersonName"))) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }

        try {
            assertEquals("Test Automated", driver.findElement(By.id("body_lblPersonName")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.id("popupCustom_TPCHm1_imgClose")).click();
        driver.findElement(By.id("body_body_mainTab_TPTCR_btnAddPerson_0")).click();
        driver.findElement(By.id("body_body_popupAddPerson_txtFirstName_I")).clear();
        driver.findElement(By.id("body_body_popupAddPerson_txtFirstName_I")).sendKeys("Test");
        driver.findElement(By.id("body_body_popupAddPerson_txtEmail_I")).clear();
        driver.findElement(By.id("body_body_popupAddPerson_txtEmail_I")).sendKeys("test@gmail.com");
        driver.findElement(By.xpath("//div[@id='body_body_popupAddPerson_btnAddHRAdmin_CD']/span")).click();
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if (isElementPresent(By.id("body_body_popupAddPerson_txtLastName_EC"))) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }

        try {
            assertEquals("Last Name is mandatory", driver.findElement(By.id("body_body_popupAddPerson_txtLastName_EC")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.id("body_body_popupAddPerson_txtFirstName_I")).clear();
        driver.findElement(By.id("body_body_popupAddPerson_txtFirstName_I")).sendKeys("");
        driver.findElement(By.id("body_body_popupAddPerson_txtLastName_I")).clear();
        driver.findElement(By.id("body_body_popupAddPerson_txtLastName_I")).sendKeys("Test");
        driver.findElement(By.id("body_body_popupAddPerson_btnAddHRAdmin_CD")).click();
        try {
            assertEquals("First Name is mandatory", driver.findElement(By.id("body_body_popupAddPerson_txtFirstName_EC")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.xpath("//div[@id='body_body_popupAddPerson_ASPxButton1_CD']/span")).click();
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
     *
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
