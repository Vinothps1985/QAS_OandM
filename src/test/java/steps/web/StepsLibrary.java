package steps.web;

import static com.qmetry.qaf.automation.step.CommonStep.click;
import org.openqa.selenium.Cookie;
import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;
import com.qmetry.qaf.automation.util.Reporter;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;
import static com.qmetry.qaf.automation.step.CommonStep.sendKeys;
import support.Util;
import support.EmailHelper;
import support.EmailObject;

import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.io.FileFilter;

import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.util.ExpectedCondition;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qmetry.qaf.automation.util.Validator;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.mail.Message;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.qmetry.qaf.automation.core.TestBaseProvider;
import com.qmetry.qaf.automation.keys.ApplicationProperties;
import com.qmetry.qaf.automation.step.CommonStep;
import com.qmetry.qaf.automation.step.QAFTestStep;

/**
 * Steps Library for web executions.
 * It's important to note that this steps will only be available for web executions.
 * They will not be available either for android executions, or hybrid executions.
 * That's because hybrid executions are actually ran under the 'android' execution process
 * 
 * If you want steps to be visible in web and Android, code them in the StepsLibrary
 * class inside the 'common' package.
 */
public class StepsLibrary {

	private static Log logger = LogFactory.getLog(StepsLibrary.class);

	//PDF Testing needs several pieces to work correctly.
	//This variables serve as helpers to allow PDF testing to work
	//in functions that appear later in this class
	protected static List<String> latestPdfPageContents = null;
	protected static String latestPdfFilePath = null;
	public static String latestFileDownloadedUrl = null;

	/**
	 * When using conga for email generation, the conga process is actually
	 * generated inside a deep tree of iframes. So this function attempts to get
	 * into the specific conga iframe to be able to start testing conga components
	 * (e.g. the email components)
	 */
	@QAFTestStep(description = "switch to conga composer frame")
	public static void switchToCongaComposerFrame() {

		int maxAttempts = 5;
		for (int attempt = 0; attempt < maxAttempts; attempt++) {
			try {
				new WebDriverTestBase().getDriver().switchTo().defaultContent();

				QAFExtendedWebElement element = 
					new WebDriverTestBase().getDriver()
					.findElementByXPath("(//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//iframe)[1]");
					
				element.waitForPresent();
				new WebDriverTestBase().getDriver().switchTo().frame(element);
				element = new WebDriverTestBase().getDriver().findElement(By.xpath("//iframe"));
				element.waitForPresent();
				new WebDriverTestBase().getDriver().switchTo().frame(element);

				element = new WebDriverTestBase().getDriver().findElement(By.xpath("//iframe[contains(@title, 'Conga Composer')]"));
				element.waitForPresent();
				new WebDriverTestBase().getDriver().switchTo().frame(element);
			} catch (Exception x) {
				try {Thread.sleep(3000);} catch (Exception ex) {}
			}
		}
	}

	/**
	 * This function is executed after a PDF exists in the file-system, specifically
	 * in the DOWNLOADS_FOLDER folder (configurable in application.properties)
	 * 
	 * Once loaded, it loads page after page and saves the text of each page in the
	 * 'latestPdfPageContents' variable as a list. So if the PDF has 10 pages, the
	 * list will have 10 strings, each of them representing the text in each page.
	 * 
	 * This process uses Apache PDFBox for PDF reading.
	 * 
	 * This process also prints each page on the console (via System.out.printlns)
	 * so the tester can check how the text is structured to make any assertions
	 * 
	 * @param pdfName The name of the pdf to look for and read
	 */
	@QAFTestStep(description = "load pdf called {0}")
    public static void loadPdfCalled(String pdfName) {
        try {
			latestPdfPageContents = new ArrayList<String>();
			System.out.println("loadPdfCalled(): Loading PDF: " + Util.DOWNLOADS_FOLDER + "/" + pdfName);
			File file = new File(Util.DOWNLOADS_FOLDER + "/" + pdfName);

			PDFTextStripper pts = new PDFTextStripper();

            try (PDDocument document = Loader.loadPDF(file)) {
				latestPdfFilePath = file.getAbsolutePath();
				pts.setSortByPosition(true);
				for (int p=0; p < document.getNumberOfPages(); p++) {
					pts = new PDFTextStripper();
					pts.setStartPage(p+1);
					pts.setEndPage(p+1);
					pts.setSortByPosition(true);
					latestPdfPageContents.add(pts.getText(document));
				}

				//This section is only relevant to print on console the
				//PDF contents, as a 'utility' so the tester can check how
				//the text is structured to make any assertions
				System.out.println("PRINTING PDF DOCUMENT");
				System.out.println("= = = = = = =");
				System.out.println("= = = = = = =");
				System.out.println("= = = = = = =");
				if (latestPdfPageContents != null && latestPdfPageContents.size() > 0) {
					for (int p = 0; p < latestPdfPageContents.size(); p++) {
						String pageContent = latestPdfPageContents.get(p);
						System.out.println(" = = Page " + (p+1));
						String[] lines = pageContent.split("\n");
						for (String line : lines) {
							System.out.println(line);
						}
						System.out.println(" = = End of page " + (p+1));
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

	/**
	 * This function checks the DOWNLOADS_FOLDER directory (configurable in application.properties)
	 * and searches for all PDF files, and takes the latest one (most recent). Then it loads
	 * the information from that PDF by calling the 'loadPdfCalled' method.
	 */
	@QAFTestStep(description = "load latest pdf in downloads directory")
    public static void loadLatestPdf() {
		boolean success = false;
		try {
			int maxAttempts = 5;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					System.out.println("Load latest pdf...");
					File dir = new File(Util.DOWNLOADS_FOLDER);
					System.out.println("Looking in dir: " + Util.DOWNLOADS_FOLDER);
					File latest = null;
					if (dir.isDirectory()) {
						System.out.println("Is DIR OK");
						File[] dirFiles = dir.listFiles((FileFilter)org.apache.commons.io.filefilter.FileFilterUtils.fileFileFilter());
						if (dirFiles != null && dirFiles.length > 0) {
							Arrays.sort(dirFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
							for (File dirFile : dirFiles) {
								if (dirFile.getName().toLowerCase().endsWith(".pdf")) {
									latest = dirFile;
									break;
								}
							}
						}
					} else {
						System.out.println("Is DIR NOT OK");
					}

					if (latest != null) {
						System.out.println("Latest found: " + latest.getName());
						loadPdfCalled(latest.getName());
						success = true;
						break;
					} else {
						System.out.println("Latest not found");
					}
				} catch (Exception x) {}
				Thread.sleep(2000);
			}
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	/**
	 * Ensures a certain text appears in the latest PDF loaded.
	 * This assertion takes a screenshot of the PDF page where the text appears
	 * @param text The text to ensure exists in the pdf as-is
	 */
	@QAFTestStep(description = "assert text {0} appears in the pdf with screenshot")
    public static void assertTextAppearsInPdfWithScreenshot(String text) {
		assertTextAppearsInPdf(text, true);
	}

	/**
	 * Ensures a certain text appears in the latest PDF loaded.
	 * This assertion does not take any screenshots
	 * @param text The text to ensure exists in the pdf as-is
	 */
	@QAFTestStep(description = "assert text {0} appears in the pdf")
    public static void assertTextAppearsInPdfWithoutScreenshot(String text) {
		assertTextAppearsInPdf(text, false);
	}
	
	/**
	 * Ensures a certain text appears in the latest PDF loaded
	 * 
	 * @param text The text to ensure exists in the pdf as-is
	 * @param takeScreenshot True if the process should take a screenshot.
	 */
    public static void assertTextAppearsInPdf(String text, boolean takeScreenshot) {
        boolean found = false;
        try {
			if (latestPdfPageContents != null && latestPdfPageContents.size() > 0) {
				for (int p=0; p < latestPdfPageContents.size(); p++) {
					String pageContent = latestPdfPageContents.get(p);
					String[] lines = pageContent.split("\n");
					for (String line : lines) {
						System.out.println("    " + line);
						if (line.contains(text)) {
							found = true;
							if (takeScreenshot) {
								takePDFScreenshot(latestPdfFilePath, p);
							}
							break;
						}
					}
					if (found) {
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

	/**
	 * Take a screenshot of the latest PDF loaded
	 * 
	 * @param pdfFileName The name of the PDF file
	 * @param pageIndex 0-based index of the page to take a screenshot of
	 */
	private static void takePDFScreenshot(String pdfFileName, int pageIndex) throws Exception {
		File pdf = new File(pdfFileName);

		try (PDDocument document = Loader.loadPDF(pdf)) {
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			//BufferedImage bim = pdfRenderer.renderImageWithDPI(pageFound, 300, ImageType.RGB);
			BufferedImage bim = pdfRenderer.renderImageWithDPI(pageIndex, 100, ImageType.RGB);

			//We cannot set a screenshot from an existing image currently (QAF)
			//So we take a regular screenshot, and then replace the file with this one
			//with the same name

			//Take the screenshot
			steps.common.StepsLibrary.takeAScreenshot();
			//Get the screenshots dir of this test execution
			File screenshotDir = new File(ApplicationProperties.SCREENSHOT_DIR.getStringVal("./img"));

			//Find the latest screenshot in the directory (the one just taken)
			File[] dirFiles = screenshotDir.listFiles((FileFilter)org.apache.commons.io.filefilter.FileFilterUtils.fileFileFilter());
			File latest = null;
			if (dirFiles != null && dirFiles.length > 0) {
				Arrays.sort(dirFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
				latest = dirFiles[0];
			}
			if (latest != null) {
				//We get its name, delete the file, and replace it with our new PDF image
				String fileName = latest.getAbsolutePath();
				latest.delete();
				File outputfile = new File(fileName);
				ImageIO.write(bim, "jpg", outputfile);
			} else {
				logger.error("Could not get the latest file in the screenshots directory" + screenshotDir.getAbsolutePath());
			}
		}
	}

	/**
	 * Ensures a certain text does not appear in the latest PDF loaded.
	 * This assertion takes a screenshot of the PDF page where the text appears
	 * @param text The text to ensure doesn't exist in the pdf as-is
	 */
	@QAFTestStep(description = "assert text {0} does not appear in the pdf with screenshot")
    public static void assertTextDoesntAppearInPdfWithScreenshot(String text) {
		assertTextDoesNotAppearInPdf(text, true);
	}

	/**
	 * Ensures a certain text does not appear in the latest PDF loaded.
	 * This assertion does not take any screenshot
	 * @param text The text to ensure doesn't exist in the pdf as-is
	 */
	@QAFTestStep(description = "assert text {0} does not appear in the pdf")
    public static void assertTextDoesntAppearInPdfWithoutScreenshot(String text) {
		assertTextDoesNotAppearInPdf(text, false);
	}

	/**
	 * Ensures a certain text does not in the latest PDF loaded
	 * 
	 * @param text The text to ensure doesn't exist in the pdf as-is
	 * @param takeScreenshot True if the process should take a screenshot.
	 */
    public static void assertTextDoesNotAppearInPdf(String text, boolean takeScreenshot) {
        boolean found = false;
        try {
			if (latestPdfPageContents != null && latestPdfPageContents.size() > 0) {
				for (int p=0; p < latestPdfPageContents.size(); p++) {
					String pageContent = latestPdfPageContents.get(p);
					String[] lines = pageContent.split("\n");
					for (String line : lines) {
						System.out.println("    " + line);
						if (line.contains(text)) {
							found = true;
							if (takeScreenshot) {
								takePDFScreenshot(latestPdfFilePath, p);
							}
							break;
						}
					}
					if (found) {
						break;
					}
				}
			}

            Validator.assertTrue(!found,
                "The text " + text + " was found in the PDF, when it should not exist",
                "The text " + text + " was not found in the PDF, which is correct");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
	}

	/**
	 * A very specific function to assert a PDF contains line items of a certain asset type, with certain comments
	 * and alternating pass/fail checklist
	 * 
	 * @param assetType The asset type that should be reviewed in case the PDF has several
	 * @param comment The comment that should appear after the passing/failing checklist
	 */
	@QAFTestStep(description = "assert pdf contains a line item of type {assetType} with comment {comment} alternating pass and fail")
    public static void assertPdfLineItemATCommentPassFail(String assetType, String comment) {
		boolean checklistValidated = false;
		boolean commentsValidated = false;

		try {

			List<Integer> pageIndexesWithScreenshotAlready = new ArrayList<Integer>();

			if (latestPdfPageContents != null && latestPdfPageContents.size() > 0) {

				boolean inLineItemsSection = false;
				boolean inLineItem = false;
				boolean afterChecklistTableHeader = false;
				boolean inChecklistSection = false;
				boolean inComments = false;
				//String lastLine = null;
				int responses = 0;
				String fullComments = "";
				//int lineNum=0;
				//boolean afterIndex = false;
				String fullChecklist = "";

				//Start at page 2 to skip the intro and index
				for (int p=2; p < latestPdfPageContents.size(); p++) {
					String pageContent = latestPdfPageContents.get(p);
					String[] lines = pageContent.split("\n");
					for (String line : lines) {
						//System.out.println("    " + line);

						if (!inLineItemsSection) {
							if (line.contains("Corrective Maintenance Inspections")) {
								System.out.println("Corrective maintenance inspections : " + line);
								inLineItemsSection = true;
							}
							continue;
						}

						//In the line items section
						if (!inLineItem) {
							if (line.contains("Corrective PM " + assetType)) {
								System.out.println("Corrective PM" + assetType + ": " + line);
								inLineItem = true;
							}
							continue;
						}

						//In the specific line item
						if (!afterChecklistTableHeader) {
							if (line.contains("Inspection Result")) {
								System.out.println("Inspection Result" + line);
								afterChecklistTableHeader = true;
							}
							continue;
						}

						if (!inChecklistSection) {
							if (line.startsWith("1 ")) {
								System.out.println("1 " + line);
								inChecklistSection = true;
							} else {
								continue;
							}
						}

						//Checklist items begin with a number, encompass 1-2 lines, and have the answer (Fail or Pass).
						//The last one ends when we reach the comment section
						if ((Character.isDigit(line.toCharArray()[0]) || line.startsWith("Comments")) && !inComments) {
							System.out.println("Digit or comments: " + line);
							if (fullChecklist.length() > 0) {
								//Check if the last line has the response we expected at the end
								String expectedResponse = responses%2 == 0 ? "Fail" : "Pass"; //Even = Fail. Odd = Pass
								boolean correct = fullChecklist.trim().contains(expectedResponse);
								Validator.assertTrue(correct,
								"Checklist item " + (responses+1) + " does not have the expected answer: Expected: " + expectedResponse + ", Actual text processed: " + fullChecklist.trim(),
								"Checklist item " + (responses+1) + " has the expected answer: " + expectedResponse);

								//Take a screenshot if necessary
								if (!pageIndexesWithScreenshotAlready.contains(p)) {
									takePDFScreenshot(latestPdfFilePath, p);
									pageIndexesWithScreenshotAlready.add(p);
								}

								responses++;
								fullChecklist = "";

								checklistValidated = true;
							}
						}
						
						//Save the last line since it may contain the latest answer
						//lastLine = line;
						fullChecklist += line;

						//Comments may have several lines
						if (!inComments) {
							if (line.startsWith("Comments")) {
								System.out.println("Now in comments: " + line);
								inComments = true;
								fullComments += line.replace("Comments: ", "").trim();
							}
						} else {
							if (line.startsWith("Photos:")) {
								System.out.println("Now in photos: " + line);
								//Comments have been fully obtained. Assert them
								
								//Test removing all spaces...
								String fullCommentsCheck = fullComments.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");
								String commentCheck = comment.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");

								Validator.assertTrue(fullCommentsCheck.equalsIgnoreCase(commentCheck),
									"Comments are different in the PDF for the Line Item. Expected: " + comment + ". Found: " + fullComments,
									"Comments appear correctly in the PDF for the Line Item " + assetType);

								//Take a screenshot if necessary
								if (!pageIndexesWithScreenshotAlready.contains(p)) {
									takePDFScreenshot(latestPdfFilePath, p);
									pageIndexesWithScreenshotAlready.add(p);
								}

								//Now we can exit
								commentsValidated = true;
								break;
							} else {
								//More comments, continue adding...
								System.out.println("Adding mor comments:  " + line);
								fullComments += line.trim();
							}
						}

						if (commentsValidated && checklistValidated) {
							break;
						}
					}
				}
			}

		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}

		Validator.assertTrue(commentsValidated && checklistValidated,
			"Comments and checklist were not validated correctly",
			"Comments and checklist validated correctly");
	}

	/**
	 * To ensure PDF testing works locally and remotely (In Zalenium), the actual process of downloading
	 * PDF files needs to download the file, and then go to the 'chrome downloads' section in chrome
	 * and take the URL of the latest downloaded file, so it can then be downloaded by the calling
	 * process (e.g. the Jenkins server or local computer), so it can then be opened and asserted.
	 * 
	 * @throws Exception in case something goes wrong
	 */
	@QAFTestStep(description = "get latest download url from chrome downloads")
	public static void getLatestDownloadUrlFromChromeDownloads() throws Exception {

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
			logger.info("latestFileDownloadedUrl: " + latestFileDownloadedUrl);
		}

		Validator.assertTrue(latestFileDownloadedUrl != null,
                "Could not obtain the latest file downloaded URL",
                "Latest file downloaded URL obtained successfully");
	}

	/**
	 * Download a file saved in the 'latestFileDownloadedUrl' variable locally.
	 * Locally means 'where the test is being executed'. For example if executing locally, it means
	 * the local computer. If it's being executed from jenkins and running on Zalenium, then locally
	 * means the Jenkins server
	 * 
	 * @throws Exception if anything goes wrong
	 */
	@QAFTestStep(description = "download file locally")
	public static void downloadFileLocally() throws Exception {

		if (latestFileDownloadedUrl == null) {
			System.out.println("Cannot download. File URL is null!");
		}

		try {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd_HHmmss");
			String pdfName = sdf.format(new java.util.Date()) + ".pdf";
			System.out.println("Downloading file from URL: " + latestFileDownloadedUrl);
			FileUtils.copyURLToFile(new URL(latestFileDownloadedUrl), new java.io.File(Util.DOWNLOADS_FOLDER + "/" + pdfName));
			System.out.println("File downloaded!");
			System.out.println("File path: " + Util.DOWNLOADS_FOLDER + "/" + pdfName);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	/**
	 * Downloads a file using specific cookies sent as a variable.
	 * This works, for example, when printing a Purchase Order, since it works differently
	 * than other PDFs in Salesforce.
	 * 
	 * Can be sen in action in CreateConReqGroupAndPOUsingOverhead.feature
	 * 
	 * The cookies are taken before, because the cookies in each frame are different so first
	 * we save the correct cookies, and then they're sent here to be able to download the PDF
	 * 
	 * @param cookies The cookies to send to the download process
	 * @throws Exception
	 */
	@QAFTestStep(description = "download file locally with cookies {cookies}")
	public static void downloadFileLocallyWithCookies(String cookies) throws Exception {

		if (latestFileDownloadedUrl == null) {
			System.out.println("Cannot download. File URL is null!");
		}

		try {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd_HHmmss");
			String pdfName = sdf.format(new java.util.Date()) + ".pdf";
			System.out.println("Downloading file from URL: " + latestFileDownloadedUrl);

			HttpURLConnection connection = (HttpURLConnection) new URL(latestFileDownloadedUrl).openConnection();
			connection.setRequestMethod("GET");
			connection.addRequestProperty("Cookie", cookies);

			try (InputStream in = connection.getInputStream()) {;
				File downloadedFile = new File(Util.DOWNLOADS_FOLDER + "/" + pdfName);
				try (FileOutputStream out = new FileOutputStream(downloadedFile)) {
					byte[] buffer = new byte[1024];
					int len = in.read(buffer);
					while (len != -1) {
						out.write(buffer, 0, len);
						len = in.read(buffer);
						if (Thread.interrupted()) {
							throw new InterruptedException();
						}
					}
				}
			}

			System.out.println("File downloaded!");
			System.out.println("File path: " + Util.DOWNLOADS_FOLDER + "/" + pdfName);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	/**
	 * Store the current cookies from the current frame inside a variable
	 * 
	 * @param varName The name of the variable to save the cookies to
	 * 
	 * @throws Exception In case anything goes wrong
	 */
	@QAFTestStep(description = "store cookies into {varName}")
	public static void storeCookiesInto(String varName) throws Exception{

		try {
			String cookies = ((JavascriptExecutor) new WebDriverTestBase().getDriver())
						.executeScript("return document.cookie;").toString();
			if (cookies != null && cookies.trim().length() > 0) {
				System.out.println("We got cookies!");
				System.out.println(cookies);
				CommonStep.store(cookies, varName);
			} else {
				System.out.println("No cookies :(");
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	/**
	 * A specific test made to get the URL of a PDF generated in-page, specifically (at least)
	 * when creating a Purchase Order O&M PDF. To get the URL to download the file locally, we need
	 * to execute a very specific process to get that URL so it can be later downloaded, and asserted on
	 * 
	 * @param varName The name of the variable to save the PDF URL to download later
	 */
	@QAFTestStep(description = "get embedded PDF URL into {varName}")
	public static void getEmbeddedPDFURLInto(String varName) throws Exception{
		boolean success = false;
		try {
			int maxAttempts = 5;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					String info = ((JavascriptExecutor) new WebDriverTestBase().getDriver())
									.executeScript("return (typeof pageData !== 'undefined' ? pageData : loadTimeData.data_).summary.failedUrl;").toString();
					if (info != null && info.trim().length() > 0) {
						info = java.net.URLDecoder.decode(info, StandardCharsets.UTF_8.name());
						String[] lines = info.split("\n");
						for (String line : lines) {
							if (line.contains("pdfUrl")) {
								logger.info("Found pdfUrl");
								line = line.substring(line.indexOf("pdfUrl") + 9);
								line = line.substring(0, line.indexOf("};"));
								latestFileDownloadedUrl = line;

								CommonStep.store(latestFileDownloadedUrl, varName);

								success = true;
								break;
							}
						}

						if (success) {
							break;
						}
					}
				} catch (Exception x) {}
				Thread.sleep(2000);
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}

		Validator.assertTrue(success,
			"Could not get PDF download URL",
			"Embedded PDF download URL obtained successfull");
	}

	/**
	 * Wait for a number of milliseconds, refreshing 'refreshes' times after
	 * the milliseconds have passed, trying to see if the text of a locator matches the text
	 * sent. This is useful for long-running processes where we have to refresh the page
	 * several times until the text of a 'Status' changes from 'Open' to something like 'Completed'
	 * (e.g. Service Optimization Process)
	 * 
	 * @param milisec Number of miliseconds to wait each iteration (refresh)
	 * @param refreshes Number of times to refresh (iterations)
	 * @param loc The locator where we'll look for a specific text
	 * @param text The specific text to look for
	 */
	@QAFTestStep(description="wait {milisec} milisec up to {refreshes} times until {loc} has the text {text}")
	public static void waitMilisecAndRefreshUntilTextIs(int milisec, int refreshes, String loc, String text) {
		try {
			int currentRefreshes=0;
			while (currentRefreshes < refreshes) {
				try {
					$(loc).waitForPresent(milisec);
					$(loc).waitForText(text, milisec);
					logger.info("Text " + text + " found in locator " + loc + "!");
					break;
				} catch (Exception x) {
					logger.info("Text " + text + " not found yet for locator " + loc);
					currentRefreshes++;
					steps.common.StepsLibrary.executeJavaScript("window.location.reload();");
				}
			}
		} catch (Exception x) {
			logger.error("Error waiting for text " + text + " in locator " + loc, x);
		}
	}

	/**
	 * Salesforce manages it's loading lifetime in a special way.
	 * Since it uses a lot of Ajax (background loading of resources), the browser
	 * actually thinks it has finished loading, but the page is full of loading indicators
	 * and blank pages. This process attempts to use salesforce lightning internal javascript
	 * indicators to wait until the page has really finished loading (after all pending ajax processes
	 * have finished).
	 * 
	 * This doesn't mean that the full page is always loaded. Salesforce Lightning uses lazy-loading,
	 * which means if you scroll down on certain pages, only then is that information loaded. For example
	 * when viewing a 'Case' page, the 'Details' section may be loaded, but if you scroll down, the rest
	 * of the elements are not loaded and will start loading. This is important because some locators may
	 * return 'false' for 'isPresent()' function, because you have to scroll first to see them.
	 * 
	 * You can use 'and scroll until {loc} is visible' for this.
	 */
	@QAFTestStep(description="wait for the page to finish loading")
	public static void waitForPageToFinishLoading() {

		WebDriverWait wait = new WebDriverWait(new WebDriverTestBase().getDriver(), 60);
			
		//Page should have finished loading
		org.openqa.selenium.support.ui.ExpectedCondition<Boolean> jsLoad = 
		    new org.openqa.selenium.support.ui.ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
				
		//Salesforce objects should finish loading (in case $A is defined)
		org.openqa.selenium.support.ui.ExpectedCondition<Boolean> aurascriptLoad = 
		    new org.openqa.selenium.support.ui.ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				String A_IS_UNDEFINED = "return typeof $A === 'undefined' || !$A";
				Boolean aIsUndefined = (Boolean) ((JavascriptExecutor) driver).executeScript((A_IS_UNDEFINED));

				//If $A is undefined (e.g. login screen) just continue
				if (aIsUndefined) {
					logger.info("$A is undefined");
					return true;
				}

				String NO_AURA_CONFIG = " return typeof $A.metricsService !== 'undefined' && " +
					" typeof $A.metricsService.getCurrentPageTransaction !== 'undefined' && " +
					" typeof $A.metricsService.getCurrentPageTransaction() === 'undefined' ";

				Boolean noAuraConfig = (Boolean) ((JavascriptExecutor) driver).executeScript((NO_AURA_CONFIG));
				//If no aura config, we may be inside an iframe. Ignore then...{}
				if (noAuraConfig) {
					logger.info("No aura config");
					return true;
				}

				//$A object is defined. This must be a salesforce page so we should wait
				//until the metricsService returns a number (only done when page finished loading)
				//all sections and tabs open
				//This prevents things like clicking the search assistant to type, only to have
				//it be closed automatically when some tabs continue loading...
				String WAIT_FOR_AURA_SCRIPT = "return (typeof $A !== 'undefined' && $A && " +
				" typeof $A.metricsService !== 'undefined' && typeof $A.metricsService.getCurrentPageTransaction !== 'undefined' && " +
				" $A.metricsService.getCurrentPageTransaction() != null && typeof $A.metricsService.getCurrentPageTransaction().config !== 'undefined' && " +
				" typeof $A.metricsService.getCurrentPageTransaction().config.context !== 'undefined' && " +
				" $A.metricsService.getCurrentPageTransaction().config.context.ept > 0)";
				Boolean result = (Boolean) ((JavascriptExecutor) driver).executeScript((WAIT_FOR_AURA_SCRIPT));

				return result;
			}
		};
				
		if (wait.until(jsLoad) && wait.until(aurascriptLoad)) {
			logger.info("Page load complete");
		}
	}

	/**
	 * Finds the table with the locator 'tableLoc', then searches what column has a specific title
	 * (e.g. Status), and saves the index of that column in 'varName', that way it can be later used
	 * for assertions
	 * 
	 * @param tableLoc The locator of the table to check
	 * @param title The title the column must have (e.g. Status)
	 * @param varName The variable that will hold the column index number if found
	 */
	@QAFTestStep(description = "store table {tableLoc} column index with title {title} on {varName}")
	public static void storeTableColumnIndexWithTitleOn(String tableLoc, String title, String varName) {
		if (!$(tableLoc).isPresent() || !$(tableLoc).isEnabled()) {
			$(tableLoc).waitForPresent();
		}
		
		int idx = -1;

		List lstThs = $(tableLoc).findElements(By.xpath("//thead//th"));
		if (lstThs != null && lstThs.size() > 0) {
			System.out.println("List of THS has size of: " + lstThs.size());
			for (int i=0; i < lstThs.size(); i++) {
				if (((WebElement)lstThs.get(i)).getAttribute("title").equalsIgnoreCase(title)) {
					idx = i;
					break;
				}
			}
		}

		if (idx > -1) {
			CommonStep.store(String.valueOf(idx), varName);
		}

		Validator.assertTrue(idx > -1,
			"Column index with title " + title + " was not found on " + tableLoc,
			"Column index with title " + title + " found successfully on " + tableLoc + " with index " + idx);
	}

	/**
	 * Asserts that the table 'tableLoc' has 2 columns with specific texts. This works specially in places
	 * like the 'Case History' section of a Case, where we want to see if the 'Status' is 'Closed' or if
	 * 'Labor Billing' is 'Billable', etc. Since columns can change indexes, we use the index number to
	 * find it (see storeTableColumnIndexWithTitleOn).
	 * 
	 * @param tableLoc The locator of the table
	 * @param idx1 The index of the first column
	 * @param val1 The text that the first column must have (e.g. Status)
	 * @param idx2 The index of the second column
	 * @param val2 The text that the second column must have (e.g. Closed)
	 */
	@QAFTestStep(description = "assert row exists on table {tableLoc} where text on index {idx1} is {val1} and text on index {idx2} is {val2}")
	public static void assertRowExistsOnTableWithTextAndText(String tableLoc, String idx1, String val1, String idx2, String val2) {
		if (!$(tableLoc).isPresent() || !$(tableLoc).isEnabled()) {
			$(tableLoc).waitForPresent();
		}

		QAFWebElement element = null;
		try {
			element = $(tableLoc).findElement(By.xpath("//td[" + idx1 + " and .='" + val1 + "']//parent::tr//td[" + idx2 +" and .='" + val2 + "']"));
		} catch (Exception x) {}

		Validator.assertTrue(element != null && element.isPresent() && element.isEnabled(),
			"Could not find a row on table " + tableLoc + " where " + val1 + " = " + val2,
			"Found a row on table " + tableLoc + " where " + val1 + " = " + val2);
	}

	/**
	 * Clicks on a locator if another locator appears after a certain number of seconds
	 * This works in very specific cases. For example sometimes when trying to save an
	 * entity (e.g. a Contact), when trying to save, a message may appear indicating a similar
	 * contact already exists. In this case we can say: Click on 'Save' (again) if the message
	 * 'Similar etc etc' appears on screen (with locators, instead of the texts).
	 * 
	 * @param loc The locator to click on
	 * @param loc2 The locator that, if it appears, will trigger a click on the first locator
	 * @param secs The seconds to wait to see if the loc2 element appears
	 */
	@QAFTestStep(description = "click on {loc} if {loc2} appears within {secs} seconds")
	public static void clickOnIfAppearsWith(String loc, String loc2, int secs) {
		try {
			$(loc2).waitForVisible(secs*1000);
			if ($(loc2).isPresent()) {
				$(loc).click();
			}
		} catch (Exception x) {
			//Do nothing
		}
	}

	/**
	 * Wait for a number of milliseconds, refreshing 'refreshes' times after
	 * the milliseconds have passed, trying to see if the text of a locator contains the text
	 * sent. This is useful for long-running processes where we have to refresh the page
	 * several times until the text of a 'Status' changes from 'Open' to something like 'Completed'
	 * (e.g. Service Optimization Process)
	 * 
	 * This function is similar to waitMilisecAndRefreshUntilTextIs but this one checks if
	 * the text is contained, not exactly equal
	 * 
	 * @param milisec Number of miliseconds to wait each iteration (refresh)
	 * @param refreshes Number of times to refresh (iterations)
	 * @param loc The locator where we'll look for a specific text
	 * @param text The text to look for (should be contained in 'loc')
	 */
	@QAFTestStep(description="wait {milisec} milisec up to {refreshes} times until {loc} contains the text {text}")
	public static void waitMilisecAndRefreshUntilTextContains(int milisec, int refreshes, String loc, String text) {
		int currentRefreshes=0;
		boolean contained = false;
		while (currentRefreshes < refreshes) {
			try {
				$(loc).waitForPresent();
				String locText = $(loc).getText();
				contained = locText.contains(text);
				if (contained) {
					break;
				} else {
					Thread.sleep(milisec);
					steps.common.StepsLibrary.executeJavaScript("window.location.reload();");
				}
			} catch (Exception x) {
				logger.info("Text " + text + " not found yet for locator " + loc);
				currentRefreshes++;
				try {Thread.sleep(milisec);} catch (Exception ex){}
				steps.common.StepsLibrary.executeJavaScript("window.location.reload();");
			}
		}

		
		Validator.assertTrue(contained,
			"The text " + text + " was not contained in the text " + loc + " as expected",
			"The text " + text + " was found in the text " + loc + " as expected");
	}

	/**
	 * Store the timestamp of the latest email received. Email configuration
	 * appears in application.properties.
	 * 
	 * @param varName The variable where the latest message received timestamp will be saved
	 */
	@QAFTestStep(description="store the timestamp of the latest received email in {varName}")
	public static void storeLatestEmailTimestamp(String varName) {
		try {
			long time = 0;
			EmailHelper email = new EmailHelper();
			List<EmailObject> latestMessages = email.getLatestMessages(1);
			if (latestMessages != null) {
				for (EmailObject m : latestMessages) {
					time = m.getSentDate().getTime();
					break;
				}
			}

			CommonStep.store(time, varName);
		} catch (Exception x) {
			x.printStackTrace();
			throw new AssertionError("Could not store the timestamp of the latest received email in " + varName + ". Error details: " + x.getMessage());
		}
	}

	/**
	 * Assert that an email is received with a certain subject. To ensure it's the correct email
	 * we want, we send a timestamp so we don't read any emails received before this timestamp.
	 * 
	 * To be used in conjunction with 'storeLatestEmailTimestamp'
	 * 
	 * @param timestamp Don't read any emails received before this timestamp
	 * @param subject The subject of the email must contain this text
	 */
	@QAFTestStep(description="assert an email is received after {timestamp} and the subject contains the text {subject}")
	public static void assertEmailIsReceivedWithSubject(Object timestamp, String subject) {
		String matchSubject = null;
		try {
			int maxAttempts = 10;
			int waitBetweenAttemptsMilis = 5000;
			
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				logger.info("Looking for matching email");
				long compareTimestamp = Long.parseLong(timestamp + "");
				EmailHelper email = new EmailHelper();
				List<EmailObject> latestMessages = email.getLatestMessages(10);
				if (latestMessages != null) {
					for (EmailObject m : latestMessages) {
						long time = m.getSentDate().getTime();
						if (time > compareTimestamp) {
							//Time matches. Check subject
							if (m.getSubject().toUpperCase().trim().contains(subject.toUpperCase().trim())) {
								matchSubject = m.getSubject();
								break;
							}
						}
					}
				}
				if (matchSubject != null) {
					break;
				}
				logger.info("Email not found yet. Waiting...");
				Thread.sleep(waitBetweenAttemptsMilis);
			}
		} catch (Exception x) {
			x.printStackTrace();
		}

		Validator.assertTrue(matchSubject != null,
		"An email was not received after " + timestamp + " with a subject that contains the text " + subject,
		"An email was received after " + timestamp + " with a subject that contains the text " + subject + ". Email full subject: " + matchSubject);
	}

	/**
	 * This is not really used anywhere. It's just to be used for testing purposes
	 * by the tester. It checks the latest 3 email messages and prints on console
	 * the subject, from and sent date of the emails.
	 */
	@QAFTestStep(description="check the latest emails")
	public static void checkTheLatestEmails() {
		try {
			EmailHelper email = new EmailHelper();
			List<EmailObject> latestMessages = email.getLatestMessages(3);
			if (latestMessages != null) {
				for (EmailObject m : latestMessages) {
					System.out.println("Subject: " + m.getSubject());
					System.out.println("From: " + m.getFrom().get(0));
					System.out.println("Timestamp: " + m.getSentDate().getTime());
				}
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	/**
	 * Waits for a locator to be enabled, with a timeout in seconds
	 * 
	 * @param loc The locator to look for to be enabled
	 * @param sec Timeout in seconds
	 */
	@QAFTestStep(description = "wait until {loc} for a max of {sec} seconds to be enable")
	public static void waitForEnabledFoxMaxSeconds(String loc, long sec) {
		$(loc).waitForEnabled(sec * 1000);
	}

	/**
	 * Waits for a locator to be present, with a timeout in seconds
	 * 
	 * @param loc The locator to look for to be present
	 * @param sec Timeout in seconds
	 */
	@QAFTestStep(description = "wait until {loc} for a max of {sec} seconds to be present")
	public static void waitForPresentFoxMaxSeconds(String loc, long sec) {
		$(loc).waitForPresent(sec * 1000);
	}

	/**
	 * Some places (like FieldService) use a simple angular-based interface
	 * instead of the Salesforce-Lightning more complicated UI components.
	 * 
	 * This process searches for a checkbox with a specific locator and, if it is not
	 * checked, it clicks it
	 * 
	 * @param loc The locator for the checkbox to look for
	 */
	@QAFTestStep(description = "check angular checkbox {loc} if not checked")
	public static void checkCheckboxIfNotChecked(String loc) {
		$(loc).waitForPresent();
		boolean hasClass = $(loc).verifyCssClass("ng-not-empty");
		if (!hasClass) {
			$(loc).click();
			$(loc).waitForCssClass("ng-not-empty");
		}
	}

	/**
	 * Some places (like FieldService) use a simple angular-based interface
	 * instead of the Salesforce-Lightning more complicated UI components.
	 * 
	 * This process looks for a specific date in an angular datepicker
	 * and clicks it (e.g. on ServiceOptimization optimize popup section)
	 * 
	 * @param loc The locator for the date to look for in yyyy-MM-dd format
	 */
	@QAFTestStep(description = "select date {date} on angular datepicker")
	public static void selectDateOnAngularDatepicker(String date) {
		boolean success = false;
		try {
			int maxAttempts = 5;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					//Angular needs a bit to form its correct tree, or else we only get stale elements...
					Thread.sleep(2000);
					String xpath = "(//div[contains(@class, 'dhx_minical_popup')]//table//td[contains(@data-cell-date,'" + date + "')]//div)[1]";
					QAFExtendedWebElement element = new WebDriverTestBase().getDriver().findElementByXPath(xpath);
					element.click();
				} catch (Exception x) {
					//Ignore the stale error, it's still being clicked
					success = true; break;
				}
			}
		} catch (Exception x) {}

		Validator.assertTrue(success,
			"Could not select the date " + date + " on the angular datepicker",
			"Date " + date + " successfully selected on the angular datepicker");
	}

	/**
	 * Waits for a locator to be visible with a timeout in seconds
	 * 
	 * @param loc The locator to look for to be visible
	 * @param sec The timeout in seconds
	 */
	@QAFTestStep(description = "wait until {loc} for a max of {sec} seconds to be visible")
	public static void waitForVisibleFoxMaxSeconds(String loc, long sec) {
		$(loc).waitForVisible(sec * 1000);
	}

	/**
	 * Step to select a salesforce-picklist option.
	 * These kind of picklists are not regular select options, so they have to be managed differently
	 * 
	 * @param label The label of the option to select
	 */
	@QAFTestStep(description = "select the picklist option with label {label}")
	public static void selectSalesforcePicklistOption(String label) {
		boolean success = false;
		try {
			int maxAttempts = 5;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					String xpath = "//div[contains(@class, 'popupTargetContainer') and contains(@class, 'visible')]//div[@class='select-options']//li//a[.='" + label + "']";
					QAFExtendedWebElement element = new WebDriverTestBase().getDriver().findElementByXPath(xpath);
					element.waitForPresent();
					element.waitForVisible();
					element.click();
					element.waitForNotVisible();
					success = true;
					break;
				} catch (Exception x) {}
				Thread.sleep(2000);
			}
		} catch (Exception x) {}

		Validator.assertTrue(success,
			"Could not select the picklist option with label " + label,
			"Picklist option with the label " + label + " found and selected");
	}

	/**
	 * Step to select a salesforce-dropdown option (different from the picklist, which exists globally)
	 * These kind of drop downs are not regular select options either, so they have to be managed differently
	 * 
	 * @param inputLabel The name of the property in the form where the input and dropdown resides
	 *                     If it is mandatory, it must include the * (e.g. *Solar Stage)
	 * @param label The label of the option to select
	 */
	@QAFTestStep(description = "select the dropdown option for {inputLabel} with label {label}")
	public static void selectSalesforceDropdownOption(String inputLabel, String label) {
		boolean success = false;
		try {
			int maxAttempts = 5;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					String xpath = "//label[.='" + inputLabel + "']//ancestor::force-record-layout-item" +
						"//div[contains(@class, 'slds-dropdown') and contains(@class, 'slds-is-open')]" +
						"//lightning-base-combobox-item[.='" + label + "']";
					QAFExtendedWebElement element = new WebDriverTestBase().getDriver().findElementByXPath(xpath);
					element.waitForPresent();
					element.waitForVisible();
					element.click();
					element.waitForNotVisible();
					success = true;
					break;
				} catch (Exception x) {}
				Thread.sleep(2000);
			}
		} catch (Exception x) {}

		Validator.assertTrue(success,
			"Could not select the dropdown option with label " + label +  " and input " + inputLabel,
			"Dropdown option with the label " + label + " for input " + inputLabel + " found and selected");
	}
	
	/**
	 * Switch to a frame in a more secure way.
	 * 
	 * Sometimes we want to change to a specific iframe, but when doing so, may mistakenly get
	 * into an erroneous iframe (e.g. if we only change to the 'first iframe', it may not be
	 * the correct one because the page had not finished loading)
	 * 
	 * This process switches to the iframe in 'frameLocator', but searches if 'searchLocator'
	 * can be found once there. If not, it gets out, waits a while, and tries again several times.
	 * 
	 * @param frameLocator The locator of the frame/iframe to switch to
	 * @param searchLocator The locator that MUST be found once inside that frame/iframe
	 */
	@QAFTestStep(description = "switch to frame {frameLocator} until {searchLocator} is present")
	public static void switchToFrameUntilLocIsPresent(String frameLocator, String searchLocator) {

		boolean success = false;
		try {
			int maxAttempts = 6;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					new WebDriverTestBase().getDriver().switchTo().defaultContent();

					$(frameLocator).waitForPresent();
					new WebDriverTestBase().getDriver().switchTo().frame(new QAFExtendedWebElement(frameLocator));

					$(searchLocator).waitForPresent(5000);
					if ($(searchLocator).isPresent()) {
						success = true;
						break;
					}
				} catch (Exception x) {}
				Thread.sleep(3000);
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}

		Validator.assertTrue(success,
			"Could not find " + searchLocator + " in frame " + frameLocator,
			"Successfully switched to frame " + frameLocator);
	}

}