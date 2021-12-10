import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class AppTest {
	public static WebDriver driver;
	
	@BeforeAll
	public static void initialize() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Youssef\\Documents\\TP Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}
	public boolean assertBooleanValue(boolean value) {
		assertEquals(value, true);
		return value;
	}
	@Test
	void test() throws InterruptedException {
        driver.get("http://todomvc.com");
        driver.findElement(By.partialLinkText("Vue.js")).click();
        addTodoItem("Meet a Friend");
        addTodoItem("Buy Meat");
        addTodoItem("Clean the Car");
        removeToDoItem(1);
        validateExpectedItemsLeft(2);
        
	}
	@ParameterizedTest
	@ValueSource(strings = {
	"Backbone.js", 
	"AngularJS",
	"Ember.js",
	"React"
	})
	public void validateTechnology(String technology) {
        driver.get("http://todomvc.com");
        driver.findElement(By.partialLinkText(technology)).click();
        addTodoItem("Meet a Friend");
        addTodoItem("Buy Meat");
        addTodoItem("Clean the Car");
        removeToDoItem(1);
        validateExpectedItemsLeft(2);
	}

	public void validateExpectedItemsLeft(int expectedNumberOfItemsLeft) {
		String expectedText;
		if (expectedNumberOfItemsLeft == 1) {
			expectedText = expectedNumberOfItemsLeft + " item left";
		}
		else {
			expectedText = expectedNumberOfItemsLeft + " items left";
		}
        WebElement count = driver.findElement(By.xpath("//*[@id='todo-count' or @class='todo-count']"));
		WebDriverWait wait = new WebDriverWait(driver, 2);
        boolean expectedValue = wait.until(ExpectedConditions.textToBePresentInElement(count, expectedText)).booleanValue();
        assertEquals(expectedValue, true);
	}
	
	public void addTodoItem(String todoText) {
        driver.findElement(By.tagName("input")).sendKeys(todoText);
        driver.findElement(By.tagName("input")).sendKeys(Keys.ENTER);
	}
	
	public void removeToDoItem(int elementIndex) {
        driver.findElement(By.cssSelector("li:nth-child(" + elementIndex + ") > div > input")).click();
	}
}
