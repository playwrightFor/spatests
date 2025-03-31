package tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import pages.TodoPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddAndCompleteTodoTest {
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
    @DisplayName("Проверка добавления и завершения задач")
    void testAddAndCompleteTodo() {
        todoPage.addTodo("Task 1");
        assertEquals(1, todoPage.getTodosCount(), "Количество задач после добавления первой задачи");

        todoPage.addTodo("Task 2");
        assertEquals(2, todoPage.getTodosCount(), "Количество задач после добавления второй задачи");

        todoPage.completeTodo(0);
        todoPage.filterCompleted();

        assertEquals(1, todoPage.getVisibleTodosCount(), "Количество выполненных задач должно быть 1");

        todoPage.filterActive();
        assertEquals(1, todoPage.getVisibleTodosCount(), "Количество активных задач должно быть 1");
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
