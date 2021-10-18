Feature: Products

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a product of record type O&M Product
@product @positive @smoke
@dataFile:resources/testdata/Products/Create OM Product.csv
@requirementKey=QTM-RQ-23
Scenario: Create OM Product
	
   Given ShrdLoginToFullCopy "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_ops_center_operator"
   And ShrdLaunchApp "products"
   Then wait until "products.create.new.button" to be present
   When wait until "products.create.new.button" to be enable
   And click on "products.create.new.button"
   Then wait until "products.createProduct.popup.recordType.OM.input" to be visible
   Then wait for 3000 milisec
   When wait until "products.createProduct.popup.recordType.OM.input" to be enable
   And click on "products.createProduct.popup.recordType.OM.input"
   And wait until "products.createProduct.popup.next.button" to be enable
   And click on "products.createProduct.popup.next.button"
   And ShrdGetVarCurrentTime 
   And wait until "products.createProduct.popup.name.input" to be enable
   And click on "products.createProduct.popup.name.input"
   And wait until "products.createProduct.popup.name.input" to be enable
   And sendKeys "${currentTime}" into "products.createProduct.popup.name.input"
   And wait until "products.createProduct.popup.family.input" to be enable
   And click on "products.createProduct.popup.family.input"
   And wait until "products.createProduct.popup.family.input.option.Tools" to be enable
   And click on "products.createProduct.popup.family.input.option.Tools"
   And wait until "products.createProduct.popup.productDescription.input" to be enable
   And sendKeys "${productDescription}" into "products.createProduct.popup.productDescription.input"
   And wait until "products.createProduct.popup.oemManufacturer.input" to be enable
   And sendKeys "${oemManufacturer}" into "products.createProduct.popup.oemManufacturer.input"
   And wait until "products.createProduct.popup.activeDataSheet.input" to be enable
   And sendKeys "${activeDataSheet}" into "products.createProduct.popup.activeDataSheet.input"
   And wait until "products.createProduct.popup.save.button" to be enable
   And click on "products.createProduct.popup.save.button"
   Then wait until "common.toastContainer" to be present
   And assert "products.details.name" text is "${currentTime}"
   



