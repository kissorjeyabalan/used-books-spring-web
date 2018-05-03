package no.octopod.usedbooks.selenium;

import no.octopod.usedbooks.Application;
import no.octopod.usedbooks.selenium.po.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumLocalIT {
    private static WebDriver driver;

    @LocalServerPort
    private int port;

    @BeforeClass
    public static void initTest() {
        driver = SeleniumDriverHandler.getChromeDriver();
        if (driver == null) {
            throw new AssumptionViolatedException("Cannot find/initialize Chrome driver");
        }

    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.close();
        }
    }

    @Before
    public void before() {
        getDriver().manage().deleteAllCookies();
        home = new IndexPO(getDriver(), getServerHost(), getServerPort());
        home.toStartingPage();
        assertTrue("Failed to start from Home Page", home.isOnPage());
    }

    protected WebDriver getDriver() { return driver; }
    protected String getServerHost() { return "localhost"; }
    protected int getServerPort() { return port; }

    private static final AtomicInteger counter = new AtomicInteger(0);

    private String getUniqueId() {
        return counter.getAndIncrement() + "@Selenium.IT";
    }

    private IndexPO home;

    private IndexPO createNewUser(String email, String password, String firstname, String lastname) {
        home.toStartingPage();
        SignUpPO signUpPO = home.toSignUp();
        IndexPO indexPO = signUpPO.createUser(email, password, firstname, lastname);
        assertNotNull(indexPO);
        return indexPO;
    }

    private IndexPO loginUser(String email, String password) {
        home.toStartingPage();
        LoginPO loginPO = home.toLogin();
        return loginPO.login(email, password);
    }

    @Test
    public void testDefaultBooks() {
        assertEquals(2, home.getNumbersOfListedBooks());
    }

    @Test
    public void testRegisterSelling() {
        assertFalse(home.isLoggedIn());
        assertFalse(home.isToggleSaleShown());

        String id = getUniqueId();
        home = createNewUser(id, id, id, id);

        assertTrue(home.isOnPage());
        assertTrue(home.isLoggedIn());
        assertTrue(home.isToggleSaleShown());

        int pos0init = home.getSellers(0);
        int pos1init = home.getSellers(1);
        int pos2init = home.getSellers(2);

        home.toggleSale(0);
        assertEquals((pos0init + 1), home.getSellers(0));
        assertEquals(pos1init, home.getSellers(1));
        assertEquals(pos2init, home.getSellers(2));

        home.toggleSale(0);
        assertEquals(pos0init, home.getSellers(0));

        home.toggleSale(0);
        assertEquals((pos0init + 1), home.getSellers(0));

        home.doLogout();
        assertEquals((pos0init + 1), home.getSellers(0));


    }

    @Test
    public void testBookDetails() {
        String user1 = getUniqueId();
        home = createNewUser(user1, user1, user1, user1);

        assertTrue(home.isOnPage());
        assertTrue(home.isLoggedIn());

        home.toggleSale(0);

        BookDetailPO bookDetail = home.goToBookDetailPage(0);
        assertTrue(bookDetail.sellerIsListed(user1));

        bookDetail.doLogout();
        assertFalse(home.isLoggedIn());

        String user2 = getUniqueId();
        home = createNewUser(user2, user2, user2, user2);
        assertTrue(home.isLoggedIn());

        bookDetail = home.goToBookDetailPage(0);
        assertTrue(bookDetail.sellerIsListed(user1));

        home.toStartingPage();
        home.doLogout();
        LoginPO login = home.toLogin();
        home = login.login(user1, user1);
        home.toggleSale(0);

        bookDetail = home.goToBookDetailPage(0);
        assertFalse(bookDetail.sellerIsListed(user1));
    }

    @Test
    public void testSellNewBook() {
        String user = getUniqueId();
        home = createNewUser(user, user, user, user);

        assertTrue(home.isLoggedIn());

        SellBookPO sbpo = home.toSellBook();
        home = sbpo.sellBook("123456789", "Book", "Author", "Course");

        assertEquals(3, home.getNumbersOfListedBooks());
    }

}
