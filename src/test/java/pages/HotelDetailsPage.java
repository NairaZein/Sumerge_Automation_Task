package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HotelDetailsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By hotelTitle = By.xpath("//*[@id=\"hp_hotel_name\"]/div/h2");
    private  By CheckInDate=By.xpath("//button[@data-testid='date-display-field-start']/span");
    private  By CheckOutDate =By.xpath("//button[@data-testid='date-display-field-end']/span");
    By NoOFBeds = By.xpath("//*[@id=\"hprt-table\"]/tbody/tr[1]/th/div/div[3]/div[1]/label[2]/ul/li");
    By Amount = By.xpath("//select[contains(@class,'hprt-nos-select')]");
    By Amount2 = By.xpath("//select[starts-with(@id,'hprt_nos_select_')]/option[2]");
    By click=By.xpath("//button[.//span[contains(text(),\"reserve\")]]");
    public HotelDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getHotelTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(hotelTitle)).getText();
    }
public String GetCheckInDate(){

        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(20));
       return wait.until(ExpectedConditions.visibilityOfElementLocated(CheckInDate)).getText();
}
    public String GetCheckOutDate(){

        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CheckOutDate)).getText();
    }
    public void select_Bed(){
        boolean found = false;
        while(!found) {
            try {
                WebElement element = driver.findElement(NoOFBeds);
                if(element.isDisplayed()) {
                    element.click();
                    found = true;
                }
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,100);"); // scroll down 100px
            }
        }
    }
public  void selectAmount(){

     WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(30));
     wait.until(ExpectedConditions.visibilityOfElementLocated(Amount)).click();
     driver.findElement(Amount2).click();
}
public  void clickReserve(){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(45));
        wait.until(ExpectedConditions.visibilityOfElementLocated(click)).click();
}
}


