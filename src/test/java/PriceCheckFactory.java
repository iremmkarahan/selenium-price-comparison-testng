
import org.example.PriceComparison;
import org.example.PriceReport;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Factory;

public class PriceCheckFactory {

    @Factory
    public Object[] createInstances() {
        return new Object[] {
                // 1. Apple: Worked perfectly, but adding backups just in case
                new PriceCheckTest(
                        new PriceComparison(
                                "Apple Türkiye",
                                "https://www.apple.com/tr/shop/buy-watch/apple-watch",
                                ".rf-pdp-price-current, .rf-bfe-header-price-current, [data-autom='full-price']"
                        )
                ),

                // 2. Gürgençler: Added '.product-price' and '.price' as backups
                new PriceCheckTest(
                        new PriceComparison(
                                "Gürgençler",
                                "https://www.gurgencler.com.tr/apple-watch-series-11-gps-42mm-simsiyah-aluminyum-kasa-ve-siyah-spor-kordon-mequ4tu-a-m-l?qty=1",
                                ".product-price-new, .product-price, .price, .current-price"
                        )
                ),

                // 3. PT: Added '.product-price' and '.price' as backups
                new PriceCheckTest(
                        new PriceComparison(
                                "PT",
                                "https://www.pt.com.tr/apple-watch-series-11-gps-42mm-simsiyah-aluminyum-kasa-siyah-spor-kordon-m-l-mequ4tu-a",
                                ".prd_price, .product-price, .price-tag, .price"
                        )
                )
        };
    }

    @AfterSuite
    public void generateFinalReport() {
        PriceReport.generateReport();
    }
}