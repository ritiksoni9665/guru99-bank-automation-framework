# guru99- bank automation-framework

A **Selenium WebDriver + TestNG** based test automation framework built for the [Guru99 Bank](https://www.demo.guru99.com/v4/) demo web application. The framework follows the **Page Object Model (POM)** design pattern and supports **data-driven testing** using Apache POI to read test data from Excel files. Test execution reports are generated using **ExtentReports**, with structured logging via **Log4j2**. Screenshots are automatically captured on test failures to aid debugging.

## Modules Covered
- Login
- Add Customer
- Open New Account
- Deposit
- Withdrawal
- Fund Transfer
- Balance Enquiry / Transaction

## Tech Stack
| Category | Tools/Libraries |
|---|---|
| Language | Java |
| Automation Tool | Selenium WebDriver 4.34.0 |
| Test Framework | TestNG 7.11.0 |
| Build Tool | Maven |
| Data-Driven Testing | Apache POI (Excel) 5.4.1 |
| Reporting | ExtentReports 5.1.2 |
| Logging | Log4j2 2.25.1 |
| Driver Management | WebDriverManager 6.2.0 |

## Project Structure
```
Guru99BankFramework/
│
├── src/test/java/
│   ├── base/
│   │   └── BaseTest.java              # Common setup/teardown for all tests
│   │
│   ├── pages/                         # Page Object classes
│   │   ├── LoginPage.java
│   │   ├── AddCustomerPage.java
│   │   ├── NewAccountPage.java
│   │   ├── DepositPage.java
│   │   ├── WithdrawalPage.java
│   │   ├── FundTransferPage.java
│   │   └── BalanceEnquiryPage.java
│   │
│   ├── testcases/                     # Test classes (TestNG)
│   │   ├── LoginTest.java
│   │   ├── AddCustomerTest.java
│   │   ├── NewAccountTest.java
│   │   ├── FundTransferTest.java
│   │   └── TransactionTest.java
│   │
│   ├── utilities/                     # Reusable helper/utility classes
│   │   ├── ConfigReader.java          # Reads config.properties
│   │   ├── ExcelUtils.java            # Excel read/write (Apache POI)
│   │   ├── ScreenshotUtil.java        # Captures screenshots on failure
│   │   ├── TestLogger.java            # Log4j2 based logging wrapper
│   │   └── WaitUtils.java             # Explicit wait helpers
│   │
│   ├── reports/                       # ExtentReports setup
│   │   ├── ExtentManager.java
│   │   └── ExtentListener.java
│   │
│   ├── testdata/                      # Excel test data files
│   │   ├── LoginData.xlsx
│   │   ├── CustomerData.xlsx
│   │   ├── NegativeCustomerData.xlsx
│   │   ├── AccountData.xlsx
│   │   ├── DepositData.xlsx
│   │   ├── WithdrawalData.xlsx
│   │   └── TransferData.xlsx
│   │
│   └── resources/
│       ├── config.properties          # Environment/browser config
│       └── log4j2.xml                 # Logging configuration
│
├── screenshots/                       # Auto-captured failure screenshots
├── logs/                              # Execution logs
├── test-output/                       # TestNG & ExtentReports output
├── testng.xml                         # TestNG suite configuration
├── pom.xml                            # Maven dependencies & build config
└── README.md
```

## How to Run

### Prerequisites
- Java JDK 11+
- Maven installed
- Eclipse / IntelliJ IDEA (optional)
- Chrome/Firefox browser installed

### Steps
```bash
# Clone the repository
git clone https://github.com/<your-username>/guru99-bank-automation-framework.git
cd guru99-bank-automation-framework

# Run tests via Maven
mvn clean test

# Or run via TestNG suite
mvn test -DsuiteXmlFile=testng.xml
```

## Reports
After execution, reports are generated at:
- `test-output/ExtentReport.html` — detailed HTML report
- `test-output/emailable-report.html` — TestNG summary report

## Key Highlights
- Clean **Page Object Model** structure for maintainability
- **Data-driven** test execution using Excel (Apache POI)
- Centralized **config.properties** for environment/browser control
- Automatic **screenshot capture** on test failure
- Rich **ExtentReports** with pass/fail status and logs
- Structured **Log4j2** logging for debugging

## Author
Ritik Soni — QA Automation Engineer
