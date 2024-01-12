package hr.fer.progi.looneycodes.BytePit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BytePitApplicationTests {

	@BeforeAll
	public static void setUp() {
		System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
	}
	@Test
	public void test_login_with_valid_credentials() {
		WebDriver driver = new FirefoxDriver();
		driver.get("https://bytepit.onrender.com");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.cssSelector(".loginDiv button")).click();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.findElement(By.cssSelector("input[name='korisnickoIme']")).sendKeys("spiderman");
		driver.findElement(By.cssSelector("input[name='lozinka']")).sendKeys("iLoveMJ");
		driver.findElement(By.cssSelector("button[type='submit']")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(d -> d.findElement(By.cssSelector(".loginDiv button")).getText().equals("odjavi se!"));

		assertEquals("https://bytepit.onrender.com/", driver.getCurrentUrl());
		driver.quit();


	}

}
