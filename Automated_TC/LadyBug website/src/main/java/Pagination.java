import org.openqa.selenium.By;
import static org.assertj.core.api.Assertions.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class Pagination extends PageObject {

    private final String CATEGORY_BUTTON = "/html/body/div[1]/ul/li[5]/a";
    private final String FIRST_ELEMENTS_ON_PAGE_BUTTON = "/html/body/div[3]/div/div[3]/div/div/div/button[1]/span";
    private final String NUMBER_OF_PAGES = "a[ng-click=\"params.page(page.number)\"]";
    private final String CATEGORY_ELEMENTS = "tr[ng-repeat=\"row in $data track by row.id\"]";
    private final int MAXIMUM_NUMBER_OF_ELEMENTS = 5;
    private final String LOADING_KITTY = "/html/body/div[3]/div/div[1]";
    private final String ELEMENTS_NO_BUTTON_SCHEMA = "/html/body/div[3]/div/div[3]/div/div/div/button[x]/span";

    @FindBy(xpath = CATEGORY_BUTTON)
    private WebElement category_button;

    @FindBy(xpath = LOADING_KITTY)
    private WebElement loading_kitty;

    public Pagination(WebDriver driver) {
        super(driver);
    }

    public void chooseCategory() throws InterruptedException {

        this.category_button.click();

        WebDriverWait wait_error = new WebDriverWait(driver, 5);
        wait_error.until(
                ExpectedConditions.elementToBeClickable(By.xpath(FIRST_ELEMENTS_ON_PAGE_BUTTON)));
    }

    public void checkPagination() {

        int numberOfPages;
        int numberOfElements;
        String temp_elements_no_button = ELEMENTS_NO_BUTTON_SCHEMA;
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
                assertThat(numberOfElements).isEqualTo(5*i);
                }
        }
        temp = temp_elements_no_button.replace("x", Integer.toString(numberOfPages));
        driver.findElement(By.xpath(temp)).click();

        waitForKitty();

        assertThat(numberOfElements).isLessThanOrEqualTo(5*numberOfPages);
    }

    private void waitForKitty() {

        WebDriverWait wait_error = new WebDriverWait(driver, 5);
        wait_error.until(
                ExpectedConditions.invisibilityOf(loading_kitty));
    }

    private int checkNumberOfPages() {

        List<WebElement>pagesNo = driver.findElements(By.cssSelector(NUMBER_OF_PAGES));
        return pagesNo.size() - 2;
        // -1 one for next button, -1 one for previous button
    }

    private int numberOfElements() {

        List<WebElement>elementsNo=driver.findElements(By.cssSelector(CATEGORY_ELEMENTS));
        return elementsNo.size();
    }

}
