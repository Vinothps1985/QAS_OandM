Feature: Products

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a product of record type O&M Product
@product @positive

Scenario: Create OM Product
	
   Given ShrdLoginToFullCopy 
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
   And sendKeys "${currentTime}" into "products.createProduct.popup.name.input"
   And wait until "products.createProduct.popup.family.input" to be enable
   And click on "products.createProduct.popup.family.input"
   And wait until "products.createProduct.popup.family.input.option.Tools" to be enable
   And click on "products.createProduct.popup.family.input.option.Tools"
   And wait until "products.createProduct.popup.save.button" to be enable
   And click on "products.createProduct.popup.save.button"
   Then wait until "common.toastContainer" to be present
   And assert "products.details.name" text is "${currentTime}"
   



