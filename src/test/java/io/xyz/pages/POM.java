package io.xyz.pages;
// Implementing singleton pattern to re-use an existing instance of an object, else to create a new one of the same
import io.xyz.layers.common.pages.ui.UiBasePage;

public class POM {

    private UiBasePage uiBasePage;

    public UiBasePage createInstanceOfBasePage() {
        if(uiBasePage == null) {
            uiBasePage = new UiBasePage();
        }
        return uiBasePage;
    }
}
