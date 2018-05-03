package no.octopod.usedbooks.selenium.po;

import no.octopod.usedbooks.selenium.PageObject;
import org.openqa.selenium.WebDriver;

public class SellBookPO extends LayoutPO {
    public SellBookPO(PageObject other) {
        super(other);
    }

    public SellBookPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Sell");
    }

    public IndexPO sellBook(String isbn, String title, String authors, String course) {
        setText("isbn", isbn);
        setText("title", title);
        setText("authors", authors);
        setText("course", course);

        clickAndWait("submit");
        IndexPO po = new IndexPO(this);
        if (po.isOnPage()) {
            return po;
        }
        return null;
    }
}
