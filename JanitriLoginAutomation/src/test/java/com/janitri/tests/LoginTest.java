package com.janitri.tests;

import com.janitri.base.BaseTest;
import com.janitri.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    // Replace with valid creds if available
    private final String VALID_USERNAME = "";
    private final String VALID_PASSWORD = "";

    @Test(priority = 1)
    public void testLoginButtonDisabledWhenFieldsAreEmpty() {
        LoginPage lp = new LoginPage(driver);
        // By default fields are empty after navigation
        boolean enabled = lp.isLoginButtonEnabled();
        // If app has disabled button behavior, this should be false.
        // We'll assert false but tests may be adjusted based on actual app behavior.
        Assert.assertFalse(enabled, "Login button should be disabled when fields are empty");
    }

    @Test(priority = 2)
    public void testPasswordMaskedbutton() {
        LoginPage lp = new LoginPage(driver);
        lp.enterPassword("MySecret123");
        Assert.assertTrue(lp.isPasswordMasked(), "Password should be masked by default");
        // Toggle visibility and verify
        lp.togglePasswordVisibility();
        Assert.assertFalse(lp.isPasswordMasked(), "Password should be unmasked after toggling");
        // Toggle again
        lp.togglePasswordVisibility();
        Assert.assertTrue(lp.isPasswordMasked(), "Password should be masked after toggling again");
    }

    @Test(priority = 3)
    public void testInvalidLoginShowErrorMsg() throws InterruptedException {
        LoginPage lp = new LoginPage(driver);
        lp.enterUserId("invalid_user@example.com");
        lp.enterPassword("wrongPass123");
        lp.clickLogin();
        // Wait briefly for possible notification
        Thread.sleep(2000);
        String err = lp.getErrorMessageText();
        System.out.println("Captured error: " + err);
        Assert.assertTrue(err.length() > 0, "Error message should be displayed for invalid credentials");
    }

    @Test(priority = 4)
    public void testAttemptLoginWithBlankFields() {
        LoginPage lp = new LoginPage(driver);
        lp.enterUserId(""); lp.enterPassword(""); lp.clickLogin();
        String err = lp.getErrorMessageText();
        // App behavior may differ: either button disabled or shows validation messages.
        // If error displayed, ensure it is meaningful.
        if (err.length() > 0) {
            Assert.assertTrue(err.toLowerCase().contains("required") || err.toLowerCase().contains("invalid") || err.toLowerCase().contains("blank"),
                    "Expected a validation message when fields are blank");    
        } else {
            // fallback: check button state
            Assert.assertFalse(lp.isLoginButtonEnabled(), "Login button should be disabled when fields are blank (if applicable)");
        }
    }
}