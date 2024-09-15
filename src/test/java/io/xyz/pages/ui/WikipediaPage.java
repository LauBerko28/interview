package io.xyz.pages.ui;

import io.xyz.utils.common_utils.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WikipediaPage {
    public WikipediaPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(id = "searchInput")
    public WebElement searchBox;

    @FindBy(xpath = "//button[@class='pure-button pure-button-primary-progressive']")
    public WebElement searchButton;

    @FindBy(id = "firstHeading")
    public WebElement firstHeading;

    @FindBy(className = "fn")
    public WebElement imageHeader;
}
