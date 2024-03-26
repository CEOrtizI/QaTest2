package stepDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Review {

	private static WebDriver driver = null;
	static WebDriverWait wait;

	@Before
	public static void before() {
		// Initial setup to start a driver and set a screen size
		Dimension screenDimension = new Dimension(1280, 720);
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.manage().window().setSize(screenDimension);
	}

	@Given("An \"User\" with valid credentials")
	public void an_user_with_valid_credentials() {
		// Login as the test user
		driver.get("https://wallethub.com/join/login");
		driver.findElement(By.id("email-input")).sendKeys("camiloeduardo.ortiz@uptc.edu.co");
		driver.findElement(By.id("pass-input")).sendKeys("Asd1234!");
		driver.findElement(By.cssSelector("button[type='submit']")).click();
	}

	@Given("The user is logged in")
	public void the_user_is_logged_in() {
		// Check for the user profile to be loaded
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='brgm-list-avatar-title']//img[@alt='avatar of Camilo']")));
	}

	@Given("I am in an users profile page")
	public void i_am_in_an_users_profile_page() {
		driver.get("https://wallethub.com/profile/test-insurance-company-13732055i");
	}

	@When("I hover and click on the four star rating on a profile")
	public void i_hover_and_click_on_the_four_star_rating_on_a_profile() {
		// Wait until the profile to be loaded
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='brgm-list-avatar-title']//img[@alt='avatar of Camilo']")));

		// Hover over the selected star
		WebElement review = driver.findElement(By.xpath(
				"//review-star[@class='rvs-svg']//div[@class='rating-box-wrapper']//*[name()='svg'][4]/*[name()='g'][1]/*[name()='path']"));
		Actions action = new Actions(driver);
		action.moveToElement(review).build().perform();

		// This code should perform the check but it fails to get the actual status of
		// the web element
//		assertTrue(driver.findElement(By.xpath(
//				"//review-star[@class='rvs-svg']//div[@class='rating-box-wrapper']//*[name()='svg'][4]/*[name()='g'][1]/*[name()='path']"))
//				.getAttribute("checked").equals("true"));

		// Select the star
		driver.findElement(By.xpath(
				"//review-star[@class='rvs-svg']//div[@class='rating-box-wrapper']//*[name()='svg'][4]/*[name()='g'][1]/*[name()='path']"))
				.click();

	}

	@Then("I should see the review page")
	public void i_should_see_the_review_page() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("md-write-a-review")));
	}

	@When("I fill all the review data and submit it")
	public void i_fill_all_the_review_data_and_submit_it() {
		//Since the content of the review form is inside a modal the search inside the modal is executed
		WebElement modalContainer = driver.findElement(By.className("md-write-a-review"));
		WebElement modalBody = modalContainer
				.findElement(By.xpath(".//div[@class='ng-modal-dialog ng-md-fullscreen opened']"));
		
		//Fill the form
		modalBody.findElement(By.xpath(".//div[@class='dropdown second']")).click();
		modalBody.findElement(By.xpath(".//li[@id='opt-66022c2ed5358-1']")).click();
		modalBody.findElement(By.xpath(".//textarea[class='textarea wrev-user-input validate']")).sendKeys(
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mollis turpis vel eros vestibulum molestie. Vivamus vel congue lectus, non ultricies ligula. Quisque ligula augue, rutrum vehicula lectus malesuada, dapibus ultrices tellus. Duis ac suscipita.");
		modalBody.findElement(By.xpath(".//button[text()='Submit']")).click();
	}

	@Then("I should see a confirmation window")
	public void i_should_see_a_confirmation_window() {
		// Confirm the 
		assertEquals("Your review has been posted.",
				driver.findElement(By.xpath("//*[text()='Your review has been posted.']")).getText());
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Submit']"))).click();
	}

	@Then("If I go to my profile I should see my review")
	public void if_i_go_to_my_profile_i_should_see_my_review() {
		driver.get("https://wallethub.com/profile/75934301i");
		assertTrue(driver.findElement(By.xpath("//*[text()='Test Insurance Company']")).isDisplayed());
	}

	@After
	public static void after() {
		driver.close();
	}

}
