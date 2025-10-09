**Установка**:

1. Требования: Java 17+, Maven
1. Установить фреймворк
1. Зависимости в pom.xml
1. Импортировать аннотации TestNG
1. Импортировать метод выполнения yaml-теста в исполняемый файл в директории test:

   import org.helpers.TestCaseLoader.*executeYamlTest*;


**Настройка конфигурации (config.yaml)**

Конфигурационный файл позволяет гибко настраивать параметры выполнения тестов:

1. **Секция browser**:
   1. name: выбор браузера (chromeилиfirefox)
   1. headless: режим без графического интерфейса (true/false)
   1. implicit\_wait: время неявного ожидания элементов (в секундах)
   1. window\_size: размер окна браузера (форматширина x высота)

Пример:

      browser:
         name: firefox
         headless: true
         implicit_wait: 15
         window_size: 1920x1080

2. **Секция environments**:
   1. Определение различных окружений (dev, prod и др.)
   1. Указание базовых URL для каждого окружения
   1. Возможность добавления произвольных параметров окружения

Пример:

      environments:
         dev:
            base_url: "https://dev.example.com"
         prod:
            base_url: "https://example.com"

3. **Секция credentials**:
   1. Хранение учетных данных различных пользователей
   1. Поддержка произвольных наборов атрибутов

Пример:

      credentials:
         admin:
            username: "admin_user"
            password: "securePass123"
         customer:
            email: "customer@example.com"
            phone: "+1234567890"

**Полная спецификация config.yaml**

|**Секция**|**Ключ**|**Тип**|**Значение по умолчанию**|**Описание**|
| :- | :- | :- | :- | :- |
|**browser**|name|String|chrome|Браузер для тестирования (chrome,firefox)|
||headless|Boolean|false|Режим без графического интерфейса|
||implicit\_wait|Integer|10|Время неявного ожидания элементов (секунды)|
||window\_size|String|1920x1080|Разрешение окна браузера|
|**environments**|dev.url|String|-|URL для среды разработки|
||dev.base\_url|||URL используемый по умолчанию|
||prod.url|String|-|URL для production-среды|
|**credentials**|user.attribute|String|-|Атрибуты данных для различных пользователей|



**Написание YAML-тестов**

**1. Базовые принципы:**

- Каждый тест-кейс представляет собой список шагов (steps)
- Шаги выполняются последовательно сверху вниз
- Каждый шаг содержит обязательное полеaction(тип действия)
- Для действий с элементами требуется блокelement
- Для проверок используется блокassert (только после действий, которые что-либо возвращают)

**2. Спецификация локаторов:**\
Фреймворк поддерживает все основные типы локаторов Selenium. Для каждого действия, связанного с элементом страницы, необходимо указать тип локатора и его значение:

element:

by: "ТИП\_ЛОКАТОРА"  *# Один из: ID, XPATH, CSS, NAME, CLASS\_NAME, TAG\_NAME*

value: "ЗНАЧЕНИЕ"    *# Строка с локатором*

**3. Параметризация локаторов:**\
Локаторы могут содержать динамические значения из конфига:

      - name: "Клик по динамическому элементу"
      action: CLICK
      element:
      by: XPATH
      value: "//div[@id='${dynamic.section.id}']/button"

**4. Структура тест-шага:**\
Полная схема шага с комментариями:

      - name: "Описание шага"          *# Обязательное поле*
      
      action: "ACTION\_TYPE"           *# Обязательное поле*
      
      description: "Детали шага"      *# Опционально*
      
      url: "URL"                      *# Только для OPEN*
      
      element:                        *# Для действий с элементами*
      
      by: "LOCATOR\_TYPE"            *# Тип локатора*
      
      value: "LOCATOR\_VALUE"        *# Значение локатора*
      
      value: "TEXT\_OR\_DATA"           *# Для TYPE, значение для ввода*
      
      args:                           *# Для табличных и кастомных действий*
      
      - "аргумент1"
      
        - 42
      
        - true
      
      method: "CUSTOM\_METHOD\_NAME"    *# Для CALL\_METHOD*
      
      assert:                         *# Блок проверки*
      
      method: "ASSERT\_TYPE"         *# EQUALS, NOT\_EQUALS и т.д.*
      
      expected: "EXPECTED\_VALUE"    *# Ожидаемое значение*
      
      soft: true/false              *# Тип ассерта (обязательно)*





<a name="_hlk199834226"></a>**Спецификация локаторов** 

|**Тип локатора**|**Пример использования в YAML**|**Описание**|
| :- | :- | :- |
|**ID**|{by: ID, value: "username"}|Поиск по атрибуту id|
|**XPATH**|{by: XPATH, value: "//div/button"}|XPath-выражения|
|**CSS**|{by: CSS, value: ".submit-btn"}|CSS-селекторы|
|**NAME**|{by: NAME, value: "email"}|Поиск по атрибуту name|
|**CLASS\_NAME**|{by: CLASS\_NAME, value: "active"}|Поиск по классу элемента|
|**TAG\_NAME**|{by: TAG\_NAME, value: "input"}|Поиск по тегу элемента|



**Полный список поддерживаемых операций**

|**Операция**|**Описание**|**Пример YAML**|
| :- | :- | :- |
|**OPEN**|Открытие URL|{action: OPEN, url: "https://example.com"}|
|**TYPE**|Ввод текста в поле|{action: TYPE, element: {by: ID, value: "username"}, value: "admin"}|
|**CLICK**|Клик по элементу|{action: CLICK, element: {by: XPATH, value: "//button"}}|
|**ACCEPT\_ALERT**|Принятие алерта браузера|{action: ACCEPT\_ALERT}|
|**CLEAR\_FIELD**|Очистка поля ввода|{action: CLEAR\_FIELD, element: {by: NAME, value: "email"}}|
|**GET\_TEXT**|Получение текста элемента|{action: GET\_TEXT, element: {by: CLASS\_NAME, value: "title"}}|
|**CHECK\_VISIBLE**|Проверка видимости элемента|{action: CHECK\_VISIBLE, element: {by: CSS, value: ".modal"}}|
|**CHECK\_ENABLED**|Проверка активности элемента|{action: CHECK\_ENABLED, element: {by: ID, value: "submit-btn"}}|
|**GET\_URL**|Получение текущего URL|{action: GET\_URL}|
|**NAV\_FORWARD**|Переход вперед в истории браузера|{action: NAV\_FORWARD}|
|**NAV\_BACK**|Переход назад в истории браузера|{action: NAV\_BACK}|
|**REFRESH\_PAGE**|Обновление страницы|{action: REFRESH\_PAGE}|
|**REFRESH**|Полный сброс браузера (cookies + refresh)|{action: REFRESH}|
|**QUIT**|Закрытие браузера|{action: QUIT}|
|**GET\_TABLE\_ROW**|Получение строки таблицы|{action: GET\_TABLE\_ROW, args: [2, "John Doe"]}|
|**GET\_TABLE\_COLUMN**|Получение столбца таблицы|{action: GET\_TABLE\_COLUMN, args: [3]}|
|**GET\_TABLE\_ELEMENT**|Получение элемента таблицы|{action: GET\_TABLE\_ELEMENT, args: ["Approved", 4]}|
|**SORT\_TABLE\_COLUMN**|Сортировка таблицы по столбцу|{action: SORT\_TABLE\_COLUMN, args: [1]}|
|**CHECK\_COLUMN\_SORTED**|Проверка сортировки столбца|{action: CHECK\_COLUMN\_SORTED, args: [2]}|
|**CHECK\_COLUMN\_SORTED\_REV**|Проверка обратной сортировки столбца|{action: CHECK\_COLUMN\_SORTED\_REV, args: [3]}|
|**CLICK\_TABLE\_ELEMENT**|Клик по элементу в таблице|{action: CLICK\_TABLE\_ELEMENT, args: [5, 2, "Delete"]}|
|**CALL\_METHOD**|Вызов кастомного метода Page Object|{action: CALL\_METHOD, method: "customAction", args: ["param1", 42]}|

*Таблица 4. Спецификация операций*

**Спецификация ассертов**

|**Тип локатора**|**Пример использования в YAML**|**Описание**|
| :- | :- | :- |
|**EQUALS**|{ assert:  method: " EQUALS” expected:"EXPECTED\_VALUE" }|Проверка на равенство|
|**NOT\_EQUALS**|{ assert:  method: "NOT\_EQUALS” expected:"NOT\_EXPECTED\_VALUE" }|Проверка на неравенство|
|**ASSET\_TRUE**|{ assert:  method: "ASSERT\_TRUE }|Проверка на истинность|
|**ASSERT\_FALSE**|{ assert:  method: "ASSERT\_FALSE }|Проверка на ложность|



**Расширенные возможности**

1. **Динамические значения**:
   1. Использование переменных из конфига: ${credentials.admin.username}
   1. Подстановка значений окружения:${environments.dev.base\_url}
1. **Вызов кастомных методов**:

         action: CALL_METHOD
         method: "customMethodName"
         args: ["param1", 42, true]

1. **Мягкие ассерты**:

         assert:
         method: EQUALS
         expected: "Expected Value"
         soft: true *# Ошибка не прервет выполнение теста*


**Интеграция в тестовые сьюты**

Пример тестового класса:

      @Listeners({AllureTestNg.class})
      
      public class YamlTestSuite {
      
      
      
         @Test(description = "Тесты авторизации")
         
         public void runAuthorizationTests() {
         
            TestCaseLoader.executeYamlTest("tests/authorization.yaml");
         
         }
         
         
         
         @Test(description = "Тесты профиля пользователя")
   
         public void runProfileTests() {
   
            TestCaseLoader.executeYamlTest("tests/user_profile.yaml");
   
         }



         @AfterMethod
         
         public void cleanup() {
         
            TestCaseLoader.executeYamlTest("tests/cleanup.yaml");
         
         }
      
      }

**Запуск тестов**:

1. Через TestNG-сьюты:

         @Test(description = "Example.com tests")
         
         public void executeNavigationTest() {
         
            executeYamlTest("test/nav_test.yaml");
         
         }

2. Командная строка:mvn test -Dtest=YamlTest

**Генерация отчетов**:

allure serve allure-results

