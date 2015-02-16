package com.saucelabs;

/**
 * @author Ross Rowe
 */

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import com.saucelabs.testng.SauceOnDemandTestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static junit.framework.Assert.fail;
import static org.testng.Assert.assertEquals;


/**
 * Simple TestNG test which demonstrates being instantiated via a DataProvider in order to supply multiple browser combinations.
 *
 * @author Ross Rowe
 */
@Listeners({SauceOnDemandTestListener.class})
public class SauceTest implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {
    /**
     * Constructs a {@link com.saucelabs.common.SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link com.saucelabs.common.SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("ivolf", "90e3bb89-c21d-4885-85cf-f25494db06ff");
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private WebDriver driver;
    /**
     * ThreadLocal variable which contains the  {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

    /**
     * ThreadLocal variable which contains the Sauce Job Id.
     */
    private ThreadLocal<String> sessionId = new ThreadLocal<String>();

    /**
     * DataProvider that explicitly sets the browser combinations to be used.
     *
     * @param testMethod
     * @return
     */
    @DataProvider(name = "hardCodedBrowsers", parallel = false)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
               // new Object[]{"internet explorer", "11", "Windows 8.1"},
                new Object[]{"safari", "6", "OSX 10.8"},
        };
    }

    /**
     * /**
     * Constructs a new {@link RemoteWebDriver} instance which is configured to use the capabilities defined by the browser,
     * version and os parameters, and which is configured to run against ondemand.saucelabs.com, using
     * the username and access key populated by the {@link #authentication} instance.
     *
     * @param browser Represents the browser to be used as part of the test run.
     * @param version Represents the version of the browser to be used as part of the test run.
     * @param os Represents the operating system to be used as part of the test run.
     * @return
     * @throws MalformedURLException if an error occurs parsing the url
     */
    private WebDriver createDriver(String browser, String version, String os) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        if (version != null) {
            capabilities.setCapability(CapabilityType.VERSION, version);
        }
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        capabilities.setCapability("name", "Sauce Sample Test");
        webDriver.set(new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities));
        sessionId.set(((RemoteWebDriver) getWebDriver()).getSessionId().toString());
        return webDriver.get();
    }

    /**
     * Runs a simple test verifying the title of the amazon.com homepage.
     *
     * @param browser Represents the browser to be used as part of the test run.
     * @param version Represents the version of the browser to be used as part of the test run.
     * @param os Represents the operating system to be used as part of the test run.
     * @throws Exception if an error occurs during the running of the test
     */
    /*@Test(dataProvider = "hardCodedBrowsers")
    public void webDriver(String browser, String version, String os) throws Exception {
        WebDriver driver = createDriver(browser, version, os);
        driver.get("http://www.amazon.com/");
        assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");
        driver.quit();
    }*/
    @Test(dataProvider = "hardCodedBrowsers")
    public void testPasswordChange(String browser, String version, String os) throws Exception {
            WebDriver driver = createDriver(browser, version, os);
        driver.get("https://alpha.insynctiveapps.com/Insynctive.Hub/Login.aspx?ReturnUrl=%2fInsynctive.Hub%2f");
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (isElementPresent(By.id("login_UserName_I"))) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.id("login_UserName_I")).sendKeys("dzonovm+19@gmail.com");
        driver.findElement(By.id("PasswordLabel")).click();
        driver.findElement(By.id("login_Password_I")).sendKeys("test123");
        driver.findElement(By.id("login_Login_CD")).click();
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (isElementPresent(By.xpath("//img[@onclick='javascript:popupAccount.Show();']"))) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.xpath("//img[@onclick='javascript:popupAccount.Show();']")).click();
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (isElementPresent(By.id("popupAccount_linkChangePass"))) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.id("popupAccount_linkChangePass")).click();
        try {
            assertEquals("Change password", driver.findElement(By.id("lblTitle")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.id("txtOldPassword_I")).clear();
        driver.findElement(By.id("txtOldPassword_I")).sendKeys("test123");
        driver.findElement(By.id("txtNewPassword_I")).clear();
        driver.findElement(By.id("txtNewPassword_I")).sendKeys("test123");
        driver.findElement(By.id("txtConfirmNewPassword_I")).clear();
        driver.findElement(By.id("txtConfirmNewPassword_I")).sendKeys("test123");
        driver.findElement(By.xpath("//div[@id='btnChangePassword_CD']/span")).click();

        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (isElementPresent(By.id("pcMessage_lblMessage"))) break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        try {
            assertEquals("Your password for accessing our website was updated successfully.", driver.findElement(By.id("pcMessage_lblMessage")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.id("pcMessage_TPCFm1_btnOk_CD")).click();

    }

    /**
     * @return the {@link WebDriver} for the current thread
     */
    public WebDriver getWebDriver() {
        System.out.println("WebDriver" + webDriver.get());
        return webDriver.get();
    }

    /**
     *
     * @return the Sauce Job id for the current thread
     */
    public String getSessionId() {
        return sessionId.get();
    }

    /**
     *
     * @return the {@link SauceOnDemandAuthentication} instance containing the Sauce username/access key
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


}

