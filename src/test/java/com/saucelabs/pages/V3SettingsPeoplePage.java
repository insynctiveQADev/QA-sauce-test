package com.saucelabs.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Iakov Volf on 2/12/2015.
 */
public class V3SettingsPeoplePage {
    WebDriver driver;

    @FindBy(id = "tds_body_btnAddList")
    WebElement addNewPersonsListButton;
    @FindBy(id = "tds_body_btnAddList_CD")
    WebElement newListListNameField;
    @FindBy(id = "tds_body_popupAddList_btnSaveList_CD")
    WebElement newListOKButton;
    @FindBy(id = "tds_body_popupAddList_btnCancelSaveList_CD")
    WebElement newListCancelButton;

    public V3SettingsPeoplePage(WebDriver driver) {

        this.driver = driver;

        //This initElements method will create all WebElements

        PageFactory.initElements(driver, this);

    }


}
