package me.loki2302.webdriver.standard;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class StandardWebDriverConfiguration {
    @Bean(destroyMethod = "quit")
    public WebDriver webDriver() {
        ChromeDriverManager.getInstance().setup();

        ChromeDriver chromeDriver = new ChromeDriver();
        chromeDriver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
        chromeDriver.manage().window().setSize(new Dimension(1366, 768));

        return chromeDriver;
    }

    @Bean
    public WebDriverWait webDriverWait() {
        return new WebDriverWait(webDriver(), 15);
    }
}
