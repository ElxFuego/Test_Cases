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
        LoginAsAdmin login_as_admin = new LoginAsAdmin(driver);
        NumberOfElements elem = new NumberOfElements(driver);

        do {
            login_as_admin.login();
        }
        while(login_as_admin.error_message());

        elem.chooseCategory();
        elem.countCategory();
        Thread.sleep(1000);
    }

    @Test(testName = "Check pagination functionality")
    public static void checkPagination() throws InterruptedException {

        driver.get(Utils.BASE_URL);
        Pagination page = new Pagination(driver);
        LoginAsAdmin login_as_admin = new LoginAsAdmin(driver);

        do {
            login_as_admin.login();
        }
        while(login_as_admin.error_message());

        page.chooseCategory();
        page.checkPagination();
        Thread.sleep(1000);
    }

    @Test(testName = "Deleting newly created user")
    public static void deleteNewUser() throws InterruptedException {

        driver.get(Utils.DELETE_USER_URL);
        WebForm registration = new WebForm(driver);
        LoginAsAdmin login_as_admin = new LoginAsAdmin(driver);
        DeleteUser deleteuser = new DeleteUser(driver);

        registration.register();

       do{
            login_as_admin.login();
        }
        while(login_as_admin.error_message());

       deleteuser.goToUsersPage();
       deleteuser.deleteUser(registration.getLogin());
       Thread.sleep(1000);
    }

    @AfterSuite
    public static void cleanUp(){
        driver.manage().deleteAllCookies();
        driver.close();
    }
}
