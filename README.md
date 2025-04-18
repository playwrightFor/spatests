# SPA Project Test Automation

## Описание

Этот проект предназначен для автоматизации тестирования одностраничного приложения (SPA) с использованием Playwright и JUnit 5. Тесты проверяют функциональность приложения TodoMVC, включая добавление, завершение и удаление задач.

## Структура проекта
```
src/test/java/
├── pages/
│  └── TodoPage.java
│
│   tests/
│  └── SPAProjectTest.java
│      AddAndCompleteTodoTest.java
│      AddEmptyTodoTest.java
│      ClearCompletedTest.java
│      PersistenceAfterReloadTest.java
pom.xml
```
Проект для автоматизированного тестирования Single-Page Application (TodoMVC) с использованием:
- Playwright 1.50.0
- Java 17
- JUnit 5
- Maven
- Allure Report

## Особенности проекта
✅ Тестирование SPA с динамическим контентом  
✅ Параллельный запуск тестов  
✅ Page Object Model  
✅ Интеграция с Allure Reporting  
✅ Обработка различных сценариев (включая граничные случаи)

## Установка

### Предварительные требования

- Java 17 или выше
- Maven 3.6.0 или выше
- Playwright

### Шаги по установке

1. Клонируйте репозиторий:

   ```bash
   git clone <URL вашего репозитория>
   cd <имя_папки_репозитория>
   ```

2. Убедитесь, что все зависимости установлены:

   ```bash
   mvn clean install
   ```

## Запуск тестов

Чтобы запустить тесты, используйте следующую команду:

```bash
mvn test
```

Для генерации отчета Allure выполните:

```bash
mvn allure:report
```

Чтобы открыть отчет в браузере, выполните:

```bash
mvn allure:serve
```

## Тесты

### SPAProjectTest

Этот класс содержит тесты для проверки функциональности приложения TodoMVC.

#### Тестовые методы

- `testAddAndCompleteTodo()`
    - Проверяет добавление задач и их завершение.

- `testClearCompleted()`
    - Проверяет функциональность очистки завершенных задач.

- `testPersistenceAfterReload()`
    - Проверяет, что состояние задач сохраняется после перезагрузки страницы.

- `testAddEmptyTodo()`
    - Проверяет, что пустая задача не добавляется.

### TodoPage

Этот класс представляет страницу TodoMVC и содержит методы для взаимодействия с элементами страницы.

#### Методы

- `navigate()`
    - Переходит на страницу TodoMVC.

- `addTodo(String todoText)`
    - Добавляет новую задачу.

- `completeTodo(int index)`
    - Завершает задачу по индексу.

- `filterCompleted()`
    - Фильтрует отображаемые задачи, показывая только завершенные.

- `filterActive()`
    - Фильтрует отображаемые задачи, показывая только активные.

- `clearCompleted()`
    - Очищает завершенные задачи.

- `getTodosCount()`
    - Возвращает общее количество задач.

- `getVisibleTodosCount()`
    - Возвращает количество видимых задач.

- `reloadAndKeepState()`
    - Перезагружает страницу и сохраняет состояние задач.

## Настройка Allure

Если у вас возникают проблемы с генерацией отчета Allure, убедитесь, что вы используете совместимые версии зависимостей в вашем `pom.xml`. Например:

```xml
<properties>
    <allure.version>2.25.0</allure.version>
    <allure-maven.version>2.12.0</allure-maven.version>
    <junit.platform.version>1.10.0</junit.platform.version>
    <surefire.version>3.2.5</surefire.version>
    <compiler.version>3.11.0</compiler.version>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

## Заключение

Этот проект демонстрирует подход к автоматизации тестирования SPA с использованием Playwright и JUnit. Вы можете расширять и модифицировать тесты в соответствии с вашими требованиями.
```
