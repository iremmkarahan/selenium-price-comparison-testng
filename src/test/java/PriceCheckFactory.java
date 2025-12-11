

import org.example.PriceComparison;
import org.openqa.selenium.By;
import org.testng.annotations.Factory;

public class PriceCheckFactory {

    @Factory
    public Object[] createInstances() {

        PriceComparison site1 = new PriceComparison(
                "SiteA",
                "https://www.apple.com/tr/shop/buy-watch/apple-watch",
                By.cssSelector("span.price") // modify according to real page
        );

        PriceComparison site2 = new PriceComparison(
                "SiteB",
                "https://www.gurgencler.com.tr/apple-watch-series-11-gps-42mm-simsiyah-aluminyum-kasa-ve-siyah-spor-kordon-mequ4tu-a-m-l?qty=1",
                By.id("price") // modify according to real page
        );

        PriceComparison site3 = new PriceComparison(
                "SiteC",
                "https://www.pt.com.tr/apple-watch-series-11-gps-42mm-simsiyah-aluminyum-kasa-siyah-spor-kordon-m-l-mequ4tu-a",
                By.xpath("//span[@class='price']") // modify according to real page
        );

        return new Object[]{
                new PriceCheckTest(site1),
                new PriceCheckTest(site2),
                new PriceCheckTest(site3)
        };
    }
}