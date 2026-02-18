package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.LoginPage;
import utils.ExcelReader;
import utils.ScreenshotUtil;

public class LoginTest {

    @DataProvider(name = "loginData")
    public Object[][] getData() {
        ExcelReader reader = new ExcelReader("TestData/LoginData.xlsx");
        return reader.readSheet("Sheet1");
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password) {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://example.com/login");
        driver.manage().window().maximize();

        LoginPage login = new LoginPage(driver);

        login.enterUsername(username);
        login.enterPassword(password);
        login.clickLogin();

        if (!login.isLoginSuccessful()) {
            ScreenshotUtil.capture(driver, username + "_failed");
        }

        Assert.assertTrue(login.isLoginSuccessful(), "Login failed for user: " + username);

        driver.quit();
    }
}
