package base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import pages.TodoPage;

public abstract class BaseTest {
    static Playwright playwright;
    Browser browser;
    BrowserContext context;
    public Page page;
    protected TodoPage todoPage;

    @BeforeAll
    static void initPlaywright() {
        playwright = Playwright.create();
    }

    @BeforeEach
    void setupBrowser() {
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        todoPage = new TodoPage(page);
        todoPage.navigate();
    }

    @AfterEach
    void tearDown() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
    }

    @AfterAll
    static void closePlaywright() {
        if (playwright != null) playwright.close();
    }
}