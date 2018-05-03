package no.octopod.usedbooks.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SeleniumDriverHandler {
    private static boolean tryToSetGeckoIfExists(String property, Path path) {
        if (Files.exists(path)) {
            System.setProperty(property, path.toAbsolutePath().toString());
            return true;
        }
        return false;
    }

    private static boolean setupDriverExecutable(String executableName, String property) {
        String homeDir = System.getProperty("user.home");
        if (!tryToSetGeckoIfExists(property, Paths.get(homeDir, executableName))) {
            if (!tryToSetGeckoIfExists(property, Paths.get(homeDir, executableName + ".exe"))) {
                return false;
            }
        }

        return true;
    }

    public static WebDriver getChromeDriver() {
        boolean OK = setupDriverExecutable("chromedriver", "webdriver.chrome.driver");
        if(!OK) {
            return null;
        }
        return new ChromeDriver();
    }

    public static WebDriver getFirefoxDriver() {
        setupDriverExecutable("geckodriver", "webdriver.gecko.driver");
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
        desiredCapabilities.setCapability("marionette", true);
        desiredCapabilities.setJavascriptEnabled(true);
        return new FirefoxDriver(desiredCapabilities);
    }
}
