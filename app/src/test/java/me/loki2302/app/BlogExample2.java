package me.loki2302.app;

import me.loki2302.be.Todo;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = { AngularWebDriverConfiguration.class, App.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BlogExample2 {
    @Autowired
    private WebDriver webDriver;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void canDeleteATodo() {
        // GIVEN there are 3 todos
        todoRepository.save(Arrays.asList(
                makeTodo("todo one"),
                makeTodo("todo two"),
                makeTodo("todo three")
        ));

        webDriver.get("http://localhost:8080/");
        WebElement ul = webDriver.findElement(By.cssSelector("ul"));
        List<WebElement> liElements = ul.findElements(By.cssSelector("li"));
        assertEquals(3, liElements.size());

        // WHEN I delete 1 ("todo two")
        liElements.get(1).findElement(By.cssSelector("button")).click();

        // THEN 2 todos still remain in the browser...
        liElements = ul.findElements(By.cssSelector("li"));
        assertEquals(2, liElements.size());
        assertTrue(liElements.get(0).getText().contains("todo one"));
        assertTrue(liElements.get(1).getText().contains("todo three"));

        // ...and in the database
        assertEquals(2, todoRepository.count());
    }

    private static Todo makeTodo(String text) {
        Todo todo = new Todo();
        todo.text = text;
        return todo;
    }
}
