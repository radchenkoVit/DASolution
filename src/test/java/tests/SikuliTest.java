package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.SeleniumPage;

import java.util.concurrent.TimeUnit;

public class SikuliTest {
    private WebDriver driver;
    private static int timeoutInSeconds = 5;
    private static String url = "http://www.seleniumhq.org/download/maven.jsp";
    private static String pathToImage = "testImage.png";

    /*Result:
    * X: 495, Y: 597

    ===============================================
    Default Suite
    Total tests run: 1, Failures: 0, Skips: 0
    ===============================================

    Note: Y value should be calibrated (cause Screen take coordinates for a screen, not only for browser)
    */

    @BeforeMethod
    public void setUp(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(timeoutInSeconds, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void sikuliTest(){
        SeleniumPage page = new SeleniumPage(driver);
        page.open(url);

        displayCoordinates(pathToImage);
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }




    private static void displayCoordinates(final String pathToImage){
        Screen screen = new Screen();

        try {
            Match match = screen.find(pathToImage);
            System.out.println(String.format("X: %s, Y: %s", match.getX(), match.getY()));
        } catch (FindFailed findFailed) {
            //Not Found
        }
    }


}
