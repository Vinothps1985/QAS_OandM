Feature: Projects

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a project for the opportunity with record type O&M Opportunity
@project @positive @smoke
@dataFile:resources/testdata/Projects/Create project for OM Opportunity.csv
@requirementKey=QTM-RQ-23
Scenario: Create project for OM Opportunity
	
   Given ShrdLoginToFullCopy "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_ops_center_operator"
   And ShrdLaunchApp "opportunities"
   Then wait until "opportunities.selectListView" to be present
   When wait until "opportunities.selectListView" to be enable
   And click on "opportunities.selectListView"
   Then wait until "opportunities.selectListView.allOpportunities" to be present
   When wait until "opportunities.selectListView.allOpportunities" to be enable
   And click on "opportunities.selectListView.allOpportunities"
   Then wait until "opportunities.search.input" to be present
   When wait until "opportunities.search.input" to be enable
   And clear "opportunities.search.input"
   And wait for 3000 milisec
   And wait until "opportunities.search.input" to be enable
   And sendKeys "O&M" into "opportunities.search.input"
   And wait until "opportunities.search.input" to be enable
   And type Enter "opportunities.search.input"
   And wait for 3000 milisec
   Then wait until "opportunities.search.results.first" to be present
   When wait until "opportunities.search.results.first" to be enable
   And click on "opportunities.search.results.first"
   Then wait until "opportunities.createProject.button" to be visible
   When wait until "opportunities.createProject.button" to be enable
   And click on "opportunities.createProject.button"
   Then wait until "opportunities.createProject.iframe" to be visible
   And switch to frame "opportunities.createProject.iframe"
   And wait until "opportunities.createProject.template.select" to be present
   When wait until "opportunities.createProject.template.select.option.OM" to be enable
   And click on "opportunities.createProject.template.select.option.OM"
   And wait until "opportunities.createProject.template.createProject.button" to be enable
   And click on "opportunities.createProject.template.createProject.button"
   Then switch to default window 
   Then wait for 3000 milisec
   And wait until "projects.editProject.button" to be visible
   When wait until "projects.editProject.button" to be enable
   And click on "projects.editProject.button"
   And wait until "projects.editProject.popup.name.input" to be enable
   And clear "projects.editProject.popup.name.input"
   And wait until "projects.editProject.popup.name.input" to be enable
   And sendKeys "${projectName}" into "projects.editProject.popup.name.input"
   And wait until "projects.editProject.popup.epcSite.input" to be enable
   And clear "projects.editProject.popup.epcSite.input"
   And wait until "projects.editProject.popup.epcSite.input" to be enable
   And sendKeys "${epcSite}" into "projects.editProject.popup.epcSite.input"
   And wait until "projects.editProject.popup.epcSite.input" to be enable
   And click on "projects.editProject.popup.epcSite.input"
   Then wait until "projects.editProject.popup.epcSite.results.first" to be present
   When wait until "projects.editProject.popup.epcSite.results.first" to be enable
   And click on "projects.editProject.popup.epcSite.results.first"
   And wait until "projects.editProject.popup.watts.input" to be enable
   And clear "projects.editProject.popup.watts.input"
   And wait until "projects.editProject.popup.watts.input" to be enable
   And sendKeys "${watts}" into "projects.editProject.popup.watts.input"
   And wait until "projects.editProject.popup.available.firstOption.li" to be enable
   And click on "projects.editProject.popup.available.firstOption.li"
   And wait until "projects.editProject.popup.available.moveToChosen.button" to be enable
   And click on "projects.editProject.popup.available.moveToChosen.button"
   And wait until "projects.editProject.popup.available.firstOption.li" to be enable
   And click on "projects.editProject.popup.available.firstOption.li"
   And wait until "projects.editProject.popup.available.moveToChosen.button" to be enable
   And click on "projects.editProject.popup.available.moveToChosen.button"
   And wait until "projects.editProject.popup.save.button" to be enable
   And click on "projects.editProject.popup.save.button"
   Then wait until "common.toastContainer" to be present
   And wait until "projects.details.projectName" to be present
   And wait for 1500 milisec
   And assert "projects.details.projectName" text is "${projectName}"
   When wait until "projects.siteInformation.siteSubstantialDate.notEmpty" to be enable
   And click on "projects.siteInformation.siteSubstantialDate.notEmpty"
   Then assert "projects.details.epcSite" text is "${epcSite}"
   And verify "projects.siteInformation.overallProjectSizeWatts" text is "${watts}"
   And verify "projects.siteInformation.siteAddress" text is "${siteAddress}"
   And verify "projects.siteInformation.siteCity" text is "${siteCity}"
   And verify "projects.details.projectState" text is "${projectState}"
   And verify "projects.siteInformation.siteZip" text is "${siteZip}"
   And verify "projects.siteInformation.sitePTODate.notEmpty" is present
   And verify "projects.siteInformation.siteSubstantialDate.notEmpty" is present
   



