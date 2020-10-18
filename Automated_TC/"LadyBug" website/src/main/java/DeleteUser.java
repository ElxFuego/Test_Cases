import com.google.common.collect.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class DeleteUser extends PageObject{

    private final String USERS_PAGE = "/html/body/div[1]/ul/li[7]/a";
    private final String USER = "/html/body/div[3]/div/table/tbody/tr[x]";
    private final String USERS_LOGIN_SUFIX ="/td[2]";
    private final String DELETE_BUTTON_SUFIX = "/td[8]/a[2]";
    private final String ROWS_IN_USERS_TABLE = "tr[ng-repeat=\"row in $data track by row.id\"]";
    private final String LOADING_KITTY = "/html/body/div[3]/div/div[1]";

    @FindBy(xpath = LOADING_KITTY)
    private WebElement loading_kitty;

    @FindBy(xpath = USERS_PAGE)
    private WebElement users_page;

    public DeleteUser(WebDriver driver) {
        super(driver);
    }
    WebDriverWait wait = new WebDriverWait(driver, 10);

    public void goToUsersPage() {
        this.users_page = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(USERS_PAGE)));
        this.users_page.click();
    }

    public void deleteUser(String login) {

        waitForKitty();

        String temp ="";
        String usersLogin;
        int i = 1;

        for(; i<=numberOfUsers(); i++) {
            temp = USER.replace("x", Integer.toString(i));
            usersLogin = driver.findElement(
                    By.xpath(temp.concat(USERS_LOGIN_SUFIX))).getAttribute("innerHTML");
            if(usersLogin.equals(login))
                break;
        }
        driver.findElement(By.xpath(temp.concat(DELETE_BUTTON_SUFIX))).click();
    }

    private int numberOfUsers() {

        List<WebElement> usersNo;
        usersNo = driver.findElements(By.cssSelector(ROWS_IN_USERS_TABLE));
        return usersNo.size();
    }

    private void waitForKitty() {

        WebDriverWait wait_error = new WebDriverWait(driver, 5);
        wait_error.until(
                ExpectedConditions.invisibilityOf(loading_kitty));
    }
}
