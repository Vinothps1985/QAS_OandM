Feature: Products

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a Price Book using an O&M Product
@product @positive @pricebook
@dataFile:resources/testdata/Products/Create Price Book Using OM Product.csv
@requirementKey=QTM-RQ-23
Scenario: Create Price Book Using OM Product
	
   Given login to salesforce with "${username}" and "${password}"
   And ShrdChangeLoggedInUser "test_o&m_manager"
   #Product name will be the one in the test data file, with added random numbers
   #to prevent duplication
   And create a random number with 6 digits and store it in "randomNumber"
   And ShrdCreateProduct "${productName}-${randomNumber}" "${productDescription}" "${oemManufacturer}" "${activeDataSheet}"

   And take a screenshot

   Then wait until "products.priceBooks.dropDownMenu" to be visible
   Then scroll until "products.priceBooks.dropDownMenu" is visible
   Then click on "products.priceBooks.dropDownMenu"

   Then wait until "products.priceBooks.dropDownMenu.option.addStandardPrice" to be visible
   Then click on "products.priceBooks.dropDownMenu.option.addStandardPrice"

   Then wait until "priceBooks.create.popup.listPrice.input" to be visible
   And wait until "priceBooks.create.popup.listPrice.input" to be enable
   And sendKeys "${listPrice}" into "priceBooks.create.popup.listPrice.input"
   And take a screenshot

   Then click on "priceBooks.create.popup.save.button"
   Then wait until "products.priceBooks.first.link" to be enable
   And scroll until "products.priceBooks.first.link" is visible
   And take a screenshot

   Then click on "products.priceBooks.dropDownMenu"
   And wait until "products.priceBooks.dropDownMenu.option.addToPriceBook" to be visible
   And click on "products.priceBooks.dropDownMenu.option.addToPriceBook"

   Then wait until "priceBooks.addToPriceBook.popup.priceBook.select" to be visible
   And wait until "priceBooks.addToPriceBook.popup.priceBook.select" to be enable
   And select "label=${priceBook}" in "priceBooks.addToPriceBook.popup.priceBook.select"
   And select "label=${currency}" in "priceBooks.addToPriceBook.popup.currency.select"
   And take a screenshot
   And click on "priceBooks.addToPriceBook.popup.next.button"

   Then wait until "priceBooks.create.popup.listPrice.input" to be visible
   And wait until "priceBooks.create.popup.listPrice.input" to be enable
   And clear "priceBooks.create.popup.listPrice.input"
   And sendKeys "${listPrice2}" into "priceBooks.create.popup.listPrice.input"
   And take a screenshot

   Then click on "priceBooks.create.popup.save.button"
   And wait until "priceBooks.details.product.link" to be visible
   And wait until "priceBooks.details.product.link" to be enable
   And take a screenshot
   And assert "priceBooks.details.listPrice" contains the text "${listPrice2}"
   And click on "priceBooks.details.product.link"

   Then wait until "products.priceBooks.second.link" to be enable
   And scroll until "products.priceBooks.second.link" is visible
   And take a screenshot
   And assert "products.priceBooks.second.link" text is "${priceBook}"