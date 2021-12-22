Feature: Cases

@author:Anusha MG
@description:Verify that user is able to create a location of Warehouse type
@location @positive @regression
@dataFile:resources/testdata/Locations/Create Location of Warehouse Type.csv

Scenario: Create a Location of Warehouse type
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_o&m_manager"
   And launch salesforce app "locations"

   Then wait until "locations.new.button" to be enable
   And wait until "locations.new.button" to be visible
   And click on "locations.new.button"

   Then wait until "locations.create.popup.warehouse.input" to be visible
   And click on "locations.create.popup.warehouse.input"
   And click on "locations.create.popup.next.button"

   Then create a random number with 5 digits and store it in "randomNumber"
   And store "${locationName} - ${randomNumber}" into "newLocationName"
   And clear "locations.create.popup.locationName.input"
   And sendKeys "${newLocationName}" into "locations.create.popup.locationName.input"

   Then clear "locations.create.popup.description.input"
   And sendKeys "${description}" into "locations.create.popup.description.input"

   Then click on "locations.create.popup.inventoryLocation.checkbox"

   Then click on "locations.create.popup.inventoryType.input"
   And select the picklist option with label "${inventoryType}"
   And take a screenshot

   Then click on "locations.create.popup.mobileLocation.checkbox"

   Then clear "locations.create.popup.street.input"
   And sendKeys "${street}" into "locations.create.popup.street.input"
   Then clear "locations.create.popup.city.input"
   And sendKeys "${city}" into "locations.create.popup.city.input"
   Then clear "locations.create.popup.state.input"
   And sendKeys "${state}" into "locations.create.popup.state.input"
   Then clear "locations.create.popup.zip.input"
   And sendKeys "${zip}" into "locations.create.popup.zip.input"
   Then scroll until "locations.create.popup.zip.input" is visible
   And take a screenshot
   And click on "locations.create.popup.save.button"

   Then wait for the page to finish loading
   And wait until "locations.details.locationName" to be enable
   And wait until "locations.details.locationName" to be visible
   And take a screenshot
   
   Then assert "locations.details.locationName" text is "${newLocationName}"
   And assert "locations.details.description" text is "${description}"
   And assert "locations.details.inventoryType" text is "${inventoryType}"

   And scroll until "locations.details.zip" is visible
   And assert "locations.details.street" text is "${street}"
   And assert "locations.details.city" text is "${city}"
   And assert "locations.details.state" text is "${state}"
   And assert "locations.details.zip" text is "${zip}"
   And take a screenshot