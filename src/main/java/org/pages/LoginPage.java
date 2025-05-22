package org.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By submitButton = By.cssSelector("button[type='submit']");

    public void login(String user, String pass) {
        type(usernameField, user);
        type(passwordField, pass);
        click(submitButton);
    }
}