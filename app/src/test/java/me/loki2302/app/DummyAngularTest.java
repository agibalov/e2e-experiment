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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = { AngularWebDriverConfiguration.class, App.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DummyAngularTest {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private WebDriver webDriver;

    @Test
    public void pageHeaderShouldSayTodoList() {
        webDriver.get("http://localhost:8080/");
        WebElement h1 = webDriver.findElement(By.cssSelector("h1"));
        assertEquals("Todo list", h1.getText());
    }

    @Test
    public void pageShouldSayThereAreNoTodosWhenThereAreNoTodos() {
        webDriver.get("http://localhost:8080/");
        WebElement div = webDriver.findElement(By.cssSelector("div"));
        assertEquals("There are no todos", div.getText());
    }

    @Test
    public void pageShouldDisplayTodosWhenThereAreTodos() {
        for(String todoText : new String[] { "todo one", "todo two", "todo three" }) {
            Todo todo = new Todo();
            todo.text = todoText;
            todoRepository.save(todo);
        }

        webDriver.get("http://localhost:8080/");
        WebElement ul = webDriver.findElement(By.cssSelector("ul"));
        List<WebElement> liElements = ul.findElements(By.cssSelector("li"));
        assertEquals(3, liElements.size());
        assertTrue(liElements.get(0).getText().contains("todo one"));
        assertTrue(liElements.get(1).getText().contains("todo two"));
        assertTrue(liElements.get(2).getText().contains("todo three"));
    }

    @Test
    public void pageShouldAllowToCreateATodo() {
        webDriver.get("http://localhost:8080/");
        webDriver.findElement(By.cssSelector("input")).sendKeys("my dummy todo");
        webDriver.findElement(By.cssSelector("button[type=submit]")).click();
        List<Todo> todos = todoRepository.findAll();
        assertEquals(1, todos.size());
        assertEquals("my dummy todo", todos.get(0).text);
    }
}
