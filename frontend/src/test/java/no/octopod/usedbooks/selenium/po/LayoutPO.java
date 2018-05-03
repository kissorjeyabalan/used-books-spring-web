package no.octopod.usedbooks.selenium.po;

import no.octopod.usedbooks.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public abstract class LayoutPO extends PageObject {
    public LayoutPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public LayoutPO(PageObject other) {
        super(other);
    }

    public SignUpPO toSignUp() {
        clickAndWait("linkToSignupId");
        SignUpPO po = new SignUpPO(this);
        assertTrue(po.isOnPage());
        return po;
    }

    public LoginPO toLogin() {
        clickAndWait("linkToLoginId");
        LoginPO po = new LoginPO(this);
        assertTrue(po.isOnPage());
        return po;
    }

    public IndexPO doLogout() {
        clickAndWait("logoutId");
        IndexPO po = new IndexPO(this);
        assertTrue(po.isOnPage());
        return po;
    }

    public SellBookPO toSellBook() {
        clickAndWait("sellBookId");
        SellBookPO po = new SellBookPO(this);
        assertTrue(po.isOnPage());
        return po;
    }

    public boolean isLoggedIn() {
        return getDriver().findElements(By.id("logoutId")).size() > 0 &&
                getDriver().findElements((By.id("linkToSignupId"))).isEmpty();
    }
}
