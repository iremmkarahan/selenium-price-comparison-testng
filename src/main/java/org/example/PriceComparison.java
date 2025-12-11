package org.example;

import org.openqa.selenium.By;

public class PriceComparison {

    private final String siteName;
    private final String productUrl;
    private final By priceLocator;

    public PriceComparison(String siteName, String productUrl, By priceLocator) {
        this.siteName = siteName;
        this.productUrl = productUrl;
        this.priceLocator = priceLocator;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public By getPriceLocator() {
        return priceLocator;
    }

    @Override
    public String toString() {
        return "PriceComparison{" +
                "siteName='" + siteName + '\'' +
                ", productUrl='" + productUrl + '\'' +
                '}';
    }
}