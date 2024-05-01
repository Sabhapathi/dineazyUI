Feature: Admin section to Manage any organization Menu Feature
  In order to Manage organization menu
  As an Admin user
  I should be able to perform different menu management actions

@positive
Scenario: Cancel the Hotel Menu creation on Dineazy
  Given user login to Dineazy
  When user navigates to "Admin" application menu
  And user navigates to "Menu" option
  And user hides side menu
  Then user should go to add new menu
  When user clicks "Cancel"
  Then user should goes back to "Menu" page


  @positive
  Scenario: Verify the Hotel Menu creation on Dineazy
    Given user login to Dineazy
    When user navigates to "Admin" application menu
    And user navigates to "Menu" option
    And user hides side menu
    Then user should go to add new menu
    Given user selects "Inventory Type" as "Non Veg"
    And user enters "Code" as "123"
    And user enters "Name" as "ae"
    And user enters "Price" as "123"
    And user selects "Tax" as "GST"
    And user selects "Category" as "Lunch"
    And user selects "Sub Category" as "Desserts"
    When user clicks "Save"
#    Then user should goes back to "Menu" page
    And user should see error "Inventory already exists with same name or code."
#    And user clean up "ae" menu item


