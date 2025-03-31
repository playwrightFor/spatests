package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Тестирование очистки выполненных задач.
 * Проверяет корректность удаления завершенных задач.
 */
public class ClearCompletedTest extends BaseTest {

    /**
     * Проверяет очистку задач и сохранение активных
     */
    @Test
    @DisplayName("Проверка очистки завершенных задач")
    void testClearCompleted() {

        todoPage.addTodo("Task to complete");
        todoPage.addTodo("Task to keep");
        todoPage.completeTodo(0);

        todoPage.clearCompleted();

        assertEquals(1, todoPage.getTodosCount(), "Неверное количество задач после очистки");
        assertEquals("Task to keep", getTaskText(),
                "Неверный текст оставшейся задачи");
        assertFalse(page.locator(".clear-completed").isVisible(),
                "Кнопка очистки должна быть скрыта");
    }

    /**
     * Возвращает текст задачи по индексу
     */
    private String getTaskText() {
        return page.locator(".todo-list li")
                .nth(0)
                .locator("label")
                .textContent()
                .trim();
    }
}