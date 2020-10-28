// Page URL: https://formy-project.herokuapp.com/form
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebForm extends PageObject{

    private final String LOGIN = "Fuego";
    private final String PASSWORD = "haslo123";
    private final String FIRST_NAME = "Maras";
    private final String LAST_NAME = "Kowal";
    private final String STREET = "Grunwaldzka";
    private final String HOME_NO = "123";
    private final String FLAT_NO = "3";
    private final String CITY = "Gdańsk";
    private final String ZIP_CODE = "80210";
    private final String REGISTRATION_BUTTON = "/html/body/div[1]/ul/li[3]";
    private final String LOADING_KITTY = "/html/body/div[3]/div/div[1]";
    private final String SUBMIT_BUTTON = "/html/body/div[3]/form/button";

    @FindBy(xpath = LOADING_KITTY)
    private WebElement loading_kitty;

    @FindBy(xpath = REGISTRATION_BUTTON)
    private WebElement registrationButton;

    @FindBy(id = "username")
    private WebElement user_login;

    @FindBy(id = "password")
    private WebElement user_pass;

    @FindBy(id = "repeatedPassword")
    private WebElement repeat_user_pass;

    @FindBy(id = "firstName")
    private WebElement first_name;

    @FindBy(id = "lastName")
    private WebElement last_name;

    @FindBy(id = "street")
    private WebElement street;

    @FindBy(id = "homeNo")
    private WebElement home_no;

    @FindBy(id = "flatNo")
    private WebElement flat_no;

    @FindBy(id = "city")
    private WebElement city;

    @FindBy(id = "zipCode")
    private WebElement zip_code;

    @FindBy(xpath = SUBMIT_BUTTON)
    private WebElement submit_button;

    public WebForm(WebDriver driver) {
        super(driver);
    }

    WebDriverWait wait = new WebDriverWait(driver, 10);

    private void enterLogin(){
        this.user_login.sendKeys(LOGIN);
    }
    private void enterPassword(){
        this.user_pass.sendKeys(PASSWORD);
    }
    private void re_enterPassword(){
        this.repeat_user_pass.sendKeys(PASSWORD);
    }
    private void enter_first_name(){
        this.first_name.sendKeys(FIRST_NAME);
    }
    private void enter_last_name(){
        this.last_name.sendKeys(LAST_NAME);
    }
    private void enter_street(){
        this.street.sendKeys(STREET);
    }
    private void enter_home_no(){
        this.home_no.sendKeys(HOME_NO);
    }
    private void enter_flat_no(){
        this.flat_no.sendKeys(FLAT_NO);
    }
    private void enter_city(){
        this.city.sendKeys(CITY);
    }
    private void enter_zip_code(){
        this.zip_code.sendKeys(ZIP_CODE);
    }
    private void pressSubmitButton(){
        this.submit_button.click();
    }

    public void register(){

        this.submit_button = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath(SUBMIT_BUTTON)));

        enterLogin();
        enterPassword();
        re_enterPassword();
        enter_first_name();
        enter_last_name();
        enter_street();
        enter_home_no();
        enter_flat_no();
        enter_city();
        enter_zip_code();
        pressSubmitButton();
    }

    public String getLogin() {
        return this.LOGIN;
    }
}