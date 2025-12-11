

import org.example.PriceReport;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

public class FinalReportTest {

    @Tests
    public void dummy() {
        // This test doesn't do anything; it's just here so TestNG loads this class.
        // The real magic is in @AfterSuite.
    }

    @AfterSuite
    public void printReport() {
        PriceReport.printSummary();
    }
}