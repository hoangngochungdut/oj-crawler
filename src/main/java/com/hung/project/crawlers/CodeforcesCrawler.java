package com.hung.project.crawlers;

import java.time.Duration;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import org.openqa.selenium.support.ui.*;

import com.hung.project.models.Submission;
//import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;?
import java.io.File;
		
import com.hung.project.API.*;
public class CodeforcesCrawler {

    private WebDriver driver;
   

    public CodeforcesCrawler() {

        System.setProperty(
            "webdriver.edge.driver",
            "edgedriver_win64/msedgedriver.exe"
        );

        EdgeOptions options = new EdgeOptions();

        // 1. Lấy đường dẫn tuyệt đối đến thư mục 'edge-profile' ngay trong dự án
        // File("edge-profile") sẽ trỏ vào thư mục gốc của project của bạn
        File profileDir = new File("edge-profile");
        String profilePath = profileDir.getAbsolutePath();

        // 2. Truyền đường dẫn động này vào EdgeOptions
        options.addArguments("--user-data-dir=" + profilePath);

        options.addArguments(
            "--disable-blink-features=AutomationControlled"
        );

        driver = new EdgeDriver(options);
    }
    
    public void enter() {
    	driver.get("https://codeforces.com");

    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    	wait.until(d -> ((JavascriptExecutor) d)
    	        .executeScript("return document.readyState")
    	        .equals("complete"));
    	
    }

    public String crawlSourceCode(int contestId, long submissionId) {
        try {
            String url = "https://codeforces.com/contest/" + contestId
                    + "/submission/" + submissionId;

            driver.get(url);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // 1. đợi page load hoàn toàn
            wait.until(d -> ((JavascriptExecutor) d)
                    .executeScript("return document.readyState")
                    .equals("complete"));

            // 2. đợi element visible (quan trọng hơn presence)
            WebElement sourceElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("program-source-text")
                    )
            );

            return sourceElement.getText();

        } catch (Exception e) {
            e.printStackTrace();

//            System.out.println("Current URL: " + driver.getCurrentUrl());
//            System.out.println("Page title: " + driver.getTitle());

            return null;
        }
    }

    public List<SubmissionCrawlResult> crawlSubmissions(int userId, String username) {

        List<SubmissionCrawlResult> submissions = new ArrayList<>();

        try {
            String url = "https://codeforces.com/submissions/" + username;
            System.out.println("Crawling: " + url);

            driver.get(url);

            WebDriverWait wait = new WebDriverWait(
                    driver,
                    Duration.ofSeconds(15)
            );

            // đợi bảng submissions load
            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("table.status-frame-datatable")
                    )
            );

            // lấy tất cả row trong bảng submissions
            List<WebElement> rows = driver.findElements(
                    By.cssSelector("table.status-frame-datatable tr")
            );

            for (WebElement row : rows) {

                try {
                    // chỉ lấy ACCEPTED submissions
                    WebElement verdict = row.findElement(
                            By.cssSelector("span.verdict-accepted")
                    );

                    // nếu không có AC thì skip (exception)

                    WebElement link = row.findElement(
                            By.cssSelector("a.view-source")
                    );

                    String href = link.getAttribute("href");

                    // https://codeforces.com/contest/1234/submission/567890
                    String[] parts = href.split("/");

                    if (parts.length < 7) continue;

                    int contestId = Integer.parseInt(parts[4]);
                    long submissionId = Long.parseLong(parts[6]);

                    submissions.add(
                            new SubmissionCrawlResult(
                                    userId,
                                    contestId,
                                    submissionId
                            )
                    );

                } catch (Exception ignored) {
                    // không phải AC hoặc row không hợp lệ → bỏ qua
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return submissions;
    }

    public void close() {

        if (driver != null) {

            driver.quit();
        }
    }
}