Feature: Cases

@author:Rodrigo Montemayor

@case @regression @imthecaptainnow
@dataFile:resources/testdata/Cases/Generate case closure report with incomplete WO Line Items.csv
@requirementKey=QTM-RQ-23

Scenario: Generate case closure report with incomplete WO Line Items
   
   Given ShrdLogin "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_ops_center_operator"
   And ShrdLaunchApp "cases"
   And ShrdCreateCase "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And take a screenshot
   And ShrdCreateWorkOrder "${generated_caseNumber}" "${assetType1}" "${assetType2}"
   And take a screenshot

   And set all service appointments of case "${generated_caseNumber}" to status "Completed"
   And take a screenshot

   And set all work orders of case "${generated_caseNumber}" to status "Completed"
   And take a screenshot

   #Do not set the line items as completed

   Then go to case "${generated_caseNumber}"
   And wait until "case.details.accountName" to be visible
   And wait until "case.details.accountName" to be enable
   And store text from "case.details.accountName" into "accountName"
   #subject, case description and summary already in test data
   #Adds comments to all work orders (e.g. Testing comment 1, Testing comment 2, etc...)
   And add a comment starting with "Testing comment" to all work order line items of case "${generated_caseNumber}"
   And take a screenshot
   And go to case "${generated_caseNumber}"

   #Answer all the questions in the work order line items
   And answer all questions of all work order line items of case "${generated_caseNumber}" alternating pass and fail
   And go to case "${generated_caseNumber}"

   
   #Create the email
   And verify "cases.sendPmCorrLsReport.button" is present
   When wait until "cases.sendPmCorrLsReport.button" to be enable
   And click on "cases.sendPmCorrLsReport.button"

   And wait for the page to finish loading
   
   And switch to conga composer frame
   When wait until "conga.outputOptions.action.input" for a max of 60 seconds to be enable
   And assert "conga.outputOptions.action.input" value is "Email"
   And assert "conga.outputOptions.fileType.pdfFile.input.selected" is present
   And assert "conga.templates.correctiveReport.selected" is present
   And take a screenshot
   And click on "conga.mergeAndEmail.button"
   And wait until "conga.email.to.input" for a max of 60 seconds to be enable
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