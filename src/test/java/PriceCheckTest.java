
import org.example.PriceReport;
import org.example.PriceComparison;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class PriceCheckTest {

    private final PriceComparison config;
    private WebDriver driver;

    // Constructor used by @Factory
    public PriceCheckTest(PriceComparison config) {
        this.config = config;
    }

    @BeforeClass
    public void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void fetchProductPrice() throws InterruptedException {
        System.out.println("Running test for site: " + config.getSiteName());

        driver.get(config.getProductUrl());

        // Optional small sleep if page loads slowly; better is explicit waits for production code
        Thread.sleep(2000);

        WebElement priceElement = driver.findElement(config.getPriceLocator());
        String priceText = priceElement.getText();

        System.out.println("Raw price text for " + config.getSiteName() + ": " + priceText);

        double price = parsePrice(priceText);
        System.out.println("Parsed price for " + config.getSiteName() + ": " + price);

        Assert.assertTrue(price > 0.0, "Price should be greater than 0");

        // Store in report
        PriceReport.addEntry(config.getSiteName(), config.getProductUrl(), price);
    }

    /**
     * Very simple price parser:
     * - keep digits, dot, comma
     * - replace comma with dot if needed
     * - strip currency symbols
     */
    private double parsePrice(String raw) {
        String cleaned = raw.replaceAll("[^0-9,\\.]", ""); // keep digits + , + .
        // If there are both ',' and '.', you might need a smarter strategy depending on locale.
        // For simplicity, assume something like "1.234,56" or "1234.56".
        if (cleaned.contains(",") && !cleaned.contains(".")) {
            cleaned = cleaned.replace(',', '.');
        } else if (cleaned.contains(",") && cleaned.contains(".")) {
            // naive: remove thousands separator
            // Example: "1,234.56" -> remove ',' -> "1234.56"
            cleaned = cleaned.replace(",", "");
        }

        if (cleaned.isEmpty()) {
            throw new IllegalArgumentException("Unable to parse price from: " + raw);
        }

        return Double.parseDouble(cleaned);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void afterSuite() {
        // Note: @AfterSuite on an instance is a bit tricky.
        // You can alternatively move this method to a separate class.
        // We’ll handle it in a separate "report" class instead.
    }
}