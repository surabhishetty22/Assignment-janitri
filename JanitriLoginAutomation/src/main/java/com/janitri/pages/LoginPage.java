package com.janitri.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    // Locators - adjust if selectors differ on the real page
    private By userIdInput = By.id("email") ; // example id, update as per actual DOM
    private By passwordInput = By.id("password"); // example id
    private By loginButton = By.xpath("//button[contains(., 'Login') or contains(., 'Sign in')]"); // generic
    private By eyeIcon = By.cssSelector(".password-visibility, .eye-icon"); // generic
    private By errorMsg = By.cssSelector(".ant-notification-notice-description, .error-message, .help-block"); // generic
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    public WebElement getUserIdField() {
        return driver.findElement(userIdInput);
    }

    public WebElement getPasswordField() {
        return driver.findElement(passwordInput);
    }

    public WebElement getLoginButton() {
        return driver.findElement(loginButton);
    }

    public boolean isLoginButtonEnabled() {
        try {
            return getLoginButton().isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterUserId(String userId) {
        getUserIdField().clear();
        getUserIdField().sendKeys(userId);
    }

    public void enterPassword(String password) {
        getPasswordField().clear();
        getPasswordField().sendKeys(password);
    }

    public void clickLogin() {
        getLoginButton().click();
    }

    public boolean isPasswordMasked() {
        // Check input type attribute
        String type = getPasswordField().getAttribute("type");
        return type != null && (type.equals("password") || type.equals(""));
    }

    public void togglePasswordVisibility() {
        driver.findElement(eyeIcon).click();
    }

    public String getErrorMessageText() {
        try {
            return driver.findElement(errorMsg).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isElementPresent(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}