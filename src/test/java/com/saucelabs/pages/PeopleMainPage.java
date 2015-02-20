package com.saucelabs.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Iakov Volf on 2/16/2015.
 */
public class PeopleMainPage {
    WebDriver driver;

    @FindBy(id = "body_body_mainTab_AT0")
    WebElement peopleListPageLink;
    @FindBy(id = "body_body_mainTab_T1")
    WebElement peopleDirectoryPageLink;

    //Column Headers
    @FindBy(id = "ctl00_ctl00_body_body_mainTab_grdListPeople_col1")
    WebElement peopleFirstNameColumnHeader;
    @FindBy(id = "ctl00_ctl00_body_body_mainTab_grdListPeople_col2")
    WebElement peopleLastNameColumnHeader;
    @FindBy(id = "ctl00_ctl00_body_body_mainTab_grdListPeople_col3")
    WebElement peopleTitleColumnHeader;
    @FindBy(id = "ctl00_ctl00_body_body_mainTab_grdListPeople_col4")
    WebElement peopleDepartmentColumnHeader;
    @FindBy(id = "ctl00_ctl00_body_body_mainTab_grdListPeople_col5")
    WebElement peopleStatusColumnHeader;
    @FindBy(id = "ctl00_ctl00_body_body_mainTab_grdListPeople_col6")
    WebElement peopleOpenColumnHeader;

    //Column Inputs
    @FindBy(id = "ctl00_ctl00_body_body_mainTab_grdListPeople_DXFREditorcol1_I")
    WebElement peopleFirstNameColumnInput;
    @FindBy(id = "ctl00_ctl00_body_body_mainTab_grdListPeople_DXFREditorcol2_I")
    WebElement peopleLastNameColumnInput;
    @FindBy(id = "ctl00_ctl00_body_body_mainTab_grdListPeople_DXFREditorcol3_I")
    WebElement peopleTitleColumnInput;
    @FindBy(id = "ctl00_ctl00_body_body_mainTab_grdListPeople_DXFREditorcol4_I")
    WebElement peopleDepartmentColumnInput;
    @FindBy(id = "ctl00_ctl00_body_body_mainTab_grdListPeople_DXFREditorcol5_I")
    WebElement peopleStatusColumnInput;
    @FindBy(id = "ctl00_ctl00_body_body_mainTab_grdListPeople_DXDataRow0")
    WebElement peopleFirstDataRow;

    //Active menu
    @FindBy(xpath = "//*[@id='body_body_popupQuery_CSD-1']/table/tbody/tr[1]")
    WebElement peopleActiveMenuActive;
    @FindBy(xpath = "//*[@id='body_body_popupQuery_CSD-1']/table/tbody/tr[2]")
    WebElement peopleActiveMenuActiveUnlisted;
    @FindBy(xpath = "//*[@id='body_body_popupQuery_CSD-1']/table/tbody/tr[3]")
    WebElement peopleActiveMenuActivePendingFirstLogin;
    @FindBy(xpath = "//*[@id='body_body_popupQuery_CSD-1']/table/tbody/tr[4]")
    WebElement peopleActiveMenuActiveSelfService;
    @FindBy(xpath = "//*[@id='body_body_popupQuery_CSD-1']/table/tbody/tr[5]")
    WebElement peopleActiveMenuActiveWithoutSelfService;
    @FindBy(xpath = "//*[@id='body_body_popupQuery_CSD-1']/table/tbody/tr[6]")
    WebElement peopleActiveMenuInactive;


    public PeopleMainPage(WebDriver driver) {

        this.driver = driver;

        //This initElements method will create all WebElements

        PageFactory.initElements(driver, this);

    }

    public static PeopleMainPage navigateTo(WebDriver driver) {
        return PageFactory.initElements(driver,
                PeopleMainPage.class);
    }


}
