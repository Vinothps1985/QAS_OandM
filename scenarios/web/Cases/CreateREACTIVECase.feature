Feature: Cases

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a case of record type REACTIVE
@case @positive @smoke
@dataFile:resources/testdata/Cases/Create REACTIVE case.csv
@requirementKey=QTM-RQ-23
Scenario: Create REACTIVE case
	
   Given ShrdLoginToFullCopy "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_ops_center_operator"
   And ShrdLaunchApp "projects"
   Then wait until "common.activeTab" to be visible
   When wait until "common.searchAssistant.button" to be enable
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
   And take a screenshot
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
   And sendKeys "${subject}" into "case.createCase.popup.subject.input"
   And wait until "case.createCase.popup.description.textarea" to be enable
   And sendKeys "${caseDescription}" into "case.createCase.popup.description.textarea"
   #Then verify "case.createCase.popup.recordType" text is "${recordType}"
   When wait until "case.createCase.popup.save.button" to be enable
   And click on "case.createCase.popup.save.button"
   Then verify "case.entityName" text is "Case"
   And wait until "case.priority" to be present
   And take a screenshot
   And assert "case.priority" text is "${casePriority}"
   And assert "case.caseOrigin" text is "${caseOrigin}"
   And assert "case.reportedIssue" text is "${reportedIssue}"
   And assert "case.caseCause" text is "${caseCause}"
   And assert "case.subject" text is "${subject}"
   And assert "case.description" text is "${caseDescription}"
   And scroll until "case.subject" is visible
   And take a screenshot