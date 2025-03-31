package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import com.microsoft.playwright.*;

/**
 * Класс TodoPage представляет страницу приложения TodoMVC.
 * Он содержит методы для взаимодействия с элементами страницы и управления задачами.
 */
public class TodoPage {
    private final Page page;

    /**
     * Конструктор класса TodoPage.
     *
     * @param page экземпляр страницы Playwright, с которой будет работать класс.
     */
    public TodoPage(Page page) {
        this.page = page;
    }

    /**
     * Навигация к главной странице TodoMVC.
     * Ожидает, пока элемент с классом ".todoapp" будет прикреплен к DOM.
     */
    public void navigate() {
        page.navigate("https://demo.playwright.dev/todomvc/#/");
        page.waitForSelector(".todoapp", new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(10000));
    }

    /**
     * Добавляет новую задачу в список дел.
     *
     * @param todoText текст задачи, которую необходимо добавить.
     *                 Если текст пустой или состоит только из пробелов, задача не добавляется.
     */
    public void addTodo(String todoText) {
        int initialCount = getTodosCount();
        page.locator(".new-todo").fill(todoText);
        page.locator(".new-todo").press("Enter");

        if (!todoText.trim().isEmpty()) {
            page.waitForFunction(
                    "([expected, text]) => {"
                            + "const items = Array.from(document.querySelectorAll('.todo-list li'));"
                            + "return items.length === expected && items.some(i => i.querySelector('label').textContent === text;}",
                    new Object[]{initialCount + 1, todoText},
                    new Page.WaitForFunctionOptions().setTimeout(10000)
            );
        }
    }

    /**
     * Завершает задачу по указанному индексу в списке.
     *
     * @param index индекс задачи, которую нужно завершить.
     */
    public void completeTodo(int index) {
        page.locator(".todo-list li").nth(index)
                .getByRole(AriaRole.CHECKBOX)
                .click();
        page.waitForTimeout(200);
    }

    /**
     * Применяет фильтр для отображения завершенных задач.
     */
    public void filterCompleted() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Completed")).click();
        waitForFilterApplied("#/completed");
    }

    /**
     * Применяет фильтр для отображения активных задач.
     */
    public void filterActive() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Active")).click();
        waitForFilterApplied("#/active");
    }

    /**
     * Очищает завершенные задачи из списка.
     */
    public void clearCompleted() {
        page.waitForSelector(".clear-completed:visible", new Page.WaitForSelectorOptions()
                .setTimeout(10000));
        page.locator(".clear-completed").click();
        page.waitForTimeout(500);
    }

    /**
     * Возвращает общее количество задач в списке.
     *
     * @return количество задач.
     */
    public int getTodosCount() {
        return page.locator(".todo-list li").count();
    }

    /**
     * Возвращает количество видимых задач в списке.
     *
     * @return количество видимых задач.
     */
    public int getVisibleTodosCount() {
        return page.locator(".todo-list li:visible").count();
    }

    /**
     * Перезагружает страницу и сохраняет состояние задач.
     * Ожидает, пока элемент с классом ".todo-list" будет прикреплен к DOM.
     */
    public void reloadAndKeepState() {
        page.reload();
        page.waitForSelector(".todo-list", new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(15000));
    }

    /**
     * Ожидает применения фильтра, проверяя хэш URL.
     *
     * @param filter ожидаемое значение хэша URL.
     */
    private void waitForFilterApplied(String filter) {
        page.waitForFunction(
                "expected => window.location.hash === expected",
                filter,
                new Page.WaitForFunctionOptions().setTimeout(5000)
        );
    }
}
