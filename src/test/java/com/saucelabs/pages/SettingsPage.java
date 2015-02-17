package com.saucelabs.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Iakov Volf on 2/16/2015.
 */
public class SettingsPage {
    WebDriver driver;

    @FindBy(id = "tds_body_btnAddList")
    WebElement addNewPersonsListButton;

    public SettingsPage(WebDriver driver) {

        this.driver = driver;

        //This initElements method will create all WebElements

        PageFactory.initElements(driver, this);

    }
}
