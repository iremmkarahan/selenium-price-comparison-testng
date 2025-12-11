
📊 Automated Price Comparison Using Selenium & TestNG

This project automates the comparison of a single product’s price across three different e-commerce websites.
Using Selenium WebDriver, TestNG, and the @Factory annotation, the framework generates one test instance per website for scalable and maintainable automation.

After the tests run, a summary is produced showing:
	•	Cheapest price
	•	Most expensive price
	•	Average price
	•	All collected prices

⸻

Project Overview

1. Test Generation (@Factory)

PriceCheckFactory creates a PriceCheckTest instance for each website, containing:
	•	Website name
	•	Product URL
	•	Price locator

This makes the suite easy to extend to more sites.

⸻

2. Automated Price Extraction

Each PriceCheckTest:
	•	Launches Selenium WebDriver
	•	Opens the product page
	•	Locates and extracts the displayed price
	•	Parses the numeric value
	•	Stores it in PriceReport

⸻

3. Final Summary Report

FinalReportTest runs after the entire suite completes and prints a clean, aggregated comparison.

⸻

▶️ How to Run

Use Maven to execute all tests:

mvn test


⸻

🛠️ Technologies Used
	•	Java
	•	Selenium WebDriver
	•	TestNG
	•	WebDriverManager
	•	Maven

⸻

📌 Notes
	•	Designed for easy expansion — add more sites by creating additional configurations.
	•	Price parsing may require small adjustments depending on website currency format.
	•	Locators should always be confirmed using browser DevTools.


