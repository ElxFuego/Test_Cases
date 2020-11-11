import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Zrób 3 przypadki (może być w jednym teście)
    * sprawdzające czy jak zaznaczysz na filtrze 5 to pojawia się 5 kategorii na liście
    *  i dodatkowo czy masz 3 karty paginacji (te po lewej pod kategoriami)
    * i analogicznie jak klikniesz 10 to powinienes mieć 10 elementów i 2 karty
    *jak 15 to 15 elementów i bez paginacji
 **/

public class Login extends PageObject{

    private final String ERROR_MESSAGE = "Bad credentials";
    private final String LOGIN_BUTTON = "//button[@class=\"btn btn-primary\"]";
    private final String LOADING_KITTY = "//div[@ng-show=\"status == 'LOADING'\"]";
    private final String ERROR_ELEMENT = "//div[@ng-show=\"!!error\"]";

    @FindBy (id = "username")
    private WebElement user_name;

    @FindBy (id = "password")
    private WebElement password;

    @FindBy (xpath = LOGIN_BUTTON)
    private WebElement login_button;

    @FindBy (xpath = LOADING_KITTY)
    private WebElement loading_kitty;

    public Login(WebDriver driver) {
        super(driver);
    }

    public void login(String login, String password) {

        waitForKitty();

        do{
            this.user_name.sendKeys(login);
            this.password.sendKeys(password);
            this.login_button.click();
        }
        while(error_message());
    }

/**
 * Sometimes website fails to login for the first time even with correct credentials. That's why "error message()" method is created.
 * It returns "true" if login is succesfull, "false" if not.
 */

    public boolean error_message() {

        try {
            waitForKitty();
            String error_message = driver.findElement(
                    By.xpath(ERROR_ELEMENT)).getAttribute("innerHTML");

            if(error_message.contains(ERROR_MESSAGE)) {
                this.user_name.clear();
                this.password.clear();
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e) {
            return false;
        }

    }
    private void waitForKitty() {

        WebDriverWait wait_error = new WebDriverWait(driver, 5);
        wait_error.until(
                ExpectedConditions.invisibilityOf(loading_kitty));
    }

}