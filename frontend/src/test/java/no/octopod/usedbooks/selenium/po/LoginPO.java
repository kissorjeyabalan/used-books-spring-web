package no.octopod.usedbooks.selenium.po;

import javafx.print.PageLayout;
import no.octopod.usedbooks.selenium.PageObject;

public class LoginPO extends LayoutPO {
    public LoginPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Log in");
    }

    public IndexPO login(String email, String password) {
        setText("username", email);
        setText("password", password);

        clickAndWait("submit");

        IndexPO po = new IndexPO(this);
        if (po.isOnPage()) {
            return po;
        }
        return null;
    }
}
