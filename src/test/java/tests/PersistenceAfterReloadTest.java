package tests;

import base.BaseTest;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тестирование сохранения состояния после перезагрузки.
 * Проверяет сохранение данных задач между сессиями.
 */
public class PersistenceAfterReloadTest extends BaseTest {

    /**
     * Проверяет сохранение задач и их статусов после обновления страницы
     */
    @Test
    @DisplayName("Проверка сохранения состояния после перезагрузки")
    void testPersistenceAfterReload() {

        todoPage.addTodo("Persistent Task");
        todoPage.completeTodo(0);
        String initialText = getTaskText();

        todoPage.reloadAndKeepState();

        assertEquals(1, todoPage.getTodosCount(), "Количество задач после перезагрузки");
        assertTrue(getCheckboxState(), "Статус выполнения не сохранен");
        assertEquals(initialText, getTaskText(), "Текст задачи изменился");
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