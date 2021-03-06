/*
 * Test Herokuapp 
 * FileUpload & Dynamic Loading testing.
 */

package tests.gui.task;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.browser.BrowserFactory;
import com.shaft.tools.io.ExcelFileManager;
import com.shaft.validation.Assertions;
import com.shaft.validation.Assertions.AssertionComparisonType;
import com.shaft.validation.Assertions.AssertionType;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import objectModels.gui.herokuapp.DynamicLoading;
import objectModels.gui.herokuapp.DynamicLoadingExample2;
import objectModels.gui.herokuapp.FileUpload;
import objectModels.gui.herokuapp.HerokuappHomePage;

public class TestHerokuapp {

    private ThreadLocal<WebDriver>  browser = new ThreadLocal<WebDriver>();
    private HerokuappHomePage home;
    private FileUpload file;
    private DynamicLoading dynamicLoadingPage;
    private DynamicLoadingExample2 example_2_Page;
   
    private ExcelFileManager testData;

    /**
     * @param "File_Path"      absolute file path
     * @param "File_Extension" extension of uploaded file e.g(.jpg)
     * @param testDataFileName test data file name need to access
     */
    @Test(description = "TC002 - Check file upload")
    @Description("Check that file uploaded successfully")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Given opened file upload page when choose file to upload and press upload button "
	    + "then I can see that the file chose correclty and page reloads and shows that file uploaded successfully with the correct file name")
    public void testFileUpload() {
	home.clickOnFileUpload();
	file.chooseFileToUpload(testData.getCellData("File_To_Upload_Path", "Data1"));
	file.clickOnUploadButton();
	Assertions.assertEquals(testData.getCellData("File_Extension", "Data1"), file.getUploadedFileText(),
		AssertionComparisonType.CONTAINS, AssertionType.POSITIVE);
    }

    /**
     * @param "Hello_World_Text" text after successful loading
     * @param testDataFileName   the file name need to access
     */
    @Test(description = "TC003 - Check dynamic loading Example2")
    @Description("Check that loading working successfully")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Given opened dynamic loading page when press on Example2 "
	    + "then I can see that loading bar in progress and page reloads "
	    + "and redirect to the target link of this dynamic loading page and shows the correct message ")
    public void testDynamicLoading() {
	home.clickOnDynamicLoading();
	dynamicLoadingPage.clickOnExample2();
	example_2_Page.clickOnStartButton();
	Assertions.assertEquals(testData.getCellData("Hello_World_Text", "Data1"),
		example_2_Page.getSuccessLoadingText(), AssertionComparisonType.CONTAINS, AssertionType.POSITIVE);
    }

    /**
     * @param testData	is ExcelFileManager object to fetch test data from excel file.
     */
    @BeforeClass
    public void beforeClass() {
	testData = new ExcelFileManager(System.getProperty("testDataFolderPath") + "TestData.xlsx");
    }
    // Before Method to be executed before test cases
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
	browser.set(BrowserFactory.getBrowser());
	home = new HerokuappHomePage(browser.get());
	file = new FileUpload(browser.get());
	dynamicLoadingPage = new DynamicLoading(browser.get());
	example_2_Page = new DynamicLoadingExample2(browser.get());
	BrowserActions.navigateToURL(browser.get(), testData.getCellData("URL_Herokuapp", "Data1"));
    }

    // Tear down
    @AfterMethod
    public void afterMethod() {
	BrowserActions.closeCurrentWindow(browser.get());
    }
}
