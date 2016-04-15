package pages;

import org.openqa.selenium.WebDriver;

/**
 * Created by vradchenko on 4/15/2016.
 */
public class SeleniumPage extends BasePage {
    public SeleniumPage(WebDriver driver) {
        super(driver);
    }

    public void open(final String url){
        driver.get(url);
    }

    @Override
    protected void waitPageToLoad() {
        //emptyImpl
    }
}
