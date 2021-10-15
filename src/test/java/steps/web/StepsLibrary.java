package steps.web;

import static com.qmetry.qaf.automation.step.CommonStep.click;
import static com.qmetry.qaf.automation.step.CommonStep.sendKeys;
import support.Util;

import java.io.File;
import java.io.FileFilter;

import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebElement;

import com.qmetry.qaf.automation.util.Validator;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import com.qmetry.qaf.automation.step.CommonStep;
import com.qmetry.qaf.automation.step.QAFTestStep;

public class StepsLibrary {

	public static String latestPdfContents = null;
	public static String latestFileDownloadedUrl = null;
	
	/**
	 * @param data
	 *                       : data which is being passed from bdd
	 */
	@QAFTestStep(description = "sample step with {0}")
	public static void sampleStep(String data) {
	}

	@QAFTestStep(description = "switch to conga composer frame")
	public static void switchToCongaComposerFrame() {

		QAFExtendedWebElement element = 
			new WebDriverTestBase().getDriver().findElementByXPath("(//div[contains(@class, 'windowViewMode-normal')]//iframe)[1]");
			
		if (element.isPresent()) {
			new WebDriverTestBase().getDriver().switchTo().frame(element);
			element = new WebDriverTestBase().getDriver().findElement(By.xpath("//iframe"));
			if (element.isPresent()) {
				new WebDriverTestBase().getDriver().switchTo().frame(element);
				element = new WebDriverTestBase().getDriver().findElement(By.xpath("//iframe[contains(@title, 'Conga Composer')]"));
				if (element.isPresent()) {
					new WebDriverTestBase().getDriver().switchTo().frame(element);
				}
			}
		}
	}

	@QAFTestStep(description = "load pdf called {0}")
    public static void loadPdfCalled(String pdfName) {
        try {
			latestPdfContents = null;
			System.out.println("loadPdfCalled(): Loading PDF: " + Util.DOWNLOADS_FOLDER + "/" + pdfName);
            File file = new File(Util.DOWNLOADS_FOLDER + "/" + pdfName);
            try (PDDocument document = Loader.loadPDF(file)) {
				latestPdfContents = new PDFTextStripper().getText(document);
				System.out.println("PRINTING PDF DOCUMENT");
				System.out.println("= = = = = = =");
				System.out.println("= = = = = = =");
				System.out.println("= = = = = = =");
				if (latestPdfContents != null) {
					String[] lines = latestPdfContents.split("\n");
					for (String line : lines) {
						System.out.println(line);
					}
				}
				System.out.println("= = = = = = =");
				System.out.println("= = = = = = =");
				System.out.println("= = = = = = =");
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	@QAFTestStep(description = "load latest pdf in downloads directory")
    public static void loadLatestPdf() {
        try {
			System.out.println("Load latest pdf...");
			File dir = new File(Util.DOWNLOADS_FOLDER);
			File latest = null;
			if (dir.isDirectory()) {
				System.out.println("Is DIR OK");
				File[] dirFiles = dir.listFiles((FileFilter)org.apache.commons.io.filefilter.FileFilterUtils.fileFileFilter());
				if (dirFiles != null && dirFiles.length > 0) {
					Arrays.sort(dirFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
					latest = dirFiles[0];
				}
			} else {
				System.out.println("Is DIR NOT OK");
			}

			if (latest != null) {
				System.out.println("Latest found: " + latest.getName());
				loadPdfCalled(latest.getName());
			} else {
				System.out.println("Latest not found");
			}
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	@QAFTestStep(description = "assert text {0} appears in the pdf")
    public static void doSomethingWithReportData(String text) {
        boolean found = false;
        try {
            if (latestPdfContents != null) {
                String[] lines = latestPdfContents.split("\n");
                for (String line : lines) {
                    if (line.contains(text)) {
                        found = true;
                        break;
                    }
                }
            }

            Validator.assertTrue(found,
                "Could not find the following text in the PDF: " + text,
                "Data found successfully in PDF:" + text);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
	}

	@QAFTestStep(description = "get latest download url from chrome downloads")
	public static void getLatestDownloadUrlFromChromeDownloads() throws Exception{

		latestFileDownloadedUrl = null;

		QAFExtendedWebElement downloadsManager = new WebDriverTestBase().getDriver().findElementByXPath("//downloads-manager");
		downloadsManager.waitForPresent(20000);

		StringBuilder script = new StringBuilder();
		script.append("var elem = document.createElement('input');");
		script.append("elem.id='input-url';");
		script.append("elem.value = document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList').items.filter(e => e.state === 'COMPLETE')[0].url;");
		script.append("document.body.insertAdjacentElement('beforeend', elem);");
		script.append("console.log(elem.value);");
		new WebDriverTestBase().getDriver().executeScript(script.toString());

		QAFExtendedWebElement input = new WebDriverTestBase().getDriver().findElementByXPath("//input[@id='input-url']");
		if (input.isPresent()) {
			latestFileDownloadedUrl = input.getAttribute("value");
		}

		Validator.assertTrue(latestFileDownloadedUrl != null,
                "Could not obtain the latest file downloaded URL",
                "Latest file downloaded URL obtained successfully");
	}

	@QAFTestStep(description = "download file locally")
	public static void downloadFileLocally() throws Exception{

		if (latestFileDownloadedUrl == null) {
			System.out.println("Cannot download. File URL is null!");
		}

		try {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd_HHmmss");
			String pdfName = sdf.format(new java.util.Date()) + ".pdf";
			System.out.println("Downloading file...");
			FileUtils.copyURLToFile(new URL(latestFileDownloadedUrl), new java.io.File(Util.DOWNLOADS_FOLDER + "/" + pdfName));
			System.out.println("File downloaded!");
			System.out.println("File path: " + Util.DOWNLOADS_FOLDER + "/" + pdfName);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	/*@QAFTestStep(description = "click on {loc}")
	public static void click(String loc) {
		System.out.println("CLICKING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		CommonStep.click(loc);
	}*/
}
