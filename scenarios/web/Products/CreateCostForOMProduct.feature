Feature: Products

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a Cost using an O&M Product
@product @positive @cost
@dataFile:resources/testdata/Products/Create Cost For OM Product.csv
@requirementKey=QTM-RQ-23
Scenario: Create Cost for OM Product
	
   Given ShrdLogin "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_ops_center_operator"
   #Product name will be the one in the test data file, with added random numbers
   #to prevent duplication
   And create a random number with 6 digits and store it in "randomNumber"
   And ShrdCreateProduct "${productName}-${randomNumber}" "${productDescription}" "${oemManufacturer}" "${activeDataSheet}"

   And take a screenshot

   Then wait until "products.costs.dropDownMenu" to be present
   When wait until "products.costs.dropDownMenu" to be enable
   And click on "products.costs.dropDownMenu"
   And wait until "products.costs.dropDownMenu.option.new" to be enable
   And wait until "products.costs.dropDownMenu.option.new" to be visible
   And click on "products.costs.dropDownMenu.option.new"

   Then wait until "cost.createCost.popup.unitCost" to be visible
   When wait until "cost.createCost.popup.unitCost" to be enable
   And sendKeys "${unitCost}" into "cost.createCost.popup.unitCost"
   And take a screenshot
   And wait until "cost.createCost.popup.save.button" to be enable
   And click on "cost.createCost.popup.save.button"

   Then wait until "common.toastContainer.link" to be present
   When wait until "common.toastContainer.link" to be enable
   And click on "common.toastContainer.link"

   Then wait until "cost.details.unitCost.edit.button" to be present
   When wait until "cost.details.unitCost.edit.button" to be enable
   And click on "cost.details.unitCost.edit.button"
   Then wait until "cost.details.unitCost.input" to be present
   And assert "cost.details.unitCost.input" value is "${unitCost}"
   And take a screenshot
   And click on "cost.edit.cancel.button"
   And wait until "cost.details.product.link" to be visible
   And click on "cost.details.product.link"

   Then Execute Java Script with data "window.location.reload();"

   Then wait until "products.costs.first.link" to be visible
   And assert "products.costs.first.link" is present
   And take a screenshot



