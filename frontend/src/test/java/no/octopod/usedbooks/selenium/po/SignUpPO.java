package no.octopod.usedbooks.selenium.po;

import no.octopod.usedbooks.selenium.PageObject;

public class SignUpPO extends LayoutPO {
    public SignUpPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Sign up");
    }

    public IndexPO createUser(String email, String password, String firstname, String lastname) {
        setText("email", email);
        setText("password", password);
        setText("firstname", firstname);
        setText("lastname", lastname);

        clickAndWait("submit");

        IndexPO po = new IndexPO(this);
        if (po.isOnPage()) {
            return po;
        }
        return null;
    }
}
