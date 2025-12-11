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

    private static List<Double> prices = Collections.synchronizedList(new ArrayList<>());
    private static List<String> logs = Collections.synchronizedList(new ArrayList<>());


    // This is the method your test was missing
    public static void addEntry(String siteName, double price) {
        prices.add(price);
        logs.add("<li><strong>" + siteName + "</strong>: " + String.format("%.2f", price) + " TL</li>");
    }

    public static void addPrice(String site, double price) {
        prices.add(price);
        logs.add("<tr><td style='color:green; font-weight:bold;'>✔ " + site + "</td><td>" + String.format("%.2f", price) + " TL</td></tr>");
    }

    public static void logError(String site, String error) {
        logs.add("<tr><td style='color:red; font-weight:bold;'>✘ " + site + "</td><td>" + error + "</td></tr>");
    }

    public static void generateReport() {
        System.out.println("Generating Report...");

        StringBuilder html = new StringBuilder();
        html.append("<html><head><meta charset='UTF-8'>"); // CRITICAL for Turkish characters
        html.append("<style>body{font-family: Arial, sans-serif; padding: 20px;} table{border-collapse: collapse; width: 80%;} td, th{border: 1px solid #ddd; padding: 10px;} th{background-color:#f4f4f4; text-align:left;}</style>");
        html.append("</head><body>");
        html.append("<h2>🛒 Apple Watch Series 11 - Price Report</h2>");
        html.append("<table><tr><th>Website</th><th>Price / Status</th></tr>");

        for (String log : logs) html.append(log);
        html.append("</table>");

        if (!prices.isEmpty()) {
            double min = Collections.min(prices);
            double max = Collections.max(prices);
            double sum = 0;
            for(double d : prices) sum += d;
            double avg = sum / prices.size();

            html.append("<h3>📊 Statistics</h3>");
            html.append("<p><strong>Cheapest:</strong> " + String.format("%.2f", min) + " TL</p>");
            html.append("<p><strong>Most Expensive:</strong> " + String.format("%.2f", max) + " TL</p>");
            html.append("<p><strong>Average:</strong> " + String.format("%.2f", avg) + " TL</p>");
        } else {
            html.append("<h3>⚠️ No prices found. Check your Internet or Selectors.</h3>");
        }
        html.append("</body></html>");

        // Force UTF-8 Encoding here
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream("PriceReport.html"), StandardCharsets.UTF_8))) {
            writer.write(html.toString());
            System.out.println("✅ Report created: PriceReport.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}