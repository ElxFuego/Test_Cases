import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.io.File;
import static org.assertj.core.api.Assertions.*;



public class TestPage extends PageObject {

    private final String MSVALUES = "//select[@name=\"multipleselect[]\"]/child::node()";
    private final String DPLIST = "//select[@name=\"dropdown\"]/option";
    private final String UPPLOADFILE = "//p/input[@name=\"filename\"]";
    private final String DOWNLOADFILE ="//p[text()=\"Download FIle:\"]/a";
    private final String RADIOBUTTONSSCHEMA = "//table/tbody/tr[x]/td/input";
    private final String ROWSINRADIOBUTTONSTABLE = " //table[@class=\"spTable\"]/tbody/tr";

    @FindBy(id = "cookie_action_close_header")
    private WebElement accept_cookies;

    @FindBy(id = "onesignal-slidedown-cancel-button")
    private WebElement notifications;

    @FindBy(name = "username")
    private WebElement text_field;

    @FindBy(name = "password")
    private WebElement password_field;

    @FindBy(name = "comments")
    private WebElement text_area;

    @FindBy(xpath = "//input[@value=\"cbselenium\"]")
    private WebElement checkbox_Selenium;

    @FindBy(xpath = "//input[@value=\"cbqtp\"]")
    private WebElement checkbox_QTP;

    @FindBy(xpath = "//input[@value=\"cbloadrunner\"]")
    private WebElement checkbox_Load_Runner;

    @FindBy(xpath = "//input[@value=\"radioselenium\"]")
    private WebElement checkbox_Radio_Selenium;

    @FindBy(xpath = "//input[@value=\"radioqtp\"]")
    private WebElement checkbox_Radio_QTP;

    @FindBy(xpath = "//input[@value=\"radioloadrunner\"]")
    private WebElement checkbox_Radio_Load_Runner;

    @FindBy(xpath = ROWSINRADIOBUTTONSTABLE)
    private WebElement radioButtonsRows;

    @FindBy(xpath = MSVALUES)
    private WebElement multiple_select_values;

    @FindBy(xpath = DPLIST)
    private WebElement dropdown_list;

    @FindBy(name = "dropdown")
    private WebElement dropdown_list_select_button;

    @FindBy(xpath = " //p/input[@name=\"bday\"]")
    private WebElement date_picker;

    @FindBy(xpath = UPPLOADFILE)
    private WebElement uppload_file;

    @FindBy(xpath = DOWNLOADFILE)
    private WebElement files_to_download;
///////////////////////////////////////////////////////////////////////
    public TestPage(WebDriver driver) {
        super(driver);
    }

    WebDriverWait wait = new WebDriverWait(driver, 5);

    public void acceptCookies() throws InterruptedException {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(accept_cookies));
            accept_cookies.click();
        } catch (Exception e) {
            System.out.println("No cookies message");
        }
    }
    public void declineNotifications() throws InterruptedException{
        try {
            wait.until(ExpectedConditions.elementToBeClickable(notifications));
            notifications.click();
        } catch (Exception e) {
            System.out.println("No notification message");
        }
    }

    public void enterTextField(String text) {
        this.text_field.sendKeys(text);
    }

    public void enterPasswordField(String text) {
        this.password_field.sendKeys(text);
    }

    public void enterTextArea(String text) {
        this.text_area.clear();
        this.text_area.sendKeys(text);
    }

    public void clickCheckboxSelenium() {
        this.checkbox_Selenium.click();
    }

    public void clickCheckboxQTP() {
        this.checkbox_QTP.click();
    }

    public void clickCheckboxLoadRunner() {
        this.checkbox_Load_Runner.click();
    }

    public void clickRadioSelenium() {
        this.checkbox_Radio_Selenium.click();
    }

    public void clickRadioQTP() {
        this.checkbox_Radio_QTP.click();
    }

    public void clickRadioLoadRunner() {
        this.checkbox_Radio_Load_Runner.click();
    }

    public void selectFromMultipleValues(String value) throws InterruptedException {
        List<WebElement> valuesToClick = this.driver.findElements(By.xpath(MSVALUES));
        for (WebElement element : valuesToClick) {
            if (element.getAttribute("innerText").equals(value)) {
                element.click();
                break;
            }
        }

    }
    public void selectFromDropDownList(String value) throws InterruptedException {
        List<WebElement> valuesToClick = this.driver.findElements(By.xpath(DPLIST));
        for (WebElement element : valuesToClick) {
            if (element.getAttribute("innerText").equals(value)) {
                dropdown_list_select_button.click();
                element.click();
                break;
            }
        }
    }
    public void selectDate(String date){
        date_picker.sendKeys(date);
    }
    public void uploadFile(String path){
        WebElement uploadElement = driver.findElement(By.xpath(UPPLOADFILE));
        uploadElement.sendKeys(path);
    }
    public void downloadFile(String fileType) throws InterruptedException {
        List<WebElement> valuesToClick = this.driver.findElements(By.xpath(DOWNLOADFILE));
        String innerText;
        for (WebElement element : valuesToClick) {
            innerText = element.getAttribute("innerText").toLowerCase();
            if (innerText.contains(fileType)) {
                dropdown_list_select_button.click();
                element.click();
                break;
            }
        }
    }
    public void radioButton(String name) {
        List<WebElement> radio = this.driver.findElements(By.xpath(ROWSINRADIOBUTTONSTABLE));
        int index = 0;
        String radiobutton;
        for (WebElement element : radio) {
            index++;
            if (element.getAttribute("innerText").contains(name)) {
                radiobutton = RADIOBUTTONSSCHEMA.replace("x",Integer.toString(index));
                this.driver.findElement(By.xpath(radiobutton)).click();
                break;
            }
        }
    }
}
