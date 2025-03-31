//package tests;
//
//import com.microsoft.playwright.options.AriaRole;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import com.microsoft.playwright.*;
//import pages.TodoPage;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Класс SPAProjectTest предназначен для автоматизации тестирования
// * одностраничного приложения (SPA) TodoMVC с использованием Playwright и JUnit.
// * В этом классе проводятся тесты для проверки функциональности приложения,
// * включая добавление, завершение и удаление задач.
// */
//public class SPAProjectTest {
//    static Playwright playwright;
//    static Browser browser;
//    BrowserContext context;
//    Page page;
//    TodoPage todoPage;
//
//    @BeforeAll
//    static void launchBrowser() {
//        playwright = Playwright.create();
//    }
//
//    @BeforeEach
//    void createContextAndPage() {
//        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
//        context = browser.newContext();
//        page = context.newPage();
//        todoPage = new TodoPage(page);
//        todoPage.navigate();
//    }
//
//    @Test
//    @DisplayName("Проверка добавления и завершения задач")
//    void testAddAndCompleteTodo() {
//        todoPage.addTodo("Task 1");
//        assertEquals(1, todoPage.getTodosCount(), "Количество задач после добавления первой задачи");
//
//        todoPage.addTodo("Task 2");
//        assertEquals(2, todoPage.getTodosCount(), "Количество задач после добавления второй задачи");
//
//        todoPage.completeTodo(0);
//        todoPage.filterCompleted();
//
//        assertEquals(1, todoPage.getVisibleTodosCount(), "Количество выполненных задач должно быть 1");
//
//        todoPage.filterActive();
//        assertEquals(1, todoPage.getVisibleTodosCount(), "Количество активных задач должно быть 1");
//    }
//
//
//    @Test
//    @DisplayName("Проверка очистки завершенных задач")
//    void testClearCompleted() {
//        todoPage.addTodo("Task to complete");
//        todoPage.addTodo("Task to keep");
//
//        todoPage.completeTodo(0);
//        todoPage.clearCompleted();
//
//        assertEquals(1, todoPage.getTodosCount());
//        assertEquals("Task to keep",
//                page.locator(".todo-list li").first().locator("label").textContent().trim());
//    }
//
//
//    @Test
//    @DisplayName("Проверка сохранения состояния после перезагрузки")
//    void testPersistenceAfterReload() {
//        todoPage.addTodo("Persistent Task");
//        todoPage.completeTodo(0);
//
//        todoPage.reloadAndKeepState();
//
//        assertEquals(1, todoPage.getTodosCount());
//        assertTrue(page.locator(".todo-list li").first()
//                .getByRole(AriaRole.CHECKBOX).isChecked());
//    }
//
//
//    @Test
//    @DisplayName("Проверка добавления пустой задачи")
//    void testAddEmptyTodo() {
//        int initialCount = todoPage.getTodosCount();
//        todoPage.addTodo("   ");
//        assertEquals(initialCount, todoPage.getTodosCount(), "Пустая задача не должна добавляться");
//    }
//
//
//    @AfterAll
//    static void closeBrowser() {
//        if (browser != null) {
//            browser.close();
//        }
//        playwright.close();
//    }
//
//    @AfterEach
//    void closeContext() {
//        if (context != null) {
//            context.close();
//        }
//    }
//}
