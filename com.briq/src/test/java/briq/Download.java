package briq;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Download
{
	public static void main(String[] args) {

		WebDriverManager.chromedriver().setup();
		ChromeOptions op=new ChromeOptions();
		op.addArguments("--remote-allow-origins=*");
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\MWCL\\Desktop\\ChromeDrive\\chromedriver-win64\\chromedriver.exe");
		
		WebDriver driver=new ChromeDriver(op);
		
		driver.get("https://the-internet.herokuapp.com/download");
		
		String fileName="depression.txt";
		
		WebElement txt=driver.findElement(By.xpath("//a[text()='depression.txt']"));

		Assert.assertEquals(fileName, txt.getText());
		
		txt.click();
	}
}