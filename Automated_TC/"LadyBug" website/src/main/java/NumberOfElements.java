// Page URL: https://formy-project.herokuapp.com/form
import org.openqa.selenium.By;
import static org.assertj.core.api.Assertions.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/** Zrób 3 przypadki (może być w jednym teście)
 * sprawdzające czy jak zaznaczysz na filtrze 5 to pojawia się 5 kategorii na liście
 *  i dodatkowo czy masz 3 karty paginacji (te po lewej pod kategoriami)
 * i analogicznie jak klikniesz 10 to powinienes mieć 10 elementów i 2 karty
 *jak 15 to 15 elementów i bez paginacji
 **/


public class NumberOfElements extends PageObject{

    private final String CATEGORY_ELEMENTS = "tr[ng-repeat=\"row in $data track by row.id\"]";
    private final String CATEGORY_BUTTON = "/html/body/div[1]/ul/li[5]/a";
    private final String FIRST_ELEMENTS_ON_PAGE_BUTTON = "/html/body/div[3]/div/div[3]/div/div/div/button[1]/span";
    private final String LOADING_KITTY = "/html/body/div[3]/div/div[1]";
    private final String ELEMENTS_ON_PAGE_BUTTONS = "button[type=\"button\"]";
    private final String ELEMENTS_ON_PAGE_BUTTON_SCHEMA = "/html/body/div[3]/div/div[3]/div/div/div/button[x]/span";
    //czy mozna uzyc skroconego xpath, tak by zostawić końcówke "/button[x]/span"  ?

    @FindBy(css = CATEGORY_ELEMENTS)
    private WebElement category_element;

    @FindBy(xpath = CATEGORY_BUTTON)
    private WebElement category_button;

    @FindBy(xpath = LOADING_KITTY)
    private WebElement loading_kitty;

    @FindBy(xpath = FIRST_ELEMENTS_ON_PAGE_BUTTON)
    private WebElement first_elements_on_page_button;

    public NumberOfElements(WebDriver driver) {
        super(driver);
    }

    public void chooseCategory() throws InterruptedException {

        this.category_button.click();

        WebDriverWait wait_error = new WebDriverWait(driver, 5);
        wait_error.until(
                ExpectedConditions.elementToBeClickable(By.xpath(FIRST_ELEMENTS_ON_PAGE_BUTTON)));
    }
/**
 *  This method checks if after every change of "elements to display"
 *  actual "number of elements" is equal to it. If not, it checks if the
 *  "number of elements" remains the same after changing "elements to display" number.
 *
 *  NOTE - I did not figure out a way to find a number of all elements- in this case
 *  number of all categories (Test1, Test2,...Test15). So I couldn't compare it to anything
 **/
    public void countCategory() throws InterruptedException {

        List<WebElement> elementsNo;
        String temp_elements_no_button = ELEMENTS_ON_PAGE_BUTTON_SCHEMA;
        String temp, numberOfElementsToDisplay;
        int previousNoElements = 0;
        boolean token = false;

        for(int i = 1; i<=numberOfButtons(); i++) {
            temp = temp_elements_no_button.replace("x", Integer.toString(i));
            driver.findElement(By.xpath(temp)).click();

            waitForKitty();

            elementsNo=driver.findElements(By.cssSelector(CATEGORY_ELEMENTS));

            numberOfElementsToDisplay = driver.findElement(
                    By.xpath(temp)).getAttribute("innerHTML");

            if(elementsNo.size() == Integer.parseInt(numberOfElementsToDisplay)){
                System.out.println("Number of element (" + elementsNo.size() +
                        ") is equal to no. of elements to display ("
                        + Integer.parseInt(numberOfElementsToDisplay) + ")");
            }
            else if(elementsNo.size()<Integer.parseInt(numberOfElementsToDisplay)) {
                if(token) {
                    assertThat(previousNoElements).isEqualTo(elementsNo.size());
                    System.out.println("Number of elements is the same");
                    continue;
                }
                System.out.println("\nNumber of elements is smaller than no. of elements\n" +
                        "to display. Checking if the number of elements, stay the same\n");
                previousNoElements = elementsNo.size();
                token = true;
            }

        }

    }

    private void waitForKitty() {

        WebDriverWait wait_error = new WebDriverWait(driver, 5);
        wait_error.until(
                ExpectedConditions.invisibilityOf(loading_kitty));
    }

    private int numberOfButtons() {

        List<WebElement> buttonsNo;
        buttonsNo = driver.findElements(By.cssSelector(ELEMENTS_ON_PAGE_BUTTONS));
        return buttonsNo.size();
    }




}