Feature: Cases

@author:Rodrigo Montemayor

@case
@dataFile:resources/testdata/Cases/Generate case closure report with completed WO Line Items.csv

Scenario: Generate case closure report with completed WO Line Items
	
   Given ShrdLoginToFullCopy "${username}" "${password}"
   When wait until "applauncher.div" to be enable
   And click on "applauncher.div"
   Then wait until "applauncher.input.text" to be present
   When wait until "applauncher.input.text" to be enable
   And clear "applauncher.input.text"
   And wait until "applauncher.input.text" to be enable
   And sendKeys "cases" into "applauncher.input.text"
   Then wait until "applauncher.link.cases" to be visible
   When wait until "applauncher.link.cases" to be enable
   And click on "applauncher.link.cases"
   And wait until "common.searchAssistant.button" to be enable
   And click on "common.searchAssistant.button"
   And wait until "common.searchAssistant.input" to be enable
   And sendKeys "${reactiveCaseNumber}" into "common.searchAssistant.input"
   Then wait until "cases.search.firstResult" to be visible
   When wait until "cases.search.firstResult" to be enable
   And click on "cases.search.firstResult"
   And wait for 2000 milisec
   Then wait until "cases.caseNumber" to be visible
   And assert "cases.caseNumber" text is "${reactiveCaseNumber}"
   When wait until "cases.quickLinks.serviceAppointments" to be enable
   And click on "cases.quickLinks.serviceAppointments"
   Then assert "common.table.span.completed.first" is present
   When wait until "breadcrumbs.second" to be enable
   And click on "breadcrumbs.second"
   And wait until "cases.quickLinks.workOrders" to be enable
   And click on "cases.quickLinks.workOrders"
   Then assert "common.table.span.completed.first" is present
   When wait until "breadcrumbs.second" to be enable
   And click on "breadcrumbs.second"
   And wait until "cases.quickLinks.workOrderLineItems" to be enable
   And click on "cases.quickLinks.workOrderLineItems"
   Then store text from "common.table.td.third" into "woLineItemComment"
   When wait until "common.table.link.first" to be enable
   And click on "common.table.link.first"
   Then assert "woLineItems.header.status.completed" is present
   When wait until "woLineItems.details.case.link" to be enable
   And click on "woLineItems.details.case.link"
   Then store text from "case.details.accountName" into "accountName"
   And store text from "case.details.subject" into "subject"
   And store text from "case.details.description" into "description"
   And store text from "case.details.caseSummary" into "caseSummary"
   And verify "cases.sendPmCorrLsReport.button" is present
   When wait until "cases.sendPmCorrLsReport.button" to be enable
   And click on "cases.sendPmCorrLsReport.button"
   



