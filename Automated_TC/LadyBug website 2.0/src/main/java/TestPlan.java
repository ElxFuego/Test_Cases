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

    @Test(testName = "Check categories functionality")
    public static void checkCategory() throws InterruptedException {

        driver.get(Utils.BASE_URL);
        Login login_as_admin = new Login(driver);
        CategoriesTab elem = new CategoriesTab(driver);

        login_as_admin.login("admin", "1234");

        elem.chooseCategory();
        elem.countElementsOnPage();
        Thread.sleep(1000);
    }

    @Test(testName = "Check pagination functionality")
    public static void checkPagination() throws InterruptedException {

        driver.get(Utils.BASE_URL);
        Login login_as_admin = new Login(driver);
        CategoriesTab elem = new CategoriesTab(driver);

        login_as_admin.login("admin", "1234");

        elem.chooseCategory();
        elem.checkPagination();
        Thread.sleep(1000);
    }

    @Test(testName = "Deleting newly created user")
    public static void deleteNewUser() throws InterruptedException {

        driver.get(Utils.DELETE_USER_URL);
        RegistrationTab registration = new RegistrationTab(driver);
        Login login_as_admin = new Login(driver);
        ClientsTab deleteUser = new ClientsTab(driver);

        registration.register();

        login_as_admin.login("admin", "1234");

        deleteUser.goToClientsPage();
        deleteUser.deleteUser(registration.getLogin());
        Thread.sleep(1000);
    }

    @AfterSuite
    public static void cleanUp(){
        driver.manage().deleteAllCookies();
        driver.close();
    }
}
