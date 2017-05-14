package me.loki2302.webdriver.angular;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.springframework.beans.factory.annotation.Autowired;

public class SynchronizingWebDriverEventListener extends AbstractWebDriverEventListener {
    @Autowired
    private AngularSynchronizer angularSynchronizer;

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        angularSynchronizer.synchronize(driver);
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        angularSynchronizer.synchronize(driver);
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        angularSynchronizer.synchronize(driver);
    }
}
