package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.HotelDetailsPage;
import pages.ReservationPage;
import utils.ExcelUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BookTest extends BaseTest{
    @DataProvider(name = "bookingData")
    public Object[][] getBookingData() throws Exception {
        String filePath = "src/test/resources/Book1.xlsx";
        return ExcelUtils.getData(filePath, "Sheet1");
    }

    @Test(dataProvider = "bookingData")
    public void BookingFlow(String location, String checkin, String checkout) throws Exception {
        SoftAssert soft = new SoftAssert();
        HomePage homePage = new HomePage(driver);

        // Step 1: Search with data from Excel
        homePage.searchLoctaion(location);
        homePage.selectDates(checkin,checkout);
        homePage.searchButton();
        homePage.findHotel("Tolip");

        // Switch to new window
        String parentWindow = driver.getWindowHandle();
        for (String window : driver.getWindowHandles()) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }

        // Step 2: Assertions in Hotel Details Page
        HotelDetailsPage tolipPage = new HotelDetailsPage(driver);
        String checkinPart = formatExcelDate(checkin);   // e.g. Oct 1
        String checkoutPart = formatExcelDate(checkout); // e.g. Oct 14

        System.out.println("Check-in Expected: " + checkinPart + " | UI: " + tolipPage.GetCheckInDate());
        System.out.println("Check-out Expected: " + checkoutPart + " | UI: " + tolipPage.GetCheckOutDate());

        soft.assertTrue(tolipPage.GetCheckInDate().contains(checkinPart),
                "Check-in date is displayed correctly: expected contains " + checkinPart);
        soft.assertTrue(tolipPage.GetCheckOutDate().contains(checkoutPart),
                "Check-out date is displayed correctly: expected contains " + checkoutPart);

        // Step 3: Reservation
        tolipPage.select_Bed();
        tolipPage.selectAmount();
        tolipPage.clickReserve();

        soft.assertAll();
    }

    @Test
    public void reservationPageTest(){
        // Step 4: Assertions in Reservation Page
        ReservationPage reer= new ReservationPage(driver);
        String nameOfHotel= reer.checkname();
        Assert.assertTrue(nameOfHotel.contains("Tolip"), "Hotel name contains 'Tolip'");
    }

    public String formatExcelDate(String excelDate) throws Exception {
        SimpleDateFormat excelFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = excelFormat.parse(excelDate);

        // خليها Oct 1 بدل Oct 01
        SimpleDateFormat uiFormat = new SimpleDateFormat("EEE d MMM");
        return uiFormat.format(date);
    }

}
