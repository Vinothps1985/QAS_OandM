package driver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriverException;

public class MyDriver extends AppiumDriver {

    Log logger = LogFactory.getLog(getClass());

    public MyDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, desiredCapabilities);
        logger.info("Custom Driver");
    }

    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        logger.info("Taking screenshot");
        return super.getScreenshotAs(outputType);
    }

}