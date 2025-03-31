package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class TodoPage {
    private final Page page;

    public TodoPage(Page page) {
        this.page = page;
    }

    public void navigate() {
        page.navigate("https://demo.playwright.dev/todomvc/#/");
        page.waitForSelector(".todoapp", new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(10000));
    }


    public void addTodo(String todoText) {
        int initialCount = getTodosCount();
        page.locator(".new-todo").fill(todoText);
        page.locator(".new-todo").press("Enter");

        if (!todoText.trim().isEmpty()) {
            page.waitForFunction(
                    "([expected, text]) => {"
                            + "const items = Array.from(document.querySelectorAll('.todo-list li'));"
                            + "return items.length === expected && items.some(i => i.querySelector('label').textContent === text);}",
                    new Object[]{initialCount + 1, todoText},
                    new Page.WaitForFunctionOptions().setTimeout(10000)
            );
        }
    }

    public void completeTodo(int index) {
        page.locator(".todo-list li").nth(index)
                .getByRole(AriaRole.CHECKBOX)
                .click();
        page.waitForTimeout(200);
    }

    public void filterCompleted() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Completed")).click();
        waitForFilterApplied("#/completed");
    }

    public void filterActive() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Active")).click();
        waitForFilterApplied("#/active");
    }

    public void clearCompleted() {
        page.waitForSelector(".clear-completed:visible", new Page.WaitForSelectorOptions()
                .setTimeout(10000));
        page.locator(".clear-completed").click();
        page.waitForTimeout(500);
    }

    public int getTodosCount() {
        return page.locator(".todo-list li").count();
    }

    public int getVisibleTodosCount() {
        return page.locator(".todo-list li:visible").count();
    }

    public void reloadAndKeepState() {
        page.reload();
        page.waitForSelector(".todo-list", new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(15000));
    }

    private void waitForFilterApplied(String filter) {
        page.waitForFunction(
                "expected => window.location.hash === expected",
                filter,
                new Page.WaitForFunctionOptions().setTimeout(5000)
        );
    }
}