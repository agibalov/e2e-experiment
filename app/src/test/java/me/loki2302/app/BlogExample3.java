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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = { AngularWebDriverConfiguration.class, App.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BlogExample3 {
    @Autowired
    private WebDriver webDriver;

    @MockBean
    private TodoRepository todoRepository;

    @Test
    public void canMakeSureThereAre3Todos() {
        // GIVEN there are 3 todos
        given(todoRepository.findAll()).willReturn(Arrays.asList(
                makeTodo(111, "mock data one"),
                makeTodo(222, "mock data two"),
                makeTodo(333, "mock data three")
        ));

        // WHEN I open the browser
        webDriver.get("http://localhost:8080/");

        // THEN indeed I see those 3 todos
        WebElement ul = webDriver.findElement(By.cssSelector("ul"));
        List<WebElement> liElements = ul.findElements(By.cssSelector("li"));
        assertEquals(3, liElements.size());
        assertTrue(liElements.get(0).getText().contains("mock data one"));
        assertTrue(liElements.get(1).getText().contains("mock data two"));
        assertTrue(liElements.get(2).getText().contains("mock data three"));
    }

    private static Todo makeTodo(long id, String text) {
        Todo todo = new Todo();
        todo.id = id;
        todo.text = text;
        return todo;
    }
}
