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
   And wait for 4000 milisec
   And wait until "common.searchAssistant.button" to be enable
   And click on "common.searchAssistant.button"
   And wait until "common.searchAssistant.input" to be enable
   And click on "common.searchAssistant.input"
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
   
   #Agregado
   And wait for 20000 milisec
   And switch to conga composer frame
   When wait until "conga.outputOptions.action.input" to be enable
   And assert "conga.outputOptions.action.input" value is "Email"
   And assert "conga.outputOptions.fileType.pdfFile.input.selected" is present
   And assert "conga.templates.correctiveReport.selected" is present
   And click on "conga.mergeAndEmail.button"
   And wait for 12000 milisec
   And wait until "conga.email.to.input" to be enable
   And assert "conga.email.to.input" value is not ""
   And assert "conga.email.cc.input" value is not ""
   And assert "conga.email.subject.input" value is not ""
   And assert "conga.email.attachment.img" is present
   And switch to frame "conga.email.contents.iframe"
   And assert "conga.email.body" text is not ""
   And assert "conga.email.body.logo" is present
   And switch to parent frame
   And click on "conga.email.attachment.img"
   And wait for 3000 milisec
   And switch to default window
   And wait for 10000 milisec
   And get "chrome://downloads"
   And get latest download url from chrome downloads
   And download file locally
   And wait for 5000 milisec

   Then load latest pdf in downloads directory
   And wait for 10000 milisec
   And assert text "Case Number: 00290346" appears in the pdf
   And assert text "Project: Acushnet - ADM Makepeace" appears in the pdf
   And assert text "Account: CleanCapital" appears in the pdf
   And assert text "Subject: testing" appears in the pdf
   And assert text "Description: testing" appears in the pdf
   And wait for 10000 milisec

   



