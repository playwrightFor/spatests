package tests;

import base.BaseTest;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Flaky;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тестирование функционала добавления и завершения задач.
 * Проверяет:
 * - Корректность добавления задач
 * - Отметку задач как выполненных
 * - Работу фильтров Completed/Active
 */
public class AddAndCompleteTodoTest extends BaseTest {
    /**
     * Тест последовательно проверяет добавление задач, их выполнение и фильтрацию
     */
    @Test
    @DisplayName("Проверка добавления и завершения задач")
    void testAddAndCompleteTodo() {
        todoPage.addTodo("Task 1");
        assertEquals(1, todoPage.getTodosCount(), "Неверное количество задач после первого добавления");

        todoPage.addTodo("Task 2");
        assertEquals(2, todoPage.getTodosCount(), "Неверное количество задач после второго добавления");

        todoPage.completeTodo(0);
        assertTrue(getCheckboxState(), "Чекбокс должен быть отмечен после выполнения");

        testFilter("Completed");
        testFilter("Active");
    }

    /**
     * Проверяет работу конкретного фильтра
     */
    private void testFilter(String filterName) {
        switch (filterName) {
            case "Completed" -> todoPage.filterCompleted();
            case "Active" -> todoPage.filterActive();
        }
        assertEquals(1, todoPage.getVisibleTodosCount(),
                "Неверное количество задач для фильтра: " + filterName);
    }

    /**
     * Возвращает состояние чекбокса задачи по индексу
     */
    private boolean getCheckboxState() {
        return page.locator(".todo-list li")
                .nth(0)
                .getByRole(AriaRole.CHECKBOX)
                .isChecked();
    }
}