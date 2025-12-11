
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import org.example.PriceComparison;
import org.example.PriceReport;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceCheckTest {

    private final PriceComparison config;

    public PriceCheckTest(PriceComparison config) {
        this.config = config;
    }

    @Test
    public void fetchPrice() {

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false) // Set to true to run in background
            );

            Page page = browser.newPage();

            // CRITICAL FIX: Changed NETWORKIDLE to DOMCONTENTLOADED
            // NetworkIdle waits for all background scripts to stop, which never happens on these sites.
            page.navigate(
                    config.getUrl(),
                    new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
            );

            Locator priceLocator = page.locator(config.getPriceSelector()).first();

            // Wait up to 10 seconds for the element
            priceLocator.waitFor(new Locator.WaitForOptions().setTimeout(20000));

            // Sometimes the text has extra spaces/newlines, so we trim it
            String rawText = priceLocator.innerText().trim();
            System.out.println("📄 Raw Text Found: " + rawText);

            double price = parsePrice(rawText);
            System.out.println("💰 Parsed Price: " + price);

            // Add to report
            PriceReport.addEntry(config.getSiteName(), price);

            browser.close();
        } catch (Exception e) {
            System.err.println("❌ ERROR on " + config.getSiteName());
            System.err.println("   Reason: " + e.getMessage());
            // Log 0.0 or -1.0 to indicate failure in the report
            PriceReport.addEntry(config.getSiteName() + " (FAILED)", 0.0);
        }
    }

    private double parsePrice(String raw) {
        if (raw == null || raw.isEmpty()) return 0.0;

        // 1. Regex to find the number pattern (e.g., 18.499,00 or 18499)
        // Matches: Numbers with dots/commas
        Pattern pattern = Pattern.compile("([0-9]+[.,][0-9]+[.,]?[0-9]*)");
        Matcher matcher = pattern.matcher(raw);

        if (matcher.find()) {
            String num = matcher.group(1);
            // TR Logic: Remove dots (thousands), replace comma with dot (decimal)
            // Example: "18.499,00" -> "18499,00" -> "18499.00"
            String clean = num.replace(".", "").replace(",", ".");
            try {
                return Double.parseDouble(clean);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }
}