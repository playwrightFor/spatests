package tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import pages.TodoPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddEmptyTodoTest {
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
    @DisplayName("Проверка добавления пустой задачи")
    void testAddEmptyTodo() {
        int initialCount = todoPage.getTodosCount();
        todoPage.addTodo("   ");
        assertEquals(initialCount, todoPage.getTodosCount(), "Пустая задача не должна добавляться");
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
