package com.saucelabs.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Iakov Volf on 2/16/2015.
 */
public class V3SettingsPage {
    public static final String PAGE_URL = "https://alphaex.insynctiveapps.com/Insynctive.Hub/Protected/AdminNotifications.aspx";
    WebDriver driver;

    @FindBy(id = "tds_div_AdminDirectory")
    WebElement V3SPeopleTabLink;


    public V3SettingsPage(WebDriver driver) {
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);
    }

    public void GoToPeopleTab() {
        V3SPeopleTabLink.click();

    }

}
