package org.furkan.tests.vendorportal;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.furkan.pages.vendorportal.DashboardPage;
import org.furkan.pages.vendorportal.LoginPage;
import org.furkan.pages.vendorportal.model.VendorPortalTestData;
import org.furkan.tests.AbstractTest;
import org.furkan.util.Config;
import org.furkan.util.Constants;
import org.furkan.util.JsonUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class VendorPortalTest extends AbstractTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private VendorPortalTestData testData;

    @BeforeTest
    @Parameters("testDataPath")
    public void setPageObjects(String testDataPath)
    {
        this.loginPage = new LoginPage(driver);
        this.dashboardPage= new DashboardPage(driver);
        this.testData = JsonUtil.getTestData(testDataPath, VendorPortalTestData.class);
    }

    @Test
    public void loginTest()
    {
        loginPage.goTo(Config.get(Constants.VENDOR_PORTAL_URL));
        Assert.assertTrue(loginPage.isAt());
        loginPage.login(testData.username(), testData.password());
    }

    @Test(dependsOnMethods = "loginTest")
    public void dashboardTest()
    {
        Assert.assertTrue(dashboardPage.isAt());

        //finance metrics
        Assert.assertEquals(dashboardPage.getMonthlyEarning(), testData.monthlyEarning());
        Assert.assertEquals(dashboardPage.getAnnualEarning(), testData.annualEarning());
        Assert.assertEquals(dashboardPage.getProfitMargin(), testData.profitMargin());
        Assert.assertEquals(dashboardPage.getAvailableInventory(), testData.availableInventory());
        //...

        //order history search
        dashboardPage.searchOrderHistoryBy(testData.searchKeyword());
        Assert.assertEquals(dashboardPage.getSearchResultsCount(), testData.searchResultsCount());
    }

    @Test(dependsOnMethods = "dashboardTest")
    public void logoutTest()
    {
        dashboardPage.logout();
        Assert.assertTrue(loginPage.isAt());
    }
}
