Feature: Service Contracts

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a Service contract with Active status
@servicecontract @positive @smoke
@dataFile:resources/testdata/Service Contracts/Create Service Contract with Active Status.csv
@requirementKey=QTM-RQ-23
Scenario: Create Service Contract with Active Status
	
   Given ShrdLoginToFullCopy "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_ops_center_operator"
   And ShrdLaunchApp "projects"
   When wait until "common.searchAssistant.button" to be enable
   And click on "common.searchAssistant.button"
   And wait until "common.searchAssistant.input" to be enable
   And sendKeys "${projectName}" into "common.searchAssistant.input"
   Then wait until "projects.search.firstResult" to be visible
   When wait until "projects.search.firstResult" to be enable
   And click on "projects.search.firstResult"
   Then wait until "project.details.opportunity.link" to be present
   When wait until "project.details.opportunity.link" to be enable
   And click on "project.details.opportunity.link"
   Then wait until "opportunities.details.opportunityRecordType" to be present
   And assert "opportunities.details.opportunityRecordType" text is "${opportunityRecordType}"
   And assert "opportunities.details.solarStage" text is "${solarStage}"
   When wait until "opportunities.createContract.button" to be enable
   And click on "opportunities.createContract.button"
   And waitForAlert 10000 millisec
   And acceptAlert 
   And wait for 5000 milisec
   Then wait until "serviceContracts.newPaid.popup.title" to be present
   And assert "serviceContracts.newPaid.popup.title" is present
   When wait until "serviceContracts.newPaid.popup.branch.select" to be enable
   And click on "serviceContracts.newPaid.popup.branch.select"
   Then wait until "serviceContracts.newPaid.popup.branch.norCal.option" to be visible
   When wait until "serviceContracts.newPaid.popup.branch.norCal.option" to be enable
   And click on "serviceContracts.newPaid.popup.branch.norCal.option"
   And wait until "serviceContracts.newPaid.popup.contractStatus.select" to be enable
   And click on "serviceContracts.newPaid.popup.contractStatus.select"
   Then wait until "serviceContracts.newPaid.popup.contractStatus.pending.option" to be visible
   When wait until "serviceContracts.newPaid.popup.contractStatus.pending.option" to be enable
   And click on "serviceContracts.newPaid.popup.contractStatus.pending.option"
   And wait until "serviceContracts.newPaid.popup.description.textarea" to be enable
   And sendKeys "${serviceContractDescription}" into "serviceContracts.newPaid.popup.description.textarea"
   And wait until "serviceContracts.newPaid.popup.contractAutoRenew.select" to be enable
   And click on "serviceContracts.newPaid.popup.contractAutoRenew.select"
   Then wait until "serviceContracts.newPaid.popup.contractAutoRenew.yes.option" to be visible
   When wait until "serviceContracts.newPaid.popup.contractAutoRenew.yes.option" to be enable
   And click on "serviceContracts.newPaid.popup.contractAutoRenew.yes.option"
   And wait until "serviceContracts.newPaid.popup.serviceContractStartDate.calendar.icon" to be enable
   And click on "serviceContracts.newPaid.popup.serviceContractStartDate.calendar.icon"
   Then wait until "common.openCalendar.today" to be visible
   When wait until "common.openCalendar.today" to be enable
   And click on "common.openCalendar.today"
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
   Then assert "serviceContracts.newPaid.popup.salesTaxResponsibility.label" is present
   And assert "serviceContracts.newPaid.popup.escalator.label" is present
   And assert "serviceContracts.newPaid.popup.solarElectrician1Rate.label" is present
   And assert "serviceContracts.newPaid.popup.solarEngineerRate.label" is present
   When wait until "serviceContracts.newPaid.save.button" to be enable
   And click on "serviceContracts.newPaid.save.button"
   Then wait until "serviceContracts.edit.button" to be present
   When wait until "serviceContracts.edit.button" to be enable
   And click on "serviceContracts.edit.button"
   Then wait until "serviceContracts.edit.popup.regionalManager.input" to be present
   When wait until "serviceContracts.edit.popup.regionalManager.input" to be enable
   And sendKeys "${regionalManager}" into "serviceContracts.edit.popup.regionalManager.input"
   And wait for 1000 milisec
   Then wait until "serviceContracts.edit.popup.regionalManager.firstOption" to be present
   When wait until "serviceContracts.edit.popup.regionalManager.firstOption" to be enable
   And click on "serviceContracts.edit.popup.regionalManager.firstOption"
   And wait until "serviceContracts.edit.popup.areaSupervisor.input" to be enable
   And sendKeys "${areaSupervisor}" into "serviceContracts.edit.popup.areaSupervisor.input"
   And wait for 1000 milisec
   Then wait until "serviceContracts.edit.popup.areaSupervisor.firstOption" to be present
   When wait until "serviceContracts.edit.popup.areaSupervisor.firstOption" to be enable
   And click on "serviceContracts.edit.popup.areaSupervisor.firstOption"
   And wait until "serviceContracts.edit.popup.contractStatus.select" to be enable
   And click on "serviceContracts.edit.popup.contractStatus.select"
   Then wait until "serviceContracts.edit.popup.contractStatus.active.option" to be present
   When wait until "serviceContracts.edit.popup.contractStatus.active.option" to be enable
   And click on "serviceContracts.edit.popup.contractStatus.active.option"
   And wait until "serviceContracts.details.edit.save.button" to be enable
   And click on "serviceContracts.details.edit.save.button"
   



