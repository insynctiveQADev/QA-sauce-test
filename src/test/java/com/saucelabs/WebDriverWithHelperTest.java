package com.saucelabs;

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

import static junit.framework.Assert.fail;
import static org.testng.Assert.assertEquals;
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
public class WebDriverWithHelperTest implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

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

    //@Test
    public void webDriverWithHelper() throws Exception {
        driver.get("http://www.amazon.com/");
        assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");
    }

    @Test
    public void registerTest() throws Exception {
        driver.get("https://alpha.insynctiveapps.com/Insynctive.Hub/Login.aspx?ReturnUrl=%2fInsynctive.Hub%2fProtected%2fInvitations.aspx%3fpage&page");
        driver.findElement(By.xpath("//input[@id='login_UserName_I']")).sendKeys("ivolf@insynctive.com");
        driver.findElement(By.id("PasswordLabel")).click();
        driver.findElement(By.id("login_Password_I")).sendKeys("Iakov1893250");
        driver.findElement(By.id("login_Login_CD")).click();
        driver.findElement(By.xpath("//input[@name='login$Login']")).click();
        driver.findElement(By.id("tds_img_Invitations")).click();
        driver.findElement(By.xpath("//div[@id='tds_body_mainTab_TPTCR_btnAddPerson_0_CD']/span")).click();
        driver.findElement(By.id("tds_body_popupAddPerson_txtFirstName_I")).clear();
        driver.findElement(By.id("tds_body_popupAddPerson_txtFirstName_I")).sendKeys("Morgan");
        driver.findElement(By.id("tds_body_popupAddPerson_txtLastName_I")).clear();
        driver.findElement(By.id("tds_body_popupAddPerson_txtLastName_I")).sendKeys("Freeman");
        driver.findElement(By.id("tds_body_popupAddPerson_txtEmail_I")).clear();
        driver.findElement(By.id("tds_body_popupAddPerson_txtEmail_I")).sendKeys("volf1212+4@gmail.com");
        driver.findElement(By.id("tds_body_popupAddPerson_chkInvite_S_D")).click();
        driver.findElement(By.id("tds_body_popupAddPerson_btnAddHRAdmin")).click();
        driver.findElement(By.xpath("//input[@name='ctl00$ctl00$tds$body$popupAddPerson$btnAddHRAdmin']")).click();
        driver.findElement(By.id("txtNewPassword_I")).clear();
        driver.findElement(By.id("txtNewPassword_I")).sendKeys("Iakov1893250");
        driver.findElement(By.id("txtConfirmNewPassword_I")).clear();
        driver.findElement(By.id("txtConfirmNewPassword_I")).sendKeys("Iakov1893250");
        driver.findElement(By.xpath("//div[@id='btnChangePassword_CD']/span")).click();
        driver.findElement(By.xpath("//input[@name='btnChangePassword']")).click();
        driver.findElement(By.id("pcMessage_TPCFm1_btnOk_CD")).click();
        driver.findElement(By.xpath("//input[@name='pcMessage$TPCFm1$ctl00$btnOk']")).click();
        driver.findElement(By.id("tds_body_newsTab_TPTCR_imgMore_0")).click();
        try {
            assertTrue(driver.findElement(By.id("lblName")).getText().matches("^[\\s\\S]*Morgan[\\s\\S]*Freeman[\\s\\S]*$"));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        // ERROR: Caught exception [Error: locator strategy either id or name must be specified explicitly.]
    }
    @Test
    public void testLoginSuccsess () throws Exception {


        driver.get("https://alpha.insynctiveapps.com");
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (isElementPresent(By.id("login_Login_CD"))) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.id("login_UserName_I")).sendKeys("ppetrea@mystaffdesk.com");
        driver.findElement(By.id("PasswordLabel")).click();
        driver.findElement(By.id("login_Password_I")).sendKeys("123qwe");
        driver.findElement(By.id("login_Login_CD")).click();
        for (int second = 0;; second++) {
            if (second >= 70) fail("timeout");
            try { if (isElementPresent(By.id("tds_body_newsTab_AT0T"))) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }

        try {
            assertEquals("GETTING STARTED", driver.findElement(By.id("tds_body_newsTab_AT0T")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
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
}
