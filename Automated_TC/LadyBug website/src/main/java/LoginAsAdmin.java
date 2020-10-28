// Page URL: https://formy-project.herokuapp.com/form
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


public class LoginAsAdmin extends PageObject{

    private final String LOGIN = "admin";
    private final String PASSWORD = "1234";
    private final String ERROR_MESSAGE = "Bad credentials";
    private final String LOGIN_BUTTON = "/html/body/div[3]/form/button";
    private final String LOGIN_PAGE = "/html/body/div[1]/ul/li[2]/a";
    private final String LOADING_KITTY = "/html/body/div[3]/form/div[1]";
    private final String ERROR_ELEMENT = "/html/body/div[2]";

    @FindBy (id = "username")
    private WebElement user_name;

    @FindBy (id = "password")
    private WebElement password;

    @FindBy (xpath = LOGIN_BUTTON)
    private WebElement login_button;

    @FindBy (xpath = LOGIN_PAGE)
    private WebElement login_page;

    @FindBy (xpath = LOADING_KITTY)
    private WebElement loading_kitty;

    public LoginAsAdmin(WebDriver driver) {
        super(driver);
    }

    public void login() {
            waitForKitty();
            this.user_name.sendKeys(LOGIN);
            this.password.sendKeys(PASSWORD);
            this.login_button.click();
    }

    public boolean error_message() {

        WebDriverWait wait_error = new WebDriverWait(driver, 5);
        wait_error.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath(LOADING_KITTY)));

        try {
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