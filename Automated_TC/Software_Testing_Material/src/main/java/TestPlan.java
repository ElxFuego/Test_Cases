import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


public class TestPlan {
    private static final WebDriver driver = new ChromeDriver();
    @BeforeSuite
    public static void main(String[] args) {
        // ChromeDriver location set up in Utils class
        System.setProperty("webdriver.chrome.driver", Utils.CHROME_DRIVER_LOCATION);
    }
    @Test(testName = "Check every functionality on the Test Website")
    public static void funcionalitiesOnTestPage() throws InterruptedException {
        driver.get(Utils.BASE_URL);

        TestPage test = new TestPage(driver);
        test.acceptCookies();
        test.declineNotifications();
        test.enterPasswordField("Checking if everything");
        test.enterTextArea("is alright");
        test.enterTextField("lalala");
        test.clickCheckboxLoadRunner();
        test.clickCheckboxQTP();
        test.clickCheckboxSelenium();
        test.clickRadioLoadRunner();
        test.clickRadioQTP();
        test.clickRadioSelenium();
        test.selectFromMultipleValues("Manual Testing");
        test.selectFromDropDownList("QTP");
        test.selectDate("04112020");
        test.uploadFile("D:\\Nauka Java\\Software_Testing_Material\\test.txt");
        test.downloadFile("csv");
        test.radioButton("QA Manager");
        Thread.sleep(2000);

    }
    @AfterSuite
    public static void cleanUp(){
        driver.manage().deleteAllCookies();
       // driver.close();
    }
}
