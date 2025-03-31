package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тестирование обработки пустых задач.
 * Проверяет, что задачи из пробелов не добавляются в список.
 */
public class AddEmptyTodoTest extends BaseTest {

    /**
     * Проверяет отсутствие изменений в списке после добавления пробелов
     */
    @Test
    @DisplayName("Проверка добавления пустой задачи")
    void testAddEmptyTodo() {
        int initialCount = todoPage.getTodosCount();

        todoPage.addTodo("   ");
        assertEquals(initialCount, todoPage.getTodosCount(),
                "Количество задач изменилось после добавления пустой задачи");

        assertTrue(verifyNoEmptyTasks(), "В списке присутствуют пустые задачи");
    }

    /**
     * Проверяет отсутствие задач с пустым текстом
     */
    private boolean verifyNoEmptyTasks() {
        return page.locator(".todo-list li label")
                .allTextContents()
                .stream()
                .noneMatch(String::isBlank);
    }
}