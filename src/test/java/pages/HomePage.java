package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    By Hotel = By.xpath("//div[@data-testid='title' and contains(text(),'Tolip')]");

    //  private final WebDriverWait wait;
    By searchCityBox = By.cssSelector("input[name='ss']");
    private By searchButton = By.cssSelector("button[type='submit']");
    private By calender=By.cssSelector("button[data-testid='searchbox-dates-container']");
    private By nextMonth = By.cssSelector("button[aria-label='Next month']");

    private By dateFrom = By.cssSelector("span[data-testid='date-display-field-start']");
    private By dateTo = By.cssSelector("span[data-testid='date-display-field-end']");



    public HomePage(WebDriver driver) {
        this.driver= driver;
        
    }

    public void searchLoctaion(String location){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until search box is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchCityBox));
        driver.findElement(searchCityBox).clear();
        driver.findElement(searchCityBox).sendKeys(location);

    }

    public void selectDates(String checkin, String checkout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // افتحي الكاليندر مرة واحدة
        wait.until(ExpectedConditions.visibilityOfElementLocated(calender));
        driver.findElement(calender).click();

        // اختاري check-in من الإكسل زي ما هو
        By checkinLocator = By.cssSelector("span[data-date='" + checkin + "']");
        wait.until(ExpectedConditions.elementToBeClickable(checkinLocator));
        driver.findElement(checkinLocator).click();

        // اختاري check-out من الإكسل زي ما هو
        By checkoutLocator = By.cssSelector("span[data-date='" + checkout + "']");
        wait.until(ExpectedConditions.elementToBeClickable(checkoutLocator));
        driver.findElement(checkoutLocator).click();
    }

    public void searchButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));
        driver.findElement(searchButton).click();

    }
    public void findHotel(String hotelName) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
      //  System.out.println("currnet URL" + driver.getCurrentUrl());
        for (int i = 0; i < 15; i++) { // جربي 15 مرة scroll
            try {
                // لو العنصر ظهر في الـ DOM
                WebElement element = driver.findElement(
                        By.xpath("//div[@data-testid='title' and contains(text(),'" + hotelName + "')]")
                );

                // scroll للعنصر
                js.executeScript("arguments[0].scrollIntoView(true);", element);

                // اتأكد إنه clickable
                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
              //  System.out.println("✅ Found and clicked hotel: " + hotelName);
                return;

            } catch (Exception e) {
                // لو مش لاقيه → Scroll لتحت 800px
                js.executeScript("window.scrollBy(0,800)");
                try {
                    Thread.sleep(1500); // سيب فرصة للصفحة تحمّل فنادق جديدة
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
       // throw new RuntimeException("❌ Hotel not found after scrolling: " + hotelName);


    }

}
