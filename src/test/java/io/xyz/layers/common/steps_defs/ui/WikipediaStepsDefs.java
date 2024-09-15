package io.xyz.layers.common.steps_defs.ui;

import io.cucumber.java.en.*;
import io.xyz.pages.ui.WikipediaPage;
import io.xyz.utils.common_utils.ConfigurationReader;
import io.xyz.utils.common_utils.Driver;

import static org.junit.Assert.assertEquals;

public class WikipediaStepsDefs {
    WikipediaPage wikipediaPage = new WikipediaPage();
    String actual;


    @Given("User is on Wikipedia home page")
    public void user_is_on_wikipedia_home_page() {
        Driver.getDriver().get(ConfigurationReader.getProperties("wikipedia"));
    }
    @When("User types {string} in the wiki search box")
    public void user_types_in_the_wiki_search_box(String name) {
     wikipediaPage.searchBox.sendKeys(name);

    }
    @When("User clicks wiki search button")
    public void user_clicks_wiki_search_button() {
        wikipediaPage.searchButton.click();
    }
    @Then("User sees {string} is in the wiki title")
    public void user_sees_is_in_the_wiki_title(String string) {
        actual = Driver.getDriver().getTitle();
        assertEquals("Steve Jobs - Wikipedia", actual);
    }

}
