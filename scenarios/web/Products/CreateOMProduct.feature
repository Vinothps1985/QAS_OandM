Feature: Products

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a product of record type O&M Product
@product @positive @smoke
@dataFile:resources/testdata/Products/Create OM Product.csv

Scenario: Create OM Product
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   Then close all open web tabs
   #Product name will be the one in the test data file, with added random numbers
   #to prevent duplication
   And create a random number with 6 digits and store it in "randomNumber"
   And ShrdCreateProduct "${productName}-${randomNumber}" "${productDescription}" "${oemManufacturer}" "${activeDataSheet}"
   And assert "products.details.name" text is "${generated_productName}"
   And take a screenshot