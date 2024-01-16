package hr.fer.progi.looneycodes.BytePit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BytePitApplicationTests {

	@BeforeAll
	public static void setUp() {
		System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
	}
	@Test
	public void test_login_with_valid_credentials() {
		WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("http://localhost:8080/");

		WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".loginDiv button")));
		loginButton.click();

		WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='korisnickoIme']")));
		usernameInput.sendKeys("spiderman");

		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='lozinka']")));
		passwordInput.sendKeys("iLoveMJ");

		WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		submitButton.click();

		Boolean logoutPrisutan = wait.until(ExpectedConditions.textToBe(By.cssSelector(".loginDiv button"), "odjavi se"));

		assertEquals("http://localhost:8080/", driver.getCurrentUrl());
		assertTrue(logoutPrisutan);
		driver.quit();
	}

	@Test
	public void test_login_with_invalid_credentials() {
		WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("http://localhost:8080/");

		WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".loginDiv button")));
		loginButton.click();

		WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='korisnickoIme']")));
		usernameInput.sendKeys("mojstarifrendimarokenrolbend");

		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='lozinka']")));
		passwordInput.sendKeys("netkotoodgorevidisve");

		WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		submitButton.click();

		Boolean loginPrisutan = wait.until(ExpectedConditions.textToBe(By.cssSelector(".loginDiv button"), "prijavi se"));

		assertEquals("http://localhost:8080/login", driver.getCurrentUrl());
		assertTrue(loginPrisutan);
		driver.quit();
	}

	@Test
	public void test_create_zadatak() {
		WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("http://localhost:8080/");

		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".loginDiv button"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("korisnickoIme"))).sendKeys("buttler");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("lozinka"))).sendKeys("robin");
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).submit();
		wait.until(ExpectedConditions.urlToBe("http://localhost:8080/"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='zadaci']"))).click();
		wait.until(ExpectedConditions.urlContains("/problems/all"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='novi zadatak']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("nazivZadatka"))).sendKeys("zivotjemore");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("tekstZadatka"))).sendKeys(
				"Da se ja pitam, ja bih proterao autobus ovuda." +
				" Nije to tako loše kao što izgleda.");
		WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.ant-select-selection-item")));
		dropdown.click();
		List<WebElement> bodoviOpcije = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".ant-select-item-option-content")));
		bodoviOpcije.get(1).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='spremi promjene']"))).click();

		assertTrue(driver.getCurrentUrl().contains("/user/profile/buttler"));
		assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='zivotjemore']"))).isDisplayed());
		driver.quit();
	}

}
