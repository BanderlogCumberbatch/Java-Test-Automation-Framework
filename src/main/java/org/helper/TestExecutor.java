// TestExecutor.java
package org.helper;

import org.junit.jupiter.api.Assertions;
import org.model.*;
import org.openqa.selenium.By;
import org.pages.BasePage;
import org.pages.TablePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class TestExecutor {
    private static final Logger logger = LoggerFactory.getLogger(TestExecutor.class);
    private final BasePage page = new BasePage();
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
        AssertMethod assertMethod;
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
            case GET_TEXT:
                locator = buildLocator(step.getElement());
                lastResult = page.getText(locator);
                assertMethod = step.getAssertMethod();
                if (Objects.nonNull(assertMethod)) {
                    performAssert(step);
                }
                break;
            case CALL_METHOD:
                lastResult = invokeTablePageMethod(step.getMethod(), step.getArgs());
                assertMethod = step.getAssertMethod();
                if (Objects.nonNull(assertMethod)) {
                    performAssert(step);
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

    private void performAssert(TestStep step) {
        switch (step.getAssertMethod()) {
            case EQUALS:
                if (step.getAssertType() == AssertType.SOFT) {
                    softAssert.assertEquals(lastResult, step.getExpected(), step.getName());
                } else {
                    Assertions.assertEquals(step.getExpected(), lastResult, step.getName());
                }
                break;
            case NOT_EQUALS:
                if (step.getAssertType() == AssertType.SOFT) {
                    softAssert.assertNotEquals(lastResult, step.getExpected(), step.getName());
                } else {
                    Assertions.assertNotEquals(step.getExpected(), lastResult, step.getName());
                }
                break;
            case ASSERT_TRUE:
                if (step.getAssertType() == AssertType.SOFT) {
                    softAssert.assertTrue((Boolean) lastResult, step.getName());
                } else {
                    Assertions.assertTrue((Boolean) lastResult, step.getName());
                }
                break;
            case ASSERT_FALSE:
                if (step.getAssertType() == AssertType.SOFT) {
                    softAssert.assertFalse((Boolean) lastResult, step.getName());
                } else {
                    Assertions.assertFalse((Boolean) lastResult, step.getName());
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported assert: " + step.getAssertMethod());
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
