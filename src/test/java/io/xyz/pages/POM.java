package io.xyz.pages;

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
