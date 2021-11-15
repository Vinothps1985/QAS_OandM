package steps.shareable.projects;

import com.qmetry.qaf.automation.step.QAFTestStep;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import steps.shareable.ShrdCreateProject;

public class ProjectSteps extends WebDriverTestCase {

	Log logger = LogFactory.getLog(getClass());

	@QAFTestStep(description = "create a project with data {opportunityName} {projectName} {epcSite} {watts}")
	public void createProjectWithData(String opportunityName, String projectName, String epcSite, String watts) {

		ShrdCreateProject create = new ShrdCreateProject();
		create.customShrdCreateProject(opportunityName, projectName, epcSite, watts);
	}
}