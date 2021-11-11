Feature: Service Contracts

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a Service contract with Active status
@servicecontract @positive @smoke
@dataFile:resources/testdata/Service Contracts/Create Service Contract with Active Status.csv
@requirementKey=QTM-RQ-23
Scenario: Create Service Contract with Active Status
	
   Given login to salesforce with "${username}" and "${password}"
   And ShrdChangeLoggedInUser "test_o&m_manager"
   And ShrdLaunchApp "projects"
   When wait until "common.searchAssistant.button" to be enable
   And click on "common.searchAssistant.button"
   And wait until "common.searchAssistant.input" to be enable
   And sendKeys "${projectName}" into "common.searchAssistant.input"
   Then wait until "projects.search.firstResult" to be visible
   When wait until "projects.search.firstResult" to be enable
   And click on "projects.search.firstResult"
   Then wait until "projects.details.projectName" to be present
   And assert "projects.details.projectName" text is "${projectName}"
   And take a screenshot

   Then wait until "project.details.opportunity.link" to be present
   And wait until "project.details.opportunity.link" to be enable
   And click on "project.details.opportunity.link"

   Then wait until "opportunities.details.opportunityRecordType" to be present
   And assert "opportunities.details.opportunityRecordType" text is "${opportunityRecordType}"
   And assert "opportunities.details.solarStage" text is "${solarStage}"
   And take a screenshot

   Then wait until "opportunities.createContract.button" to be enable
   And click on "opportunities.createContract.button"

   #Wait for the alert to appear for a max of 10 secs
   And waitForAlert 10000 millisec
   And acceptAlert 

   Then wait until "serviceContracts.newPaid.popup.title" to be present
   And wait until "serviceContracts.newPaid.popup.title" to be enable
   And take a screenshot

   #Select the form options
   When wait until "serviceContracts.newPaid.popup.branch.select" to be enable
   And click on "serviceContracts.newPaid.popup.branch.select"
   And select the picklist option with label "${branch}"

   And wait until "serviceContracts.newPaid.popup.contractStatus.select" to be enable
   And click on "serviceContracts.newPaid.popup.contractStatus.select"
   And select the picklist option with label "${contractStatus1}"
   And take a screenshot

   And wait until "serviceContracts.newPaid.popup.description.textarea" to be enable
   And sendKeys "${serviceContractDescription}" into "serviceContracts.newPaid.popup.description.textarea"

   And wait until "serviceContracts.newPaid.popup.contractAutoRenew.select" to be enable
   And click on "serviceContracts.newPaid.popup.contractAutoRenew.select"
   And select the picklist option with label "${contractAutoRenews}"
   
   And wait until "serviceContracts.newPaid.popup.serviceContractStartDate.calendar.icon" to be enable
   And click on "serviceContracts.newPaid.popup.serviceContractStartDate.calendar.icon"
   Then wait until "common.openCalendar.today" to be visible
   When wait until "common.openCalendar.today" to be enable
   And click on "common.openCalendar.today"
   And take a screenshot

   And wait until "serviceContracts.newPaid.popup.contractTerm.input" to be enable
   And sendKeys "${contractTermMonths}" into "serviceContracts.newPaid.popup.contractTerm.input"

   And wait until "serviceContracts.newPaid.popup.contractEndDate.calendar.icon" to be enable
   And click on "serviceContracts.newPaid.popup.contractEndDate.calendar.icon"
   Then wait until "common.openCalendar.year.select" to be visible
   When wait until "common.openCalendar.year.select" to be enable
   And click on "common.openCalendar.year.select"
   Then wait until "common.openCalendar.year.select.option.twoYearsLater" to be visible
   When wait until "common.openCalendar.year.select.option.twoYearsLater" to be enable
   And click on "common.openCalendar.year.select.option.twoYearsLater"
   Then wait until "common.openCalendar.firstDayOfOpenMonth" to be present
   When wait until "common.openCalendar.firstDayOfOpenMonth" to be enable
   And click on "common.openCalendar.firstDayOfOpenMonth"
   And take a screenshot

   #Data should be filled in automatically
   Then scroll until "serviceContracts.newPaid.popup.contractAmountYearly.input" is visible
   Then assert "serviceContracts.newPaid.popup.contractAmountYearly.input" value is not ""
   Then assert "serviceContracts.newPaid.popup.solarEngineerRate.input" value is not ""
   And take a screenshot

   When wait until "serviceContracts.newPaid.save.button" to be enable
   And click on "serviceContracts.newPaid.save.button"

   Then wait until "serviceContracts.edit.button" to be present
   And wait until "serviceContracts.edit.button" to be enable
   Then take a screenshot
   And click on "serviceContracts.edit.button"

   Then wait until "serviceContracts.edit.popup.regionalManager.input" to be present
   When wait until "serviceContracts.edit.popup.regionalManager.input" to be enable
   And sendKeys "${regionalManager}" into "serviceContracts.edit.popup.regionalManager.input"
   Then wait until "serviceContracts.edit.popup.regionalManager.firstOption" to be present
   And wait until "serviceContracts.edit.popup.regionalManager.firstOption" to be visible
   And click on "serviceContracts.edit.popup.regionalManager.firstOption"
   And wait until "serviceContracts.edit.popup.regionalManager.firstOption" not to be visible
   
   And wait until "serviceContracts.edit.popup.areaSupervisor.input" to be enable
   And sendKeys "${areaSupervisor}" into "serviceContracts.edit.popup.areaSupervisor.input"
   Then wait until "serviceContracts.edit.popup.areaSupervisor.firstOption" to be present
   When wait until "serviceContracts.edit.popup.areaSupervisor.firstOption" to be visible
   And click on "serviceContracts.edit.popup.areaSupervisor.firstOption"
   And wait until "serviceContracts.edit.popup.areaSupervisor.firstOption" not to be visible

   And wait until "serviceContracts.edit.popup.contractStatus.select" to be enable
   And click on "serviceContracts.edit.popup.contractStatus.select"
   And select the picklist option with label "${contractStatus2}"
   And take a screenshot

   And wait until "serviceContracts.details.edit.save.button" to be enable
   And click on "serviceContracts.details.edit.save.button"

   Then wait for the page to finish loading
   And wait until "serviceContracts.details.contractStatus.edit.button" to be enable
   And take a screenshot