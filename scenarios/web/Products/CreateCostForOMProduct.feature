Feature: Products

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a Cost using an O&M Product
@product @positive @cost
@dataFile:resources/testdata/Products/Create Cost For OM Product.csv
@requirementKey=QTM-RQ-23
Scenario: Create Cost for OM Product
	
   Given ShrdLoginToFullCopy "${username}" "${password}"
   Then wait until "applauncher.div" to be present
   When wait until "applauncher.div" to be enable
   And click on "applauncher.div"
   Then wait until "applauncher.input.text" to be present
   When wait until "applauncher.input.text" to be enable
   And clear "applauncher.input.text"
   And wait until "applauncher.input.text" to be enable
   And sendKeys "products" into "applauncher.input.text"
   Then wait until "applauncher.link.products" to be present
   When wait until "applauncher.link.products" to be enable
   And click on "applauncher.link.products"
   Then wait until "products.selectListView" to be present
   When wait until "products.selectListView" to be enable
   And click on "products.selectListView"
   Then wait until "products.selectListView.OMProducts" to be present
   When wait until "products.selectListView.OMProducts" to be enable
   And click on "products.selectListView.OMProducts"
   And wait for 2000 milisec
   Then wait until "products.search.results.first" to be present
   When wait until "products.search.results.first" to be enable
   And click on "products.search.results.first"
   Then wait until "products.costs.dropDownMenu" to be present
   When wait until "products.costs.dropDownMenu" to be enable
   And click on "products.costs.dropDownMenu"
   And wait for 1000 milisec
   And wait until "products.costs.dropDownMenu.option.new" to be enable
   And click on "products.costs.dropDownMenu.option.new"
   Then wait until "cost.createCost.popup.unitCost" to be visible
   When wait until "cost.createCost.popup.unitCost" to be enable
   And sendKeys "123" into "cost.createCost.popup.unitCost"
   And wait until "cost.createCost.popup.save.button" to be enable
   And click on "cost.createCost.popup.save.button"
   Then wait until "common.toastContainer.link" to be present
   When wait until "common.toastContainer.link" to be enable
   And click on "common.toastContainer.link"
   Then wait until "cost.details.unitCost" to be present
   And assert "cost.details.unitCost" text is "$123.00"
   



