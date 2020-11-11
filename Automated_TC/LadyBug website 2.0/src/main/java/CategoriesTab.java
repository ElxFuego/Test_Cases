// Page URL: https://formy-project.herokuapp.com/form
import org.openqa.selenium.By;
import static org.assertj.core.api.Assertions.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class CategoriesTab extends PageObject{

    private final String CATEGORY_ELEMENTS = "tr[ng-repeat=\"row in $data track by row.id\"]";
    private final String CATEGORY_BUTTON = "//a[@href=\"#/categories\"]";
    private final String LOADING_KITTY = "//div[@ng-show=\"status == 'LOADING'\"]";
    private final String ELEMENTS_ON_PAGE_BUTTONS = "button[type=\"button\"]";
    private final String ELEMENTS_ON_PAGE_BUTTON_SCHEMA = "//div/button[x]/span";
    private final String FIRST_ELEMENTS_ON_PAGE_BUTTON = "//div/button/span";
    private final int MAXIMUM_NUMBER_OF_ELEMENTS = 5;
    private final String NUMBER_OF_PAGES = "a[ng-click=\"params.page(page.number)\"]";

    @FindBy(css = CATEGORY_ELEMENTS)
    private WebElement category_element;

    @FindBy(xpath = CATEGORY_BUTTON)
    private WebElement category_button;

    @FindBy(xpath = LOADING_KITTY)
    private WebElement loading_kitty;

    public CategoriesTab(WebDriver driver) {
        super(driver);
    }

    public void chooseCategory(){
        this.category_button.click();
        waitForKitty();
    }
/**
 *  This method checks if after every change of "elements to display"
 *  actual "number of elements" is equal to it. If not, it checks if the
 *  "number of elements" remains the same after changing "elements to display" number.
 *
 *  NOTE - I did not figure out a way to find a number of all elements- in this case
 *  number of all categories (Test1, Test2,...Test15). So I couldn't compare it to anything
 **/
    public void countElementsOnPage(){

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
    public void checkPagination() {

        int numberOfPages;
        int numberOfElements;
        String temp_elements_no_button = ELEMENTS_ON_PAGE_BUTTON_SCHEMA;
        String temp;

        driver.findElement(By.xpath(FIRST_ELEMENTS_ON_PAGE_BUTTON)).click();

        waitForKitty();

        numberOfPages = checkNumberOfPages();
        numberOfElements = numberOfElements();

        if(numberOfPages == 0) {
            assertThat(numberOfElements).isLessThanOrEqualTo(MAXIMUM_NUMBER_OF_ELEMENTS);
        }
        else{

            for(int i = 1; i<numberOfPages; i++) {
                temp = temp_elements_no_button.replace("x", Integer.toString(i));
                driver.findElement(By.xpath(temp)).click();

                waitForKitty();

                numberOfElements = numberOfElements();
                assertThat(numberOfElements).isEqualTo(5 * i);
                System.out.println("Number of elements on page is " + numberOfElements +"\n" +
                        "Maximum number of elements on page is " + 5 * i + "\n--------------\n");
            }
        }
        temp = temp_elements_no_button.replace("x", Integer.toString(numberOfPages));
        driver.findElement(By.xpath(temp)).click();

        waitForKitty();

        assertThat(numberOfElements).isLessThanOrEqualTo(5*numberOfPages);
        System.out.println("Number of elements on page is " + numberOfElements +"\n" +
                "Maximum number of elements on page is " + 5 * numberOfPages + "\n--------------\n");
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

    private int checkNumberOfPages() {
        List<WebElement>pagesNo = driver.findElements(By.cssSelector(NUMBER_OF_PAGES));
        return pagesNo.size() - 2;
        // -1 one for next button, -1 one for previous button
    }

    private int numberOfElements() {
        List<WebElement>elementsNo = driver.findElements(By.cssSelector(CATEGORY_ELEMENTS));
        return elementsNo.size();
    }

}