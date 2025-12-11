package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;

public class PriceReport {

    public static class PriceEntry {
        private final String siteName;
        private final String productUrl;
        private final double price;

        public PriceEntry(String siteName, String productUrl, double price) {
            this.siteName = siteName;
            this.productUrl = productUrl;
            this.price = price;
        }

        public String getSiteName() {
            return siteName;
        }

        public String getProductUrl() {
            return productUrl;
        }

        public double getPrice() {
            return price;
        }
    }

    private static final List<PriceEntry> entries = new ArrayList<>();

    public static synchronized void addEntry(String siteName, String productUrl, double price) {
        entries.add(new PriceEntry(siteName, productUrl, price));
    }

    public static synchronized List<PriceEntry> getEntries() {
        return new ArrayList<>(entries);
    }

    public static synchronized void clear() {
        entries.clear();
    }

    public static synchronized void printSummary() {
        if (entries.isEmpty()) {
            System.out.println("No price entries collected.");
            return;
        }

        System.out.println("===== PRICE COMPARISON REPORT =====");
        for (PriceEntry entry : entries) {
            System.out.printf("Site: %-15s | Price: %.2f | URL: %s%n",
                    entry.getSiteName(),
                    entry.getPrice(),
                    entry.getProductUrl());
        }

        // Cheapest
        PriceEntry min = entries.stream()
                .min(Comparator.comparingDouble(PriceEntry::getPrice))
                .orElse(null);

        // Most expensive
        PriceEntry max = entries.stream()
                .max(Comparator.comparingDouble(PriceEntry::getPrice))
                .orElse(null);

        // Average
        OptionalDouble avg = entries.stream()
                .mapToDouble(PriceEntry::getPrice)
                .average();

        System.out.println("------------------------------------");
        if (min != null) {
            System.out.printf("Cheapest     : %s (%.2f)%n", min.getSiteName(), min.getPrice());
        }
        if (max != null) {
            System.out.printf("Most expensive: %s (%.2f)%n", max.getSiteName(), max.getPrice());
        }
        if (avg.isPresent()) {
            System.out.printf("Average price: %.2f%n", avg.getAsDouble());
        }
        System.out.println("====================================");
    }
}