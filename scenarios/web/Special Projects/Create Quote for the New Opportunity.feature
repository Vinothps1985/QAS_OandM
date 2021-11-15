Feature: Special Projects

@author:Rodrigo Montemayor
@description:Verify that user is able to create a Quote for the New Opportunity
@specialproject @regression
@dataFile:resources/testdata/Special Projects/Create Quote for the New Opportunity.csv
@requirementKey=QTM-RQ-23
Scenario: Create a Quote for the New Opportunity
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_special_projects"
   And close all open web tabs
   And launch salesforce app "opportunities"

   Then wait until "opportunities.new.button" to be enable
   And click on "opportunities.new.button"

   Then wait until "opportunities.create.popup.OMAddlSvcsSpecialPro.input" to be enable
   And click on "opportunities.create.popup.OMAddlSvcsSpecialPro.input"
   And click on "opportunities.create.popup.next.button"

   #Create the opportunity name based on the date and a random number
   Then store the current date in format "yy-MMdd" into "formattedDate"
   And create a random number with 5 digits and store it in "randomNumber"
   And store "${formattedDate}-${randomNumber}" into "opportunityName"

   Then wait until "opportunities.create.popup.opportunityName.input" to be enable
   And clear "opportunities.create.popup.opportunityName.input"
   And sendKeys "${opportunityName}" into "opportunities.create.popup.opportunityName.input"

   And click on "opportunities.create.popup.accountName.input"
   And clear "opportunities.create.popup.accountName.input"
   And sendKeys "${accountName}" into "opportunities.create.popup.accountName.input"
   Then wait until "opportunities.create.popup.accountName.firstOption" to be visible
   And click on "opportunities.create.popup.accountName.firstOption"
   And wait until "opportunities.create.popup.accountName.firstOption" not to be visible

   Then clear "opportunities.create.popup.sizeKW.input"
   And sendKeys "${sizeKW}" into "opportunities.create.popup.sizeKW.input"

   And click on "opportunities.create.popup.team.input"
   And clear "opportunities.create.popup.team.input"
   And sendKeys "${team}" into "opportunities.create.popup.team.input"
   Then wait until "opportunities.create.popup.team.firstOption" to be visible
   And click on "opportunities.create.popup.team.firstOption"
   And wait until "opportunities.create.popup.team.firstOption" not to be visible

   When wait until "opportunities.create.popup.solarStage.input" to be enable
   And click on "opportunities.create.popup.solarStage.input"
   And select the dropdown option for "*Solar Stage" with label "${solarStage}"

   Then click on "opportunities.create.popup.solarCloseDate.datepicker"
   And wait until "common.openLightningCalendar.today" to be enable
   And click on "common.openLightningCalendar.today"

   When wait until "opportunities.create.popup.projectLikelyhood.input" to be enable
   And click on "opportunities.create.popup.projectLikelyhood.input"
   And select the dropdown option for "*Project Likelihood" with label "${projectLikelyhood}"

   Then click on "opportunities.create.popup.expectedBillingStartDate.datepicker"
   And wait until "common.openLightningCalendar.today" to be enable
   And click on "common.openLightningCalendar.today"

   When wait until "opportunities.create.popup.dealType.input" to be enable
   And click on "opportunities.create.popup.dealType.input"
   And select the dropdown option for "*Deal Type" with label "${dealType}"

   Then clear "opportunities.create.popup.projectDescription.textarea"
   And sendKeys "${projectDescription}" into "opportunities.create.popup.projectDescription.textarea"

   When wait until "opportunities.create.popup.prevailingWage.input" to be enable
   And click on "opportunities.create.popup.prevailingWage.input"
   And select the dropdown option for "Prevailing Wage" with label "${prevailingWage}"

   #Contract details
   When wait until "opportunities.create.popup.paymentOptions.input" to be enable
   And click on "opportunities.create.popup.paymentOptions.input"
   And select the dropdown option for "*Payment Options" with label "${paymentOptions}"

   Then clear "opportunities.create.popup.regionalManagerRate.input"
   And sendKeys "${regionalManagerRate}" into "opportunities.create.popup.regionalManagerRate.input"

   Then clear "opportunities.create.popup.solarElectrician1Rate.input"
   And sendKeys "${solarElectrician1Rate}" into "opportunities.create.popup.solarElectrician1Rate.input"

   Then clear "opportunities.create.popup.solarElectrician2Rate.input"
   And sendKeys "${solarElectrician2Rate}" into "opportunities.create.popup.solarElectrician2Rate.input"

   Then clear "opportunities.create.popup.solarElectrician2OTRate.input"
   And sendKeys "${solarElectrician2OTRate}" into "opportunities.create.popup.solarElectrician2OTRate.input"

   Then clear "opportunities.create.popup.solarEngineerRate.input"
   And sendKeys "${solarEngineerRate}" into "opportunities.create.popup.solarEngineerRate.input"

   Then clear "opportunities.create.popup.solarEngineerOTRate.input"
   And sendKeys "${solarEngineerOTRate}" into "opportunities.create.popup.solarEngineerOTRate.input"

   Then click on "opportunities.create.popup.equipmentRentalIncluded.input"
   And select the dropdown option for "*Equipment Rental Included" with label "${equipmentRentalIncluded}"

   Then click on "opportunities.create.popup.materialEquipmentMarkup.input"
   And select the dropdown option for "*Material and Equipment Mark-Up" with label "${materialEquipmentMarkup}"

   Then click on "opportunities.create.popup.travelBillable.input"
   And select the dropdown option for "*Travel Billable ?" with label "${travelBillable}"

   Then clear "opportunities.create.popup.travelBillingCap.input"
   And sendKeys "${travelBillingCap}" into "opportunities.create.popup.travelBillingCap.input"

   Then click on "opportunities.create.popup.save.button"

   And wait until "opportunities.opportunityName" to be enable
   And store the current url in "opportunityURL"
   And take a screenshot
   And scroll until "opportunities.details.projectDescription" is visible
   And take a screenshot
   And scroll until "opportunities.details.travelBillingCap" is visible
   And take a screenshot

   Then click on "opportunities.createQuote.button"

   Then wait until "quotes.createQuote.popup.primaryContact.input" to be enable
   And click on "quotes.createQuote.popup.primaryContact.input"
   And clear "quotes.createQuote.popup.primaryContact.input"
   And sendKeys "${quotePrimaryContact}" into "quotes.createQuote.popup.primaryContact.input"
   And wait until "quotes.createQuote.popup.primaryContact.firstOption" to be visible
   And click on "quotes.createQuote.popup.primaryContact.firstOption"
   And wait until "quotes.createQuote.popup.primaryContact.firstOption" not to be visible

   Then click on "quotes.createQuote.popup.estimatedWorkStartDate.calendar.icon"
   And wait until "common.openLightningCalendar.today" to be enable
   And click on "common.openLightningCalendar.today"

   Then click on "quotes.createQuote.popup.siteWorkState.select"
   And select the dropdown option for "*Site/Work State" with label "${quoteSiteWorkState}"

   Then click on "quotes.createQuote.popup.status.input"
   And select the dropdown option for "*Status" with label "${quoteStatus}"

   Then click on "quotes.createQuote.popup.type.input"
   And select the dropdown option for "*Type" with label "${quoteType}"

   Then click on "quotes.createQuote.popup.typeOfQuote.input"
   And select the dropdown option for "Type of Quote" with label "${quoteTypeOfQuote}"

   Then click on "quotes.createQuote.popup.typeOfWork.select"
   And select the dropdown option for "*Type of Work" with label "${quoteTypeOfWork}"

   Then clear "quotes.createQuote.popup.specialNotes.textarea"
   And sendKeys "${quoteSpecialNotes}" into "quotes.createQuote.popup.specialNotes.textarea"

   Then click on "quotes.createQuote.popup.laborBilling.input"
   And select the dropdown option for "Labor Billing" with label "${quoteLaborBilling}"

   Then click on "quotes.createQuote.popup.pmBilling.input"
   And select the dropdown option for "P&M Billing" with label "${quotePmBilling}"

   Then clear "quotes.createQuote.popup.caseDetail.textarea"
   And sendKeys "${quoteCaseDetail}" into "quotes.createQuote.popup.caseDetail.textarea"

   Then click on "quotes.createQuote.popup.save.button"
   
   Then wait until "quote.details.quoteNumber" for a max of 60 seconds to be visible
   And take a screenshot
   And store text from "quote.details.quoteNumber" into "generated_quoteNumber"

   And assert "quotes.details.status" text is "${quoteStatus}"
   And assert "quotes.details.type" text is "${quoteType}"
   And assert "quote.details.typeOfQuote" text is "${quoteTypeOfQuote}"
   And assert "quote.details.typeOfWork" text is "${quoteTypeOfWork}"
   And assert "quote.details.siteWorkState" text is "${quoteSiteWorkState}"
   And take a screenshot

   And scroll until "quotes.details.specialNotes" is visible
   And assert "quotes.details.specialNotes" text is "${quoteSpecialNotes}"
   And take a screenshot

   And scroll until "quote.details.caseDetail" is visible
   And assert "quotes.details.laborBilling" text is "${quoteLaborBilling}"
   And assert "quotes.details.pmBilling" text is "${quotePmBilling}"
   And assert "quote.details.caseDetail" text is "${quoteCaseDetail}"
   And take a screenshot

   And get "${opportunityURL}"
   And wait until "opportunities.opportunityName" to be enable
   #Reload
   Then Execute Java Script with data "window.location.reload();"
   
   And wait until "opportunities.opportunityName" to be enable
   Then scroll until "quotes.relatedCards.quotes.header" is visible
   And assert "quotes.relatedCards.quotes.first" is present
   And assert "quotes.relatedCards.quotes.first" text is "${generated_quoteNumber}"
   And take a screenshot