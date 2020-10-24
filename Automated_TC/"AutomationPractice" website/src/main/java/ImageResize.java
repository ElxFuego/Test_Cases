import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

public class ImageResize extends PageObject {

    private final String IMAGES_WITH_QUICK_VIEW = "//ul[@id=\"homefeatured\"]/child::node() //a[@class=\"product_img_link\"]";
    private final String IMAGES_FROM_QUICK_VIEWS = "//ul[@id=\"homefeatured\"]/child::node()  //img[@class=\"replace-2x img-responsive\"]";
    private final String POP_UP_CLOSE_BUTTON = "//a[@title=\"Close\"]";
    private final String RESIZED_IMAGE = "//*[@id=\"bigpic\"]";
    private final String SECOND_IFRAME = "/html/body/div[2]/div/div/div/div/iframe";


    @FindBy(xpath = IMAGES_WITH_QUICK_VIEW)
    private WebElement imagesOnPageQuickView;

    public ImageResize(WebDriver driver) {
        super(driver);
    }

    WebDriverWait wait = new WebDriverWait(driver, 10);
    JavascriptExecutor js = (JavascriptExecutor) driver;    //useful JS stuff

    public void checkImage() {

        List<WebElement> numberOfImagesToClick = driver.findElements(By.xpath(IMAGES_WITH_QUICK_VIEW));

        int widthBefore, heightBefore, widthAfter, heightAfter;
        int imageClickTrials = 0;
        boolean clickIntercepted;

        for(WebElement temp : numberOfImagesToClick)
        {
          wait.until((ExpectedConditions.presenceOfElementLocated(By.xpath(IMAGES_WITH_QUICK_VIEW))));

          js.executeScript(
                  "arguments[0].scrollIntoView(); window.scrollBy(0, -window.innerHeight / 4) ;", imagesOnPageQuickView); //useful JS stuff

          widthBefore = driver.findElement(By.xpath(IMAGES_FROM_QUICK_VIEWS)).getSize().getWidth();
          heightBefore = driver.findElement(By.xpath(IMAGES_FROM_QUICK_VIEWS)).getSize().getHeight();

          System.out.println("First image: " + widthBefore + " " + heightBefore );

          wait.until((ExpectedConditions.elementToBeClickable(temp)));

          do{
            try {
                temp.click();
                clickIntercepted = false;
            } catch (Exception e) {
                js.executeScript("window.scrollBy(0,1000)");
                clickIntercepted = true;
                imageClickTrials++;
            }
            assertThat(imageClickTrials).isLessThan(10);
          }
          while(clickIntercepted);

          wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(POP_UP_CLOSE_BUTTON)));
          this.driver.switchTo().frame(driver.findElement(By.xpath(SECOND_IFRAME)));

          widthAfter = driver.findElement(By.xpath(RESIZED_IMAGE)).getSize().getWidth();
          heightAfter = driver.findElement(By.xpath(RESIZED_IMAGE)).getSize().getHeight();

          System.out.println("Resized image: " + widthAfter + " " + heightAfter );
          System.out.println("------------------------------------------");

          assertThat(widthBefore).isLessThan(widthAfter);
          assertThat(heightBefore).isLessThan(heightAfter);

          this.driver.switchTo().defaultContent();
          temp.findElement(By.xpath(POP_UP_CLOSE_BUTTON)).click();
        }
    }
}
