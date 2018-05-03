package no.octopod.usedbooks.selenium.po;

import no.octopod.usedbooks.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BookDetailPO extends LayoutPO {
    public BookDetailPO(PageObject other) {
        super(other);
    }

    public BookDetailPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("ISBN:");
    }

    public boolean sellerIsListed(String seller) {
        String xpath = "//p[@id='sellerId']";

        List<WebElement> elements = getDriver().findElements(By.xpath(xpath));
        for (WebElement element : elements) {
            if (element.getText().contains(seller)) return true;
        }
        return false;
    }

}
