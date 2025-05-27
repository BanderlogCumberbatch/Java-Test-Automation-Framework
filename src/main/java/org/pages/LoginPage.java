package org.pages;

import org.openqa.selenium.By;

public class LoginPage extends Page {
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By submitButton = By.id("login-button");

    public void login(String user, String pass) {
        type(usernameField, user);
        type(passwordField, pass);
        click(submitButton);
    }
}