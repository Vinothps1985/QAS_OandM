Feature: Products

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a product of record type O&M Product
@product @positive @smoke
@dataFile:resources/testdata/Products/Create OM Product.csv
@requirementKey=QTM-RQ-23
Scenario: Create OM Product
	
   Given ShrdLogin "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_ops_center_operator"
   And ShrdCreateProduct "ANY" "${productDescription}" "${oemManufacturer}" "${activeDataSheet}"
   And assert "products.details.name" text is "${generated_productName}"
   And take a screenshot
   And wait for 2000 milisec