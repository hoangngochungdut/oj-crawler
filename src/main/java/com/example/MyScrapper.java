package com.example;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;

public class MyScrapper {

    public static void main(String[] args) {

        // ===== 1. SETUP DRIVER =====
        System.setProperty("webdriver.edge.driver",
                "C:\\Users\\user\\Desktop\\java\\oj-crawler\\edgedriver_win64\\msedgedriver.exe");

        EdgeOptions options = new EdgeOptions();

        // ✅ dùng profile đã login (QUAN TRỌNG)
        options.addArguments("user-data-dir=C:\\edge-profile");
        options.addArguments("profile-directory=Default");
        options.addArguments("--start-maximized");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

        WebDriver driver = new EdgeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // ===== 2. CONFIG =====
        String baseUrl = "https://codeforces.com/submissions/hoangngochung120/page/";
        int page = 1;

        try {
            while (true) {

                String url = baseUrl + page;
                System.out.println("==== PAGE " + page + " ====");
                driver.get(url);

                // đợi load bảng submissions
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("tr[data-submission-id]")
                ));

                List<WebElement> rows = driver.findElements(
                        By.cssSelector("tr[data-submission-id]")
                );

                if (rows.isEmpty()) {
                    System.out.println("Hết dữ liệu.");
                    break;
                }

                // ===== 3. LẤY LINK TRƯỚC =====
                List<String> links = new ArrayList<>();

                for (WebElement row : rows) {
                    try {
                        WebElement link = row.findElement(By.cssSelector("a.view-source"));
                        links.add(link.getAttribute("href"));
                    } catch (Exception ignored) {}
                }

                // ===== 4. VÀO TỪNG SUBMISSION =====
                for (String href : links) {
                    try {
                        driver.get(href);

                        WebElement code = wait.until(
                                ExpectedConditions.presenceOfElementLocated(
                                        By.id("program-source-text")
                                )
                        );

                        String source = code.getText();

                        System.out.println("Link: " + href);
                        System.out.println("Code:\n" + source);
                        System.out.println("-------------");

                    } catch (Exception e) {
                        System.out.println("Lỗi: " + e.getMessage());
                    }
                }

                page++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}