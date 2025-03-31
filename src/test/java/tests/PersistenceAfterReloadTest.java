package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;
import pages.TodoPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersistenceAfterReloadTest {

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
    @DisplayName("Проверка сохранения состояния после перезагрузки")
    void testPersistenceAfterReload() {
        todoPage.addTodo("Persistent Task");
        todoPage.completeTodo(0);

        todoPage.reloadAndKeepState();

        assertEquals(1, todoPage.getTodosCount());
        assertTrue(page.locator(".todo-list li").first()
                .getByRole(AriaRole.CHECKBOX).isChecked());
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
