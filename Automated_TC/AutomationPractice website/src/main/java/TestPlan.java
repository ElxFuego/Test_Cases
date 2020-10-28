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
    @Test(testName = "Image is made bigger after clicking on Quick view")
    public static void imageSize() throws InterruptedException {
        driver.get(Utils.BASE_URL1);

        ImageResize imageResize = new ImageResize(driver);
        imageResize.checkImage();
        Thread.sleep(2000);
    }
    @AfterSuite
    public static void cleanUp(){
        driver.manage().deleteAllCookies();
        driver.close();
    }
}