package steps.web;

import static com.qmetry.qaf.automation.step.CommonStep.click;
import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;
import com.qmetry.qaf.automation.util.Reporter;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;
import static com.qmetry.qaf.automation.step.CommonStep.sendKeys;
import support.Util;
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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import com.qmetry.qaf.automation.core.TestBaseProvider;
import com.qmetry.qaf.automation.step.CommonStep;
import com.qmetry.qaf.automation.step.QAFTestStep;

public class StepsLibrary {

	private static Log logger = LogFactory.getLog(StepsLibrary.class);

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

	@QAFTestStep(description = "load pdf called {0}")
    public static void loadPdfCalled(String pdfName) {
        try {
			latestPdfContents = null;
			System.out.println("loadPdfCalled(): Loading PDF: " + Util.DOWNLOADS_FOLDER + "/" + pdfName);
            File file = new File(Util.DOWNLOADS_FOLDER + "/" + pdfName);
            try (PDDocument document = Loader.loadPDF(file)) {
				PDFTextStripper pts = new PDFTextStripper();
				pts.setSortByPosition(true);
				latestPdfContents = pts.getText(document);
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
    public static void assertTextAppearsInPdf(String text) {
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

	@QAFTestStep(description = "assert text {0} does not appear in the pdf")
    public static void assertTextDoesNotAppearInPdf(String text) {
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

            Validator.assertTrue(!found,
                "The text " + text + " was found in the PDF, when it should not exist",
                "The text " + text + " was not found in the PDF, which is correct");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
	}

	@QAFTestStep(description = "assert pdf contains a line item of type {assetType} with comment {comment} alternating pass and fail")
    public static void assertPdfLineItemATCommentPassFail(String assetType, String comment) {
		boolean checklistValidated = false;
		boolean commentsValidated = false;

		if (latestPdfContents != null) {
			String[] lines = latestPdfContents.split("\n");
			boolean inLineItemsSection = false;
			boolean inLineItem = false;
			boolean afterChecklistTableHeader = false;
			boolean inChecklistSection = false;
			boolean inComments = false;
			String lastLine = null;
			int responses = 0;
			String fullComments = "";
			int lineNum=0;
			boolean afterIndex = false;
			String fullChecklist = "";
			for (String line : lines) {
				lineNum++;
				System.out.println(lineNum);
				//Skip the index page to go straight to the real contents
				if (!afterIndex) {
					if (line.startsWith("Page 2 of")) {
						afterIndex = true;
					}
					continue;
				}
				if (line.startsWith("Page") && line.contains(" of ")) {
					continue;
				}
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
				if (Character.isDigit(line.toCharArray()[0]) || line.startsWith("Comments")) {
					System.out.println("Digit or comments: " + line);
					if (fullChecklist.length() > 0) {
						//Check if the last line has the response we expected at the end
						String expectedResponse = responses%2 == 0 ? "Fail" : "Pass"; //Even = Fail. Odd = Pass
						boolean correct = fullChecklist.trim().contains(expectedResponse);
						Validator.assertTrue(correct,
						"Checklist item " + (responses+1) + " does not have the expected answer: Expected: " + expectedResponse + ", Actual text processed: " + fullChecklist.trim(),
						"Checklist item " + (responses+1) + " has the expected answer: " + expectedResponse);

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

						//Now we can exit
						commentsValidated = true;
						break;
					} else {
						//More comments, continue adding...
						System.out.println("Adding mor comments:  " + line);
						fullComments += line.trim();
					}
				}
			}
		}

		Validator.assertTrue(commentsValidated && checklistValidated,
			"Comments and checklist were not validated correctly",
			"Comments and checklist validated correctly");
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
	
	@QAFTestStep(description="take a screenshot")
	public static void takeAScreenshot() {
		//TestBaseProvider.instance().get().takeScreenShot();
		Reporter.logWithScreenShot("take a screenshot");
	}

	@QAFTestStep(description="scroll until {0} is visible")
	public static void scrollUntilVisible(String locator) {
		try {
			logger.info("Scrolling until " + locator + " is visible");
			if ($(locator).isPresent()) {
				logger.info("Scrolling mode 1");
				((JavascriptExecutor)new WebDriverTestBase().getDriver())
							.executeScript("arguments[0].scrollIntoView({block: 'center'});", $(locator));
			} else {
				logger.info("Scrolling mode 2");			
				
				int maxScrolls = 10;
				int scrolls = 1;
				while (scrolls <= maxScrolls) {
					StringBuilder javascript = new StringBuilder();
					javascript.append("var automation_scrollingElement = (document.scrollingElement || document.body);");
					javascript.append("var automation_scrollHeight = automation_scrollingElement.scrollHeight;");
					javascript.append("automation_scrollingElement.scrollTop = (300*"+scrolls + ");");
					((JavascriptExecutor)new WebDriverTestBase().getDriver()).executeScript(javascript.toString());
					Thread.sleep(500);
					if ($(locator).isPresent()) {
						logger.info("Scrolling mode 2: found");
			((JavascriptExecutor)new WebDriverTestBase().getDriver())
						.executeScript("arguments[0].scrollIntoView({block: 'center'});", $(locator));
						break;
					}
					scrolls++;
				}
			}
			Thread.sleep(500);
		} catch (Exception x) {
			//?
			x.printStackTrace();
		}
	}

	/**
	 * 
	 * @param milisec
	 * @param refreshes
	 * @param loc
	 * @param text
	 */
	@QAFTestStep(description="wait {milisec} milisec up to {refreshes} times until {loc} has the text {text}")
	public static void waitMilisecAndRefreshUntilTextIs(int milisec, int refreshes, String loc, String text) {
		try {
			int currentRefreshes=0;
			while (currentRefreshes < refreshes) {
				try {
					$(loc).waitForPresent();
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

	@QAFTestStep(description="store the current url in {variableName}")
	public static void saveTheCurrentUrl(String variableName) {
		String url = new WebDriverTestBase().getDriver().getCurrentUrl();
		logger.info("Storing URL " + url + " in variable named " + variableName);
		CommonStep.store(url, variableName);
	}

	@QAFTestStep(description="store the current time in {variableName}")
	public static void saveTheCurrentTime(String variableName) {
		String time = String.valueOf(new Date().getTime());
		logger.info("Storing time " + time + " in variable named " + variableName);
		CommonStep.store(time, variableName);
	}

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

	@QAFTestStep(description = "create a random number with {digits} digits and store it in {varName}")
	public static void createRandomNumber(int digits, String varName) {

		int num = (int) Math.pow(10, digits - 1);
		String randomNumber = String.valueOf(num + new Random().nextInt(9 * num));
	
		CommonStep.store(randomNumber, varName);
	}

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

	@QAFTestStep(description = "assert {loc} contains the text {text}")
	public static void assertLocContainsText(String loc, String text) {
		$(loc).waitForEnabled();
		String locText = $(loc).getText();
		boolean contained = locText.contains(text);
		Validator.assertTrue(contained,
			"The text " + text + " was not contained in the text " + locText + " as expected",
			"The text " + text + " was found in the text " + locText + " as expected");
	}

	/**
	 * 
	 * @param milisec
	 * @param refreshes
	 * @param loc
	 * @param text
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


	@QAFTestStep(description = "wait until {loc} for a max of {sec} seconds to be enable")
	public static void waitForEnabledFoxMaxSeconds(String loc, long sec) {
		$(loc).waitForEnabled(sec * 1000);
	}

	@QAFTestStep(description = "format date {originDate} from {originFormat} to {destFormat} into {destVar}")
	public static void formatDateFromVarToVarWithFormats(String originDate, String originFormat, String destFormat, String destVar) {
		boolean success = false;
		try {
			Date date = new java.text.SimpleDateFormat(originFormat).parse(originDate);
			CommonStep.store(new java.text.SimpleDateFormat(destFormat).format(date), destVar);
			success = true;
		} catch (Exception x) {}

		Validator.assertTrue(success,
			"Could not format date " + originDate + " with format " + originFormat + " to convert to format " + destFormat,
			"Date " + originDate + " with format " + originFormat + " converted successfully to format " + destFormat);
	}

}
