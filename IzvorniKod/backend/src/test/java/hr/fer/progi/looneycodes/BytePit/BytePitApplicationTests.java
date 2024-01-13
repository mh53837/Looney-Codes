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

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.cssSelector(".loginDiv button")).click();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.findElement(By.cssSelector("input[name='korisnickoIme']")).sendKeys("spiderman");
		driver.findElement(By.cssSelector("input[name='lozinka']")).sendKeys("iLoveMJ");
		driver.findElement(By.cssSelector("button[type='submit']")).click();

		wait.until(d -> d.findElement(By.cssSelector(".loginDiv button")).getText().equals("odjavi se!"));

		assertEquals("http://localhost:8080/", driver.getCurrentUrl());
		driver.quit();

	}

	@Test
	public void test_create_zadatak_success() {
		WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		driver.get("http://localhost:8080/");

		WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".loginDiv button")));
		loginButton.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("korisnickoIme")));
		driver.findElement(By.name("korisnickoIme")).sendKeys("buttler");
		driver.findElement(By.name("lozinka")).sendKeys("robin");

		WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		submitButton.click();

		WebElement zadaciButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='zadaci']")));
		zadaciButton.click();
		zadaciButton.click();

		wait.until(ExpectedConditions.urlContains("/problems/all"));
		WebElement noviZadatakButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".addBtn")));
		noviZadatakButton.click();

		// Unos podataka za novi zadatak
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("nazivZadatka")));
		driver.findElement(By.name("nazivZadatka")).sendKeys("testni zadatak");
		driver.findElement(By.cssSelector("input[name='tekstZadatka']")).sendKeys("testni tekst");
		driver.findElement(By.cssSelector("span.ant-select-selection-item")).click();
		driver.findElements(By.cssSelector(".ant-select-item-option-content")).get(1).click();

		driver.findElement(By.cssSelector("button[type='submit']")).click();

		WebElement spremiPromjeneButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='spremi promjene']")));
		spremiPromjeneButton.click();

		assertTrue(driver.getCurrentUrl().contains("/user/profile/buttler"));
		assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='testni zadatak']"))).isDisplayed());
	}
}
