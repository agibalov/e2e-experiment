package me.loki2302.app;

import me.loki2302.be.TodoRepository;
import me.loki2302.webdriver.angular.AngularWebDriverConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = { AngularWebDriverConfiguration.class, App.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BlogExample1 {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private WebDriver webDriver;

    @Test
    public void canCreateATodo() {
        // GIVEN there are no todos

        // WHEN I create one
        webDriver.get("http://localhost:8080/");
        webDriver.findElement(By.cssSelector("input")).sendKeys("test todo");
        webDriver.findElement(By.cssSelector("button[type=submit]")).click();

        // THEN it appears on the list
        WebElement ul = webDriver.findElement(By.cssSelector("ul"));
        List<WebElement> liElements = ul.findElements(By.cssSelector("li"));
        assertEquals(1, liElements.size());
        assertTrue(liElements.get(0).getText().contains("test todo"));
    }
}
