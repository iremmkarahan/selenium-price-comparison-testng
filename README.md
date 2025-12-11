
📊 Price Comparison Automation

(Java + Playwright + TestNG + @Factory)

This project automates price comparison for the same product across multiple e-commerce websites using Playwright for Java and TestNG.
It dynamically generates one test per website, extracts prices, and produces a summary with:
	•	💰 Cheapest price
	•	💵 Most expensive price
	•	📈 Average price
	•	📝 Price list per website

Playwright’s built-in auto-wait system makes the automation highly stable and faster than Selenium.

⸻

⚙️ How the Framework Works

🧩 1. Test Generation (@Factory)

PriceCheckFactory creates one test instance per website.
Each instance contains:
	•	Website name
	•	Product URL
	•	Locator strategy (text=TL — ideal for Turkish e-commerce)

⸻

🕹️ 2. Playwright Price Extraction

Each PriceCheckTest:
	1.	Launches a Playwright browser
	2.	Navigates to the product page
	3.	Locates the first element containing “TL”
	4.	Cleans and parses the price into a number
	5.	Saves it into PriceReport

Playwright auto-waits for elements, eliminating timing issues.

⸻

📊 3. Final Summary Report

After all tests finish, FinalReportTest prints a clean summary:
	•	Full list of collected prices
	•	Minimum price
	•	Maximum price
	•	Average price

⸻

▶️ Running the Tests

To run the entire suite:

mvn test

⸻

🛠️ Tech Stack

Component	Technology
Language	Java 23
Automation	Playwright for Java
Test Runner	TestNG
Build Tool	Maven
Pattern	TestNG @Factory (dynamic test generation)


⸻

