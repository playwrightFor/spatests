package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;
import pages.TodoPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SPAProjectTest {
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


    @Test
    void testClearCompleted() {
        todoPage.addTodo("Task to complete");
        todoPage.addTodo("Task to keep");

        todoPage.completeTodo(0);
        todoPage.clearCompleted();

        assertEquals(1, todoPage.getTodosCount());
        assertEquals("Task to keep",
                page.locator(".todo-list li").first().locator("label").textContent().trim());
    }

    @Test
    void testPersistenceAfterReload() {
        todoPage.addTodo("Persistent Task");
        todoPage.completeTodo(0);

        todoPage.reloadAndKeepState();

        assertEquals(1, todoPage.getTodosCount());
        assertTrue(page.locator(".todo-list li").first()
                .getByRole(AriaRole.CHECKBOX).isChecked());
    }

    @Test
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
