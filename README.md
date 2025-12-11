
📊 Automated Price Comparison Using Selenium & TestNG

This project automates the comparison of a single product’s price across three different e-commerce websites.
Using Selenium WebDriver, TestNG, and the @Factory annotation, the framework dynamically generates test instances—one per website—allowing scalable and maintainable price-checking automation.

After all tests run, a consolidated summary is produced showing:
	•	Cheapest price
	•	Most expensive price
	•	Average price
	•	Detailed list of all collected prices

⸻
 Project Overview

The framework is organized into three core components:

1. Test Generation (@Factory)

PriceCheckFactory creates one PriceCheckTest instance per website, each containing:
	•	Website name
	•	Product URL
	•	Locator for the price element

This makes the test suite flexible and easy to extend.

2. Automated Price Extraction

Each PriceCheckTest:
	•	Launches Selenium WebDriver
	•	Navigates to the product page
	•	Locates and parses the price
	•	Stores the result in PriceReport

3. Final Summary Report

FinalReportTest runs after the suite completes and prints a clean, aggregated comparison.

⸻
Running the Tests

Execute the full price comparison suite using Maven:

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
	•	The project is designed for easy expansion—add more websites by simply creating additional configurations.
	•	Price parsing includes basic cleanup but may need adjustment depending on currency format.
	•	Locators should be validated using browser DevTools.

⸻
