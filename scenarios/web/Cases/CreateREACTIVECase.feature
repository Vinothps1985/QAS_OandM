Feature: Cases

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a case of record type REACTIVE
@case @positive
@dataFile:resources/testdata/Cases/Create REACTIVE case.csv

Scenario: Create REACTIVE case
	
   Given ShrdLoginToFullCopy "${username}" "${password}"
   When wait until "applauncher.div" to be enable
   And click on "applauncher.div"
   Then wait until "applauncher.input.text" to be present
   When wait until "applauncher.input.text" to be enable
   And clear "applauncher.input.text"
   And wait until "applauncher.input.text" to be enable
   And sendKeys "projects" into "applauncher.input.text"
   Then wait until "applauncher.link.projects" to be visible
   When wait until "applauncher.link.projects" to be enable
   And click on "applauncher.link.projects"
   And wait until "common.searchAssistant.button" to be enable
   And click on "common.searchAssistant.button"
   And wait until "common.searchAssistant.input" to be enable
   And sendKeys "${projectName}" into "common.searchAssistant.input"
   Then wait until "projects.search.firstResult" to be visible
   When wait until "projects.search.firstResult" to be enable
   And click on "projects.search.firstResult"
   Then switch to default window 
   And wait until "projects.quicklink.contracts" to be present
   When wait until "projects.quicklink.contracts" to be enable
   And click on "projects.quicklink.contracts"
   Then wait until "projects.contracts.firstContract.status" to be present
   And assert "projects.contracts.firstContract.status" is present
   When wait until "breadcrumbs.second" to be enable
   And click on "breadcrumbs.second"
   And wait until "projects.createCase.button" to be enable
   And click on "projects.createCase.button"
   Then verify "projects.createCase.popup.OnM.first" is present
   When wait until "projects.createCase.popup.OnM.first.createCase.button" to be enable
   And click on "projects.createCase.popup.OnM.first.createCase.button"
   Then wait until "case.createCase.recordType.select" to be present
   When wait until "case.createCase.recordType.select" to be enable
   And click on "case.createCase.recordType.select"
   And wait until "case.createCase.recordType.select.reactive.option" to be enable
   And click on "case.createCase.recordType.select.reactive.option"
   And wait until "case.createCase.recordType.next.button" to be enable
   And click on "case.createCase.recordType.next.button"
   And wait until "case.createCase.popup.priority.select" to be enable
   And click on "case.createCase.popup.priority.select"
   And wait until "case.createCase.popup.priority.low.option" to be enable
   And click on "case.createCase.popup.priority.low.option"
   And wait until "case.createCase.popup.caseOrigin.select" to be enable
   And click on "case.createCase.popup.caseOrigin.select"
   And wait until "case.createCase.popup.caseOrigin.managerCreated.option" to be enable
   And click on "case.createCase.popup.caseOrigin.managerCreated.option"
   And wait until "case.createCase.popup.reportedIssue.select" to be enable
   And click on "case.createCase.popup.reportedIssue.select"
   And wait until "case.createCase.popup.reportedIssue.systemDown.option" to be enable
   And click on "case.createCase.popup.reportedIssue.systemDown.option"
   And wait until "case.createCase.popup.caseCause.select" to be enable
   And click on "case.createCase.popup.caseCause.select"
   And wait until "case.createCase.popup.caseCause.other.option" to be enable
   And click on "case.createCase.popup.caseCause.other.option"
   And wait until "case.createCase.popup.subject.input" to be enable
   And sendKeys "Sample subject from QAS" into "case.createCase.popup.subject.input"
   And wait until "case.createCase.popup.description.textarea" to be enable
   And sendKeys "Sample description from QAS" into "case.createCase.popup.description.textarea"
   Then verify "case.createCase.popup.recordType" text is "Reactive"
   When wait until "case.createCase.popup.save.button" to be enable
   And click on "case.createCase.popup.save.button"
   Then verify "case.entityName" text is "Case"
   And wait until "case.priority" to be present
   And assert "case.priority" text is "Low"
   And assert "case.caseOrigin" text is "Manager Created"
   And assert "case.reportedIssue" text is "System Down"
   And assert "case.caseCause" text is "Other"
   And assert "case.subject" text is "Sample subject from QAS"
   And assert "case.description" text is "Sample description from QAS"
   



