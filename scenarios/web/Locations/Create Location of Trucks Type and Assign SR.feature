Feature: Cases

@author:Rodrigo Montemayor
@description:Verify that user is able to create a location of Trucks type and assign the Service Resource to the location
@location @positive @regression
@dataFile:resources/testdata/Locations/Create Location of Trucks Type and Assign SR.csv

Scenario: Create a Location of Trucks type and assign the Service Resource
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_o&m_manager"
   And launch salesforce app "locations"

   Then wait until "locations.new.button" to be enable
   And wait until "locations.new.button" to be visible
   And click on "locations.new.button"

   Then wait until "locations.create.popup.trucks.input" to be visible
   And click on "locations.create.popup.trucks.input"
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

   Then ShrdLaunchApp "service resources"
   And wait until "serviceResources.table.first.link" to be enable
   And wait until "serviceResources.table.first.link" to be visible
   And store text from "serviceResources.table.first.link" into "serviceResourceName"
   And click on "serviceResources.table.first.link"
   
   Then wait until "serviceResources.details.name" to be enable
   And wait until "serviceResources.details.name" to be visible
   And wait until "serviceResource.details.location.edit.button" to be enable
   And click on "serviceResource.details.location.edit.button"
   #If already has a location, delete it before continuing
   And click on "serviceResource.details.edit.location.delete.button" if it appears within 5000 milisec
   And wait until "serviceResource.details.edit.location.input" to be visible
   And click on "serviceResource.details.edit.location.input"
   And clear "serviceResource.details.edit.location.input"
   And sendKeys "${newLocationName}" into "serviceResource.details.edit.location.input"
   And wait for the page to finish loading
   And wait until "serviceResource.details.edit.location.firstOption" to be enable
   And wait until "serviceResource.details.edit.location.firstOption" to be visible
   And click on "serviceResource.details.edit.location.firstOption"
   And wait until "serviceResource.details.edit.location.firstOption" not to be visible
   And click on "serviceResources.details.save.button"
   And wait until "serviceResource.details.location.edit.button" to be visible
   And take a screenshot

   And wait until "serviceResources.details.location.link" to be enable
   And wait until "serviceResources.details.location.link" to be visible

   And store text from "serviceResources.details.shippingStreet" into "resourceStreet"
   And store text from "serviceResources.details.shippingCity" into "resourceCity"
   And store text from "serviceResources.details.shippingState" into "resourceState"
   And store text from "serviceResources.details.shippingZip" into "resourceZip"
   And click on "serviceResources.details.location.link"

   Then wait until "locations.details.locationName" to be enable
   And wait until "locations.details.locationName" to be visible
   #Reload
   Then Execute Java Script with data "window.location.reload();"
   Then wait until "locations.details.locationName" to be enable
   And wait until "locations.details.locationName" to be visible
   And take a screenshot
   
   #Need to try several times and refreshing until the resource appears
   #It usually takes 2-3 refreshes
   Then wait 5000 milisec up to 5 times until "location.details.serviceResource.link" has the text "${serviceResourceName}"
   And assert "locations.details.serviceResourceUser.link" text is "${serviceResourceName}"
   And assert "locations.details.serviceResourceEmail.link" text is not ""

   And scroll until "locations.details.zip" is visible
   And assert "locations.details.street" text is "${resourceStreet}"
   And assert "locations.details.city" text is "${resourceCity}"
   And assert "locations.details.state" text is "${resourceState}"
   And assert "locations.details.zip" text is "${resourceZip}"
   And take a screenshot