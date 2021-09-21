Feature: Projects

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a project for the opportunity with record type O&M Opportunity
@project @positive

Scenario: Create project for OM Opportunity
	
   Given get "https://bssi--fullcopy.lightning.force.com/"
   And maximizeWindow 
   When wait until "login.username" to be enable
   And clear "login.username"
   And wait until "login.username" to be enable
   And sendKeys "rmontemayor@borregosolar.com.fullcopy" into "login.username"
   And wait until "login.password" to be enable
   And clear "login.password"
   And wait until "login.password" to be enable
   And sendEncryptedKeys "RkNwYXNzd29yZDEyMyE=" into "login.password"
   And wait until "login.submit" to be enable
   And click on "login.submit"
   And wait until "applauncher.div" to be enable
   And click on "applauncher.div"
   Then wait until "applauncher.input.text" to be present
   When wait until "applauncher.input.text" to be enable
   And clear "applauncher.input.text"
   And wait until "applauncher.input.text" to be enable
   And sendKeys "opportunities" into "applauncher.input.text"
   And wait until "applauncher.link.opportunities" to be enable
   And click on "applauncher.link.opportunities"
   And wait until "opportunities.selectListView" to be enable
   And click on "opportunities.selectListView"
   And wait until "opportunities.selectListView.allOpportunities" to be enable
   And click on "opportunities.selectListView.allOpportunities"
   And wait until "opportunities.search.input" to be enable
   And clear "opportunities.search.input"
   And wait until "opportunities.search.input" to be enable
   And sendKeys "O&M" into "opportunities.search.input"
   And wait until "opportunities.search.input" to be enable
   And type Enter "opportunities.search.input"
   Then wait until "opportunities.search.results.first" to be visible
   When wait until "opportunities.search.results.first" to be enable
   And click on "opportunities.search.results.first"
   Then wait until "opportunities.createProject.button" to be visible
   When wait until "opportunities.createProject.button" to be enable
   And click on "opportunities.createProject.button"
   Then switch to frame "opportunities.createProject.iframe"
   And wait until "opportunities.createProject.template.select" to be present
   When wait until "opportunities.createProject.template.select.option.OM" to be enable
   And click on "opportunities.createProject.template.select.option.OM"
   And wait until "opportunities.createProject.template.createProject.button" to be enable
   And click on "opportunities.createProject.template.createProject.button"
   Then switch to default window 
   And wait until "projects.editProject.button" to be visible
   When wait until "projects.editProject.button" to be enable
   And click on "projects.editProject.button"
   And wait until "projects.editProject.popup.name.input" to be enable
   And clear "projects.editProject.popup.name.input"
   And wait until "projects.editProject.popup.name.input" to be enable
   And sendKeys "Project Name - Recording" into "projects.editProject.popup.name.input"
   And wait until "projects.editProject.popup.epcSite.input" to be enable
   And clear "projects.editProject.popup.epcSite.input"
   And wait until "projects.editProject.popup.epcSite.input" to be enable
   And sendKeys "Silveira Ranch Rd - Site 1" into "projects.editProject.popup.epcSite.input"
   And wait until "projects.editProject.popup.epcSite.input" to be enable
   And click on "projects.editProject.popup.epcSite.input"
   And wait until "projects.editProject.popup.epcSite.results.first" to be enable
   And click on "projects.editProject.popup.epcSite.results.first"
   And wait until "projects.editProject.popup.watts.input" to be enable
   And clear "projects.editProject.popup.watts.input"
   And wait until "projects.editProject.popup.watts.input" to be enable
   And sendKeys "100" into "projects.editProject.popup.watts.input"
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
   And verify "projects.details.projectName" text is "Project Name - Recording"
   And verify "projects.details.epcSite" text is "Silveira Ranch Rd - Site 1"
   And verify "projects.details.projectState" text is "California"
   



