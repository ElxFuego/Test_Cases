import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientsTab extends PageObject{

    private final String USERS_PAGE = "//a[@href=\"#/clients\"]";
    private final String USER = "//tbody/tr[x]";
    private final String USERS_LOGIN ="//tbody/tr/td[@data-title=\"'Login'\"]";
    private final String DELETE_BUTTON_SUFIX = "/td/a[@ng-click=\"delete(row)\"]";
    private final String LOADING_KITTY = "//div[@ng-show=\"status == 'LOADING'\"]";

    @FindBy(xpath = LOADING_KITTY)
    private WebElement loading_kitty;

    @FindBy(xpath = USERS_PAGE)
    private WebElement users_page;

    public ClientsTab(WebDriver driver) {
        super(driver);
    }
    WebDriverWait wait = new WebDriverWait(driver, 10);

    public void goToClientsPage() {
        this.users_page = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(USERS_PAGE)));
        this.users_page.click();
    }

    public void deleteUser(String login) {
        String user;
        String userLogin = "";
        int i = 1;

        waitForKitty();
        List<WebElement> usersRows = this.driver.findElements(By.xpath(USERS_LOGIN));

        for(WebElement e : usersRows) {
            if(e.getAttribute("innerHTML").equals(login)) {
                userLogin = e.getAttribute("innerHTML");
                break;
            }
            i++;
        }
        user = USER.replace("x", Integer.toString(i));
        System.out.println("Deleted user with 'Login': " + userLogin );
        assertThat(userLogin).isEqualTo(login);
        driver.findElement(By.xpath(user.concat(DELETE_BUTTON_SUFIX))).click();
    }
    private void waitForKitty() {
        WebDriverWait wait_error = new WebDriverWait(driver, 5);
        wait_error.until(
                     ExpectedConditions.invisibilityOf(loading_kitty));
    }
}