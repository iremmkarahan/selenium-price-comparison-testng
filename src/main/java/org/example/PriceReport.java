package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriceReport {

    // Store prices for math calculations
    private static List<Double> prices = Collections.synchronizedList(new ArrayList<>());

    // Store HTML rows directly
    private static List<String> tableRows = Collections.synchronizedList(new ArrayList<>());

    public static void addEntry(String siteName, double price) {
        if (price > 0) {
            prices.add(price);
            // Add a successful row to the table
            tableRows.add("<tr><td><b>" + siteName + "</b></td><td style='color:#2e7d32; font-weight:bold;'>" + String.format("%.2f", price) + " TL</td></tr>");
        } else {
            // Add a failed row to the table
            tableRows.add("<tr><td>" + siteName + "</td><td style='color:red;'>Price not found</td></tr>");
        }
    }

    public static void generateReport() {
        System.out.println("Generating Final HTML Report...");

        // Start building HTML
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html><head><meta charset='UTF-8'>"); // CRITICAL: Fixes the weird characters
        html.append("<title>Apple Watch Price Report</title>");
        html.append("<style>");
        html.append("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; padding: 40px; background-color: #f9f9f9; }");
        html.append("h2 { color: #333; text-align: center; }");
        html.append(".container { max-width: 800px; margin: 0 auto; background: white; padding: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1); border-radius: 8px; }");
        html.append("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        html.append("th, td { border-bottom: 1px solid #ddd; padding: 15px; text-align: left; }");
        html.append("th { background-color: #f2f2f2; color: #555; }");
        html.append(".stats { margin-top: 30px; padding: 20px; background-color: #e3f2fd; border-radius: 5px; }");
        html.append(".stats p { margin: 10px 0; font-size: 16px; }");
        html.append("</style>");
        html.append("</head><body>");

        html.append("<div class='container'>");
        html.append("<h2>🛒 Apple Watch Price Comparison</h2>");

        // TABLE START
        html.append("<table>");
        html.append("<thead><tr><th>Website</th><th>Price</th></tr></thead>");
        html.append("<tbody>");

        for (String row : tableRows) {
            html.append(row);
        }

        html.append("</tbody></table>");

        // STATISTICS SECTION
        if (!prices.isEmpty()) {
            double min = Collections.min(prices);
            double max = Collections.max(prices);
            double sum = 0;
            for (double p : prices) sum += p;
            double avg = sum / prices.size();

            html.append("<div class='stats'>");
            html.append("<h3>📊 Statistics</h3>");
            html.append("<p><strong>Cheapest Price:</strong> " + String.format("%.2f", min) + " TL</p>");
            html.append("<p><strong>Most Expensive:</strong> " + String.format("%.2f", max) + " TL</p>");
            html.append("<p><strong>Average Price:</strong> " + String.format("%.2f", avg) + " TL</p>");
            html.append("</div>");
        } else {
            html.append("<div class='stats' style='background-color:#ffebee'><p style='color:red'>⚠️ No prices were found.</p></div>");
        }

        html.append("</div></body></html>");

        // Write file with UTF-8 Encoding
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream("PriceReport.html"), StandardCharsets.UTF_8))) {
            writer.write(html.toString());
            System.out.println("✅ Report created successfully: PriceReport.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}