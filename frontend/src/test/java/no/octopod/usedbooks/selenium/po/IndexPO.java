package no.octopod.usedbooks.selenium.po;

import no.octopod.usedbooks.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IndexPO extends LayoutPO {
    public IndexPO(PageObject other) {
        super(other);
    }

    public IndexPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public void toStartingPage() {
        getDriver().get(host + ":" + port);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Home");
    }

    public int getNumbersOfListedBooks() {
        return getDriver().findElements(By.xpath("//table[@id='bookTable']/tbody/tr")).size();
    }

    public BookDetailPO goToBookDetailPage (int position) {
        clickAndWait("bookTable:" + position + ":moreInfo");

        BookDetailPO po = new BookDetailPO(this);
        if (po.isOnPage()) {
            return po;
        }
        return null;
    }

    public void toggleSale(int position) {
        clickAndWait("bookTable:" + position + ":toggleBookSaleForm:toggleSale");
    }

    public int getSellers(int position) {
        String xpath = "//*[@id='bookTable:"+position+":sellers']";
        int i = 0;
        try  {
            i = Integer.parseInt(getDriver().findElement(By.xpath(xpath)).getText());
            return i;
        } catch (Exception e) {
            return i;
        }
    }

    public boolean isSellBookWhenNoneForSaleDisplayed() {
        return getDriver().findElements(By.id("linkToSellBook")).size() > 0;
    }

    public boolean isNoBooksForSaleDisplayed() {
        return getDriver().findElements(By.id("noBooksForSaleId")).size() > 0;
    }

    public boolean isToggleSaleShown() {
        int i = getDriver().findElements(By.xpath("//*[contains(@*,'toggleSale')]")).size();
        System.out.println("TOGGLE SALE HIDDEN SIZE: " + i);
        return i > 0;
    }
}
