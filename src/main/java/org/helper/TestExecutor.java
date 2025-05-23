// TestExecutor.java
package org.helper;

import org.model.*;
import org.openqa.selenium.By;
import org.pages.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class TestExecutor {
    private static final Logger logger = LoggerFactory.getLogger(TestExecutor.class);
    private final BasePage page = new BasePage();

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
    }

    private void executeStep(TestStep step) {
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
                By locator = buildLocator(step.getElement());
                page.type(locator, ConfigManager.getResolvedKey(step.getValue()));
                break;
            case CLICK:
                page.click(buildLocator(step.getElement()));
                break;
            case ACCEPT_ALERT:
                page.acceptAlert();
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
}
