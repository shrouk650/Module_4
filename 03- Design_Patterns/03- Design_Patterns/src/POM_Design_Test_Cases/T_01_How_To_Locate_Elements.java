package POM_Design_Test_Cases;

import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class T_01_How_To_Locate_Elements {

	WebDriver driver;
	static Home_Page home = new Home_Page();
	static Sign_in sign_in = new Sign_in();

	@BeforeTest
	public void Launch_Browser() {

		String chromePath = System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromePath);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://www.google.com/?hl=ar");
		driver.findElement(By.partialLinkText("Eng")).click();
	}

	@Test(priority = 1) // Login without using email field
	public void Login_With_Invalid_Email() throws InterruptedException {

		home.SignInBtn(driver).click();

		Sign_in sign_in = new Sign_in();
		sign_in.Enter_Email(driver).sendKeys("test");
		sign_in.Next_button(driver).click();
		Thread.sleep(2000);
		String actualResult;
		actualResult = sign_in.actual_Result(driver).getText();
		System.out.println(actualResult);
		Reporter.log(actualResult);
		assertEquals(actualResult, "Couldn’t find your Google Account");

	}

	@Test(priority = 2)
	public void Verify_Forgot_Email_URL() {
		home.SignInBtn(driver).click();
		driver.findElement(By.xpath("//button[@jsname=\"Cuz2Ue\"]")).click();
		String actualResult;
		actualResult = driver.getCurrentUrl();
		System.out.println(actualResult);
		Reporter.log(actualResult);
		assertTrue(actualResult.contains("/signin/v2/usernamerecovery"));

	}

	@Test(priority = 3)
	//, dependsOnMethods = { "Login_With_Invalid_Email", "Verify_Forgot_Email_URL" }
	public void Google_Search() {
		
		home.search(driver);
		System.out.println(home.search_result(driver).getText());
		Reporter.log(home.search_result(driver).getText());
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(home.serach_text(driver).getText().contains("713,000,000"), true);
		System.out.println(2 + 2);
	/*	try {
			soft.assertAll();
		} catch (Error e) {
			System.out.println(e.getMessage());
		}
*/
		soft.assertAll();
	}

	@AfterMethod
	public void GO_TO_Home_Page(ITestResult result) {
		if (result.isSuccess() == true){
			System.out.println("Passed");
		}
		else if(result.isSuccess() == false){
			System.out.println("failed");
		}
		driver.navigate().to("https://www.google.com/?hl=en");
	}

	@AfterTest
	public void Close_Driver() {
		driver.quit();
	}

}
