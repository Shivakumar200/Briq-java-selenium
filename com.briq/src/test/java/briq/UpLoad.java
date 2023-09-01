package briq;

import java.awt.AWTException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UpLoad 
{
	public static void main(String[] args) throws AWTException {

		WebDriverManager.chromedriver().setup();
		ChromeOptions op=new ChromeOptions();
		op.addArguments("--remote-allow-origins=*");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\MWCL\\Desktop\\ChromeDrive\\chromedriver-win64\\chromedriver.exe");
		WebDriver driver=new ChromeDriver(op);
		
		driver.get("https://the-internet.herokuapp.com/upload");
		
//		Robot ro=new Robot();
		
		WebElement up=driver.findElement(By.id("file-upload"));
		up.sendKeys("C://Users//MWCL//Desktop//depression.txt");
		
		driver.findElement(By.id("file-submit")).click();
		String message="File Uploaded!";
		
		
		WebElement msg=driver.findElement(By.xpath("//h3[text()='File Uploaded!']"));
		Assert.assertEquals(message,msg.getText());
	}
}
