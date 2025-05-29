// TestExecutor.java
package org.helpers;

import org.junit.jupiter.api.Assertions;
import org.model.*;
import org.openqa.selenium.By;
import org.pages.Page;
import org.pages.TablePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class TestExecutor {
    private static final Logger logger = LoggerFactory.getLogger(TestExecutor.class);
    private final Page page = new Page();
    private final TablePage tablePage = new TablePage();
    private final SoftAssert softAssert = new SoftAssert();
    private Object lastResult;

    public void execute(TestCase testCase) {
        logger.info("Executing test case: {}", testCase.getName());
        for (TestStep step : testCase.getSteps()) {
            try {
                executeStep(step);
                logger.info("Step '{}' completed successfully", step.getName());
            } catch (Exception e) {
                logger.error("Error executing step '{}': {}", step.getName(), e.getMessage());
                throw new RuntimeException("Test execution failed", e);
            }
        }
        // В конце тест-кейса вызываем assertAll() для мягких ассертов
        softAssert.assertAll();
    }

    private void executeStep(TestStep step) {
        By locator;
        Assert assertAction;
        switch (step.getAction()) {
            case OPEN:
                String url = step.getUrl();
                if (Objects.nonNull(url)) {
                    page.open(ConfigManager.getResolvedKey(url));
                }
                else {
                    page.open();
                }
                break;
            case TYPE:
                locator = buildLocator(step.getElement());
                page.type(locator, ConfigManager.getResolvedKey(step.getValue()));
                break;
            case CLICK:
                page.click(buildLocator(step.getElement()));
                break;
            case ACCEPT_ALERT:
                page.acceptAlert();
                break;
            case CLEAR_FIELD:
                page.clearField(buildLocator(step.getElement()));
                break;
            case GET_TEXT:
                locator = buildLocator(step.getElement());
                lastResult = page.getText(locator);
                assertAction = step.getAssertAction();
                if (Objects.nonNull(assertAction)) {
                    performAssert(assertAction, step.getName());
                }
                break;
            case CHECK_VISIBLE:
                locator = buildLocator(step.getElement());
                lastResult = page.checkElementVisible(locator);
                assertAction = step.getAssertAction();
                if (Objects.nonNull(assertAction)) {
                    performAssert(assertAction, step.getName());
                }
                break;
            case CHECK_ENABLED:
                locator = buildLocator(step.getElement());
                lastResult = page.checkElementEnabled(locator);
                assertAction = step.getAssertAction();
                if (Objects.nonNull(assertAction)) {
                    performAssert(assertAction, step.getName());
                }
                break;
            case GET_URL:
                lastResult = page.getCurrentUrl();
                assertAction = step.getAssertAction();
                if (Objects.nonNull(assertAction)) {
                    performAssert(assertAction, step.getName());
                }
                break;
            case NAV_FORWARD:
                page.navigateForward();
                break;
            case NAV_BACK:
                page.navigateBack();
                break;
            case REFRESH_PAGE:
                page.refreshPage();
                break;
            case REFRESH:
                BrowserManager.refresh();
                break;
            case QUIT:
                BrowserManager.quit();
                break;
            case GET_TABLE_ROW:
                lastResult = invokeTablePageMethod("getSelectedRowString", step.getArgs());
                assertAction = step.getAssertAction();
                if (Objects.nonNull(assertAction)) {
                    performAssert(assertAction, step.getName());
                }
                break;
            case GET_TABLE_COLUMN:
                lastResult = invokeTablePageMethod("getColumnElementsList", step.getArgs());
                assertAction = step.getAssertAction();
                if (Objects.nonNull(assertAction)) {
                    performAssert(assertAction, step.getName());
                }
                break;
            case GET_TABLE_ELEMENT:
                lastResult = invokeTablePageMethod("getSelectedElementOfColumn", step.getArgs());
                assertAction = step.getAssertAction();
                if (Objects.nonNull(assertAction)) {
                    performAssert(assertAction, step.getName());
                }
                break;
            case SORT_TABLE_COLUMN:
                lastResult = invokeTablePageMethod("sortByColumn", step.getArgs());
                assertAction = step.getAssertAction();
                if (Objects.nonNull(assertAction)) {
                    performAssert(assertAction, step.getName());
                }
                break;
            case CHECK_COLUMN_SORTED:
                lastResult = invokeTablePageMethod("isSortedByColumn", step.getArgs());
                assertAction = step.getAssertAction();
                if (Objects.nonNull(assertAction)) {
                    performAssert(assertAction, step.getName());
                }
                break;
            case CHECK_COLUMN_SORTED_REV:
                lastResult = invokeTablePageMethod("isSortedByColumnInReverse", step.getArgs());
                assertAction = step.getAssertAction();
                if (Objects.nonNull(assertAction)) {
                    performAssert(assertAction, step.getName());
                }
                break;
            case CLICK_TABLE_ELEMENT:
                lastResult = invokeTablePageMethod("deleteElement", step.getArgs());
                assertAction = step.getAssertAction();
                if (Objects.nonNull(assertAction)) {
                    performAssert(assertAction, step.getName());
                }
                break;
            case CALL_METHOD:
                lastResult = invokeTablePageMethod(step.getMethod(), step.getArgs());
                assertAction = step.getAssertAction();
                if (Objects.nonNull(assertAction)) {
                    performAssert(assertAction, step.getName());
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported action: " + step.getAction());
        }
    }

    private By buildLocator(Element element) {
        String value = ConfigManager.getResolvedKey(element.getValue());
        return switch (element.getBy()) {
            case CSS -> By.cssSelector(value);
            case XPATH -> By.xpath(value);
            case ID -> By.id(value);
            case NAME -> By.name(value);
            case CLASS_NAME -> By.className(value);
            case TAG_NAME -> By.tagName(value);
            default -> throw new IllegalArgumentException("Unsupported locator type");
        };
    }

    private void performAssert(Assert assertAction, String assertName) {
        switch (assertAction.getMethod()) {
            case EQUALS:
                if (assertAction.getSoft()) {
                    softAssert.assertEquals(lastResult, assertAction.getExpected(), assertName);
                } else {
                    Assertions.assertEquals(assertAction.getExpected(), lastResult, assertName);
                }
                break;
            case NOT_EQUALS:
                if (assertAction.getSoft()) {
                    softAssert.assertNotEquals(lastResult, assertAction.getExpected(), assertName);
                } else {
                    Assertions.assertNotEquals(assertAction.getExpected(), lastResult, assertName);
                }
                break;
            case ASSERT_TRUE:
                if (assertAction.getSoft()) {
                    softAssert.assertTrue((Boolean) lastResult, assertName);
                } else {
                    Assertions.assertTrue((Boolean) lastResult, assertName);
                }
                break;
            case ASSERT_FALSE:
                if (assertAction.getSoft()) {
                    softAssert.assertFalse((Boolean) lastResult, assertName);
                } else {
                    Assertions.assertFalse((Boolean) lastResult, assertName);
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported assert: " + assertAction.getMethod());
        }
    }

    private Object invokeTablePageMethod(String methodName, List<Object> args) {
        try {
            Class<?>[] paramTypes = new Class[args.size()];
            Object[] params = new Object[args.size()];
            for (int i = 0; i < args.size(); i++) {
                paramTypes[i] = args.get(i).getClass();
                params[i] = args.get(i);
            }
            Method method = TablePage.class.getMethod(methodName, paramTypes);
            return method.invoke(tablePage, params);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка вызова метода: " + methodName, e);
        }
    }
}
