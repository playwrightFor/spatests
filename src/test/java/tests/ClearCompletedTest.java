package tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import pages.TodoPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearCompletedTest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    TodoPage todoPage;


    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
    }

    @BeforeEach
    void createContextAndPage() {
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        todoPage = new TodoPage(page);
        todoPage.navigate();
    }


    @Test
    @DisplayName("Проверка очистки завершенных задач")
    void testClearCompleted() {
        todoPage.addTodo("Task to complete");
        todoPage.addTodo("Task to keep");

        todoPage.completeTodo(0);
        todoPage.clearCompleted();

        assertEquals(1, todoPage.getTodosCount());
        assertEquals("Task to keep",
                page.locator(".todo-list li").first().locator("label").textContent().trim());
    }

    @AfterAll
    static void closeBrowser() {
        if (browser != null) {
            browser.close();
        }
        playwright.close();
    }


    @AfterEach
    void closeContext() {
        if (context != null) {
            context.close();
        }
    }
}
