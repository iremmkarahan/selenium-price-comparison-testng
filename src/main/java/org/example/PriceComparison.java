package org.example;

public class PriceComparison {
    private String siteName;
    private String url;
    private String priceSelector; // Renamed to match your test method

    public PriceComparison(String siteName, String url, String priceSelector) {
        this.siteName = siteName;
        this.url = url;
        this.priceSelector = priceSelector;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getUrl() {
        return url;
    }

    // This is the method your test was missing
    public String getPriceSelector() {
        return priceSelector;
    }
}