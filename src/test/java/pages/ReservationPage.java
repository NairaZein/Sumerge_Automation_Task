package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ReservationPage {
    public ReservationPage(WebDriver driver) {
        this.driver = driver;
    }

    private WebDriver driver;
    private WebDriverWait wait;
    By CheckName= By.xpath("//*[@id=\"bodyconstraint-inner\"]/div[3]/div[3]/aside/div/div[1]/div/div/div/div[2]/div/div/div/div[1]/div[1]/h1");

    public String checkname(){
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(90));
        wait.until(ExpectedConditions.visibilityOfElementLocated(CheckName));
      return driver.findElement(CheckName).getText() ;
    }
}
