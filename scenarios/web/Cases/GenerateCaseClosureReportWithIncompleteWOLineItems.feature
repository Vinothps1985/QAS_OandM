Feature: Cases

@author:Rodrigo Montemayor
@description:Generate case closure report with incomplete WO Line Items
@case @regression @conga @pdf
@dataFile:resources/testdata/Cases/Generate case closure report with incomplete WO Line Items.csv

Scenario: Generate case closure report with incomplete WO Line Items
   
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   And launch salesforce app "cases"
   And create a case with data "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And take a screenshot
   And create a work order with data "${generated_caseNumber}" "${assetType1}" "${assetType2}"
   And take a screenshot

   #Before closing SA, WO and WO Line items, the case is now in status 'Scheduled'

   Then go to case "${generated_caseNumber}"
   And wait until "case.details.accountName" to be visible
   And wait until "case.details.accountName" to be enable
   And store text from "case.details.accountName" into "accountName"
   
   #Adds comments to all work orders 
   #It adds a sequential number to each comment(e.g. Testing comment 1, Testing comment 2, etc...)
   And add a comment starting with "Testing comment" to all work order line items of case "${generated_caseNumber}"
   And take a screenshot
   And go to case "${generated_caseNumber}"

   #Answer all the questions in the work order line items
   And answer all questions of all work order line items of case "${generated_caseNumber}" alternating pass and fail
   And go to case "${generated_caseNumber}"

   #Complete the Work Orders and Servie Appointments

   And set all work orders of case "${generated_caseNumber}" to status "Completed"
   And take a screenshot
  
   And set all service appointments of case "${generated_caseNumber}" to status "Completed"
   And take a screenshot

   #Do not set the line items as completed
   And go to case "${generated_caseNumber}"
   Then wait until "cases.quickLinks.workOrderLineItems" to be present
   And wait until "cases.quickLinks.workOrderLineItems" to be enable
   And click on "cases.quickLinks.workOrderLineItems"

	And wait until "woLineItems.table.firstResult.link" to be present
   And wait until "woLineItems.table.firstResult.link" to be enable
   And click on "woLineItems.table.firstResult.link"

   Then wait until "woLineItems.details.workOrder.link" to be enable
   And assert "woLineItems.details.status" text is not "Completed"
   And take a screenshot
   And go to case "${generated_caseNumber}"
   
   #Create the email
   And verify "cases.sendPmCorrLsReport.button" is present
   When wait until "cases.sendPmCorrLsReport.button" to be enable
   And click on "cases.sendPmCorrLsReport.button"

   And wait for the page to finish loading
   
   And switch to conga composer frame
   When wait until "conga.outputOptions.action.input" for a max of 180 seconds to be enable
   And assert "conga.outputOptions.action.input" value is "Email"
   And assert "conga.outputOptions.fileType.pdfFile.input.selected" is present
   And assert "conga.templates.correctiveReport.selected" is present
   And take a screenshot
   And click on "conga.mergeAndEmail.button"
   And wait until "conga.email.to.input" for a max of 180 seconds to be enable
   And assert "conga.email.to.input" value is not ""
   And assert "conga.email.cc.input" value is not ""
   And assert "conga.email.subject.input" value is not ""
   And assert "conga.email.attachment.img" is present
   And switch to frame "conga.email.contents.iframe"
   And assert "conga.email.body" text is not ""
   And assert "conga.email.body.logo" is present
   And take a screenshot
   And switch to parent frame
   And click on "conga.email.attachment.img"
   #Download time may vary and is not directly verifiable. Must leave implicit wait
   And wait for 8000 milisec
   And switch to default window
   And get "chrome://downloads"
   And get latest download url from chrome downloads
   And download file locally
   #Download time may vary and is not verifiable. Must leave implicit wait
   And wait for 8000 milisec

   #Download the PDF file and assert its contents
   Then load latest pdf in downloads directory
   And assert text "Case Number: ${generated_caseNumber}" appears in the pdf
   
   #Ensure no inspeciton results are present
   And assert text "Inspection Result" does not appear in the pdf