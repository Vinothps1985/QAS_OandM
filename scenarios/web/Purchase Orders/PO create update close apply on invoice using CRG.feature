Feature: Purchase Orders

@author:Rodrigo Montemayor
@description:Verify the Purchase order creation, update, close and applying them on the AP Invoice using Con Req Groups
@purchaseorder @positive @regression
@dataFile:resources/testdata/Purchase Orders/PO create update close apply on invoice using CRG.csv
@requirementKey=QTM-RQ-23
Scenario: Verify PO creation, update, close, apply on AP Invoice using Con Req Groups
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_po procurement"

   #These users use classic mode!

   Then wait until "classic.home.allTabs.link" to be enable
   And click on "classic.home.allTabs.link"

   Then wait until "classic.home.allTabs.constructionRequisitions.link" to be enable
   And click on "classic.home.allTabs.constructionRequisitions.link"

   #Iterate over the Con Req lines by attempting to create a PO from one.
   #If it's not possible, then attempt the next one, etc.
   Then attempt to create a PO from available construction requisitions

   #PO created
   Then wait until "classic.common.success.h4" to be present
   And assert "classic.common.success.h4" is present
   And take a screenshot

   Then wait until "classic.home.allTabs.link" to be enable
   And click on "classic.home.allTabs.link"

   Then wait until "classic.home.allTabs.purchaseOrders.link" to be enable
   And click on "classic.home.allTabs.purchaseOrders.link"

   Then wait until "classic.purchaseOrders.recentTable.first.link" to be enable
   And click on "classic.purchaseOrders.recentTable.first.link"

   Then wait until "classic.common.pageDescription.h2" to be present
   And store text from "classic.common.pageDescription.h2" into "purchaseOrder"
   And take a screenshot

   Then click on "classic.common.edit.button.first"
   And wait until "classic.purchaseOrders.edit.paymentTerms.input" to be present

   #Set payment terms
   Then clear "classic.purchaseOrders.edit.paymentTerms.input"
   And sendKeys "${paymentTerms}" into "classic.purchaseOrders.edit.paymentTerms.input"
   #Set Terms and Conditions
   And click on "classic.purchaseOrders.edit.termsAndConditions.search.link"
   And switchWindow "1"
   And wait until "classic.common.frame.first" to be present
   And switch to frame "classic.common.frame.first"
   And sendKeys "${termsAndConditions}" into "classic.common.search.input"
   And click on "classic.common.search.go.button"
   And switch to parent frame
   And switch to frame "classic.common.frame.second"
   And wait until "classic.common.search.showAllResults.link" to be present
   And wait until "classic.common.search.showAllResults.link" to be enable
   And click on "classic.common.search.showAllResults.link"

   And wait until "classic.common.search.results.first" to be present
   And wait until "classic.common.search.results.first" to be enable
   And assert "classic.common.search.results.first" text is "${termsAndConditions}"
   And click on "classic.common.search.results.first"
   And switchWindow "0"
   Then switch to default window

   #Shipping method and delivery notes
   Then clear "classic.purchaseOrders.edit.shippingMethod.input"
   And sendKeys "${shippingMethod}" into "classic.purchaseOrders.edit.shippingMethod.input"
   And clear "classic.purchaseOrders.edit.deliveryNotes.input"
   And sendKeys "${deliveryNotes}" into "classic.purchaseOrders.edit.deliveryNotes.input"
   And take a screenshot
   And click on "classic.common.save.button.first"

   #Click on first po line
   And wait until "classic.purchaseOrders.poLines.table.name.link.first" to be enable
   And click on "classic.purchaseOrders.poLines.table.name.link.first"

   #PO Line page, clone
   Then wait until "classic.common.clone.button.first" to be enable
   And click on "classic.common.clone.button.first"
   Then wait until "classic.common.save.button.first" to be enable
   And click on "classic.common.save.button.first"

   #Edit the clone
   Then wait until "classic.common.edit.button.first" to be enable
   And click on "classic.common.edit.button.first"

   Then wait until "classic.poLines.edit.unitCost.input" to be enable
   And clear "classic.poLines.edit.unitCost.input"
   And sendKeys "${unitCost}" into "classic.poLines.edit.unitCost.input"

   And clear "classic.poLines.edit.freight.input"
   And sendKeys "${freight}" into "classic.poLines.edit.freight.input"

   And clear "classic.poLines.edit.quantity.input"
   And sendKeys "${quantity}" into "classic.poLines.edit.quantity.input"

   #Set Site
   And click on "classic.poLines.edit.site.search.link"
   And switchWindow "1"
   And wait until "classic.common.frame.first" to be present
   And switch to frame "classic.common.frame.first"
   And sendKeys "${site}" into "classic.common.search.input"
   And click on "classic.common.search.go.button"
   And switch to parent frame
   And switch to frame "classic.common.frame.second"
   And wait until "classic.common.search.showAllResults.link" to be present
   And wait until "classic.common.search.showAllResults.link" to be enable
   And click on "classic.common.search.showAllResults.link"

   And wait until "classic.common.search.results.first" to be present
   And wait until "classic.common.search.results.first" to be enable
   And assert "classic.common.search.results.first" text is "${site}"
   And click on "classic.common.search.results.first"
   And switchWindow "0"
   Then switch to default window

   And take a screenshot
   And click on "classic.common.save.button.first"
   Then wait until "classic.common.edit.button.first" to be enable
   And take a screenshot

   And change logged in user to "test_po accounting"

   Then wait until "classic.home.allTabs.link" to be enable
   And click on "classic.home.allTabs.link"

   Then wait until "classic.home.allTabs.apInvoices.link" to be enable
   And click on "classic.home.allTabs.apInvoices.link"

   And wait until "classic.common.new.button.first" to be enable
   And click on "classic.common.new.button.first"

   #Set PO
   And wait until "classic.invoices.edit.purchaseOrder.search.link" to be enable
   And click on "classic.invoices.edit.purchaseOrder.search.link"
   And switchWindow "1"
   And wait until "classic.common.frame.first" to be present
   And switch to frame "classic.common.frame.first"
   And sendKeys "${purchaseOrder}" into "classic.common.search.input"
   And click on "classic.common.search.go.button"
   And switch to parent frame
   And switch to frame "classic.common.frame.second"
   #And wait until "classic.common.search.showAllResults.link" to be present
   #And wait until "classic.common.search.showAllResults.link" to be enable
   #And click on "classic.common.search.showAllResults.link"
   And wait until "classic.common.search.results.first" to be present
   And wait until "classic.common.search.results.first" to be enable
   And assert "classic.common.search.results.first" text is "${purchaseOrder}"
   And click on "classic.common.search.results.first"
   And switchWindow "0"
   Then switch to default window

   And clear "classic.invoices.edit.invoiceAmount.input"
   And sendKeys "${invoiceAmount}" into "classic.invoices.edit.invoiceAmount.input"

   #Due date
   And click on "classic.invoices.edit.dueDate.today.link"

   #Employee
   And click on "classic.invoices.edit.employee.search.link"
   And switchWindow "1"
   And wait until "classic.common.frame.first" to be present
   And switch to frame "classic.common.frame.first"
   And sendKeys "${invoiceEmployee}" into "classic.common.search.input"
   And click on "classic.common.search.go.button"
   And switch to parent frame
   And switch to frame "classic.common.frame.second"
   #And wait until "classic.common.search.showAllResults.link" to be present
   #And wait until "classic.common.search.showAllResults.link" to be enable
   #And click on "classic.common.search.showAllResults.link"
   And wait until "classic.common.search.results.first" to be present
   And wait until "classic.common.search.results.first" to be enable
   And assert "classic.common.search.results.first" text is "${invoiceEmployee}"
   And click on "classic.common.search.results.first"
   And switchWindow "0"
   Then switch to default window

   Then create a random number with 7 digits and store it in "invoiceNumber"
   And clear "classic.invoices.edit.invoiceNumber.input"
   And sendKeys "${invoiceNumber}" into "classic.invoices.edit.invoiceNumber.input"

   And clear "classic.invoices.edit.project.input"
   And sendKeys "${invoiceProject}" into "classic.invoices.edit.project.input"

   #Save the invoice
   And click on "classic.common.save.button.first"
   And wait until "classic.common.edit.button.first" to be present
   And take a screenshot

   Then wait until "classic.invoices.details.newPayableLine.button" to be enable
   And click on "classic.invoices.details.newPayableLine.button"
   
   #Payable Line
   Then wait until "classic.payableLines.edit.branch.search.link" to be enable
   And click on "classic.payableLines.edit.branch.search.link"
   And switchWindow "1"
   And wait until "classic.common.frame.first" to be present
   And switch to frame "classic.common.frame.first"
   And sendKeys "${payableLineBranch}" into "classic.common.search.input"
   And click on "classic.common.search.go.button"
   And switch to parent frame
   And switch to frame "classic.common.frame.second"
   #And wait until "classic.common.search.showAllResults.link" to be present
   #And wait until "classic.common.search.showAllResults.link" to be enable
   #And click on "classic.common.search.showAllResults.link"
   And wait until "classic.common.search.results.first" to be present
   And wait until "classic.common.search.results.first" to be enable
   And assert "classic.common.search.results.first" text is "${payableLineBranch}"
   And click on "classic.common.search.results.first"
   And switchWindow "0"
   Then switch to default window

   Then clear "classic.payableLines.edit.total.input"
   And sendKeys "${payableLineTotal}" into "classic.payableLines.edit.total.input"

   Then wait until "classic.payableLines.edit.expenseGLAccount.search.link" to be enable
   And click on "classic.payableLines.edit.expenseGLAccount.search.link"
   And switchWindow "1"
   And wait until "classic.common.frame.first" to be present
   And switch to frame "classic.common.frame.first"
   And sendKeys "${payableLineGlAccount}" into "classic.common.search.input"
   And click on "classic.common.search.go.button"
   And switch to parent frame
   And switch to frame "classic.common.frame.second"
   #And wait until "classic.common.search.showAllResults.link" to be present
   #And wait until "classic.common.search.showAllResults.link" to be enable
   #And click on "classic.common.search.showAllResults.link"
   And wait until "classic.common.search.results.first" to be present
   And wait until "classic.common.search.results.first" to be enable
   And assert "classic.common.search.results.first" text is "${payableLineGlAccount}"
   And click on "classic.common.search.results.first"
   And switchWindow "0"
   Then switch to default window

   #Save the payable line
   And click on "classic.common.save.button.first"

   And wait until "classic.payableLines.details.apInvoice.link" to be enable
   And take a screenshot
   And click on "classic.payableLines.details.apInvoice.link"

   And wait until "classic.invoices.details.payeeInformation.purchaseOrder.link" to be enable
   And take a screenshot
   And scroll until "classic.invoices.details.payeeInformation.purchaseOrder.link" is visible
   And assert "classic.invoices.details.payeeInformation.purchaseOrder.link" text is "${purchaseOrder}"
   And assert "classic.invoices.details.termsInformation.dueDate" text is not ""
   And take a screenshot
   #TODO: Project gets deleted when we add a payable line. Hmmm
   #And scroll until "classic.invoices.details.accountingInformation.project" is visible
   #And assert "classic.invoices.details.accountingInformation.project" text is "${invoiceProject}"
   And take a screenshot
   And scroll until "classic.invoices.details.payableLines.table.row.first" is visible
   And assert "classic.invoices.details.payableLines.table.row.first" is present
   And take a screenshot

   #Copy the invoice amount and paste it on the payable line
   And store text from "classic.invoices.details.invoiceAmount" into "copiedInvoiceAmount"
   And click on "classic.invoices.details.payableLines.table.row.first.edit.button"

   Then clear "classic.payableLines.edit.total.input"
   And sendKeys "${copiedInvoiceAmount}" into "classic.payableLines.edit.total.input"
   #Save the payable line
   And click on "classic.common.save.button.first"

   Then wait until "classic.invoices.details.pauableLines.table.row.first.last.td" to be enable
   And scroll until "classic.invoices.details.pauableLines.table.row.first.last.td" is visible
   And assert "classic.invoices.details.pauableLines.table.row.first.last.td" contains the text "${copiedInvoiceAmount}"

   #Change the status to approved
   Then click on "classic.common.edit.button.first"
   And wait until "classic.invoices.edit.invoiceStatus.select" to be enable
   Then select "label=Approved" in "classic.invoices.edit.invoiceStatus.select"
   And click on "classic.common.save.button.first"
   Then wait until "classic.invoices.details.invoiceStatus" to be enable
   And assert "classic.invoices.details.invoiceStatus" text is "Approved"
   And take a screenshot

   #Post
   Then click on "classic.common.post.button.first"
   And wait until "classic.invoices.post.title" to be present
   And take a screenshot
   Then click on "classic.common.post.button.first"

   Then wait until "classic.invoices.details.payeeInformation.purchaseOrder.link" to be enable
   And take a screenshot
   And click on "classic.invoices.details.payeeInformation.purchaseOrder.link"

   #In purchase order screen
   Then wait until "classic.common.edit.button.first" to be enable
   And click on "classic.common.edit.button.first"

   Then wait until "classic.purchaseOrders.edit.status.select" to be enable
   And select "label=Closed" in "classic.purchaseOrders.edit.status.select"
   And click on "classic.common.save.button.first"

   Then wait until "classic.purchaseOrders.details.status" to be enable
   And assert "classic.purchaseOrders.details.status" text is "Closed"
   And take a screenshot