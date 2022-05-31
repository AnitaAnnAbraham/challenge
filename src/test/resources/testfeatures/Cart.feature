@All
@CartFeatures
Feature: User actions in shopping cart

  @EditCart
  Scenario: Editing shopping list from cart
    Given User navigates to greenkart home page
    Then User is taken to home page
    And User adds several products with random quantities
    |product|
    |Brocolli|
    |Cucumber|
    |Raspberry|
    |Pista|
    And User navigates to cart
    Then User should have option to remove unwanted items from cart
    |product|
    |Raspberry|
    |Cucumber|
    Then User should be able to proceed with checkout
    
    