package org.pages;

import io.qameta.allure.Step;
import org.helper.BrowserManager;
import org.helper.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.utils.SortChecker;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс страницы со списком пользователей.
 */
public class TablePage extends Page {

    public TablePage() {
        this.driver = BrowserManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Селектор столбца таблицы.
     */
    private final String tableColumnSelector = "//table[contains(@class, 'table')]/tbody/tr/td[%s]";

    /**
     * Селектор кнопки сортировки столбца.
     */
    private final String tableSortButtonSelector = "//table[contains(@class, 'table')]/thead/tr/td[%s]/a";

    /**
     * Селектор выбирающий строку со всеми данными выбранного столбца с определённым значением элемента.
     */
    private final String tableDataSelector = "//table[contains(@class, 'table')]/tbody/tr[" +
            "td[%s][text()='%s']]";

    /**
     * Селектор выбирающий из таблицы кнопку определённого столбца (кнопку удаления) с определённым значением другого столбца (имя).
     */
    private final String tableButtonSelector = "//table[contains(@class, 'table')]/tbody/tr[td[%s][text()='%s']]/td[%s]/button";

    /**
     * Возвращает список со строкой со всеми данными выбранной строки по-определённому столбцу и значению элемента.
     * @return List<String>
     */
    @Step("Get list of data of selected column")
    public final List<String> getSelectedRowString(Integer column, String element) {
        Wait.waitUntilVisible(driver, By.xpath(String.format(tableColumnSelector, column)));
        return driver
                .findElements(By.xpath(String.format(tableDataSelector, column, element)))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список элементов столбца.
     * @return List<String>
     */
    @Step("Get column elements list")
    public final List<String> getColumnElementsList(Integer column) {
        Wait.waitUntilVisible(driver, By.xpath(String.format(tableColumnSelector, column)));
        return driver
                .findElements(By.xpath(String.format(tableColumnSelector, column)))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список с определённым названием элемента выбранного столбца. (stream).
     * @return List<String>
     */
    @Step("Get element of selected column (stream)")
    public final List<String> getSelectedElementOfColumn(String element, Integer column) {
        Wait.waitUntilVisible(driver, By.xpath(String.format(tableColumnSelector, column)));
        return driver
                .findElements(By.xpath(String.format(tableColumnSelector, column)))
                .stream()
                .map(WebElement::getText)
                .filter(s -> s.equals(element))
                .collect(Collectors.toList());
    }

    /**
     * Проверяет отсортирована ли таблица по определённому столбцу.
     * @return boolean
     */
    @Step("Check if the table is sorted")
    public boolean isSortedByColumn(Integer column) {
        List<String> names = getColumnElementsList(column);
        return SortChecker.isSorted(names);
    }

    /**
     * Проверяет отсортирована ли таблица по именам в обратном порядке.
     * @return boolean
     */
    @Step("Check if the table is sorted in reverse order")
    public boolean isSortedByColumnInReverse(Integer column) {
        List<String> names = getColumnElementsList(column);
        return SortChecker.isSortedInReverse(names);
    }

    /**
     * Сортирует таблицу по определенному столбцу.
     */
    @Step("Sort table by column")
    public void sortByColumn(Integer column) {
        Wait.waitThenCLick(driver, By.xpath(String.format(tableSortButtonSelector, column)));
    }

    /**
     * Удаляет из таблицы пользователя с именем.
     */
    @Step("Delete table element")
    public void deleteElement(Integer buttonColumn, Integer column, String element) {
        Wait.waitThenCLick(driver, By.xpath(String.format(tableButtonSelector, column, element, buttonColumn)));
    }
}
