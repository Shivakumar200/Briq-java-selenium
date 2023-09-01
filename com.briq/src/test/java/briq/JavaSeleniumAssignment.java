package briq;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
public class JavaSeleniumAssignment {

	public static void main(String[] args) {
		// Set up the folder path
		String folderPath = "./HOME/briq";

		// Check if the folder exists, and create it if it doesn't
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdirs(); // Creates the folder and any parent folders if needed
		}


		WebDriverManager.chromedriver().setup();
		ChromeOptions op=new ChromeOptions();
		op.addArguments("--remote-allow-origins=*");

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\MWCL\\Desktop\\ChromeDrive\\chromedriver-win64\\chromedriver.exe");

		WebDriver driver=new ChromeDriver(op);

		driver.get("https://chromedriver.chromium.org/downloads");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Navigate to the webpage
		driver.get("http://the-internet.herokuapp.com/challenging_dom");

		// Find and read the table headers
		WebElement headerRow = driver.findElement(By.cssSelector(".large-10 thead tr"));
		List<WebElement> headerCells = headerRow.findElements(By.tagName("th"));
		List<String> tableHeaders = new ArrayList<>();
		for (WebElement cell : headerCells) {
			tableHeaders.add(cell.getText());
		}

		// Find and read the table data
		WebElement table = driver.findElement(By.cssSelector(".large-10 tbody"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		List<List<String>> tableData = new ArrayList<>();

		for (WebElement row : rows) {
			List<String> rowData = new ArrayList<>();
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for (WebElement cell : cells) {
				rowData.add(cell.getText());
			}
			tableData.add(rowData);
		}

		// Format the timestamp
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yy-HH-mm-ss");
		String timestamp = LocalDateTime.now().format(formatter);

		// Generate a CSV file with the formatted timestamp
		String fileName = folderPath + "/webtable_" + timestamp + ".csv";

		try (FileWriter csvWriter = new FileWriter(fileName)) {
			// Write headers to the CSV file
			csvWriter.append(String.join(",", tableHeaders));
			csvWriter.append("\n");

			// Write table data to the CSV file
			for (List<String> rowData : tableData) {
				csvWriter.append(String.join(",", rowData));
				csvWriter.append("\n");
			}
			System.out.println("CSV file created successfully: " + fileName);

		} catch (IOException e) {
			e.printStackTrace();
		}
		// Close the WebDriver
		driver.quit();
	}
}