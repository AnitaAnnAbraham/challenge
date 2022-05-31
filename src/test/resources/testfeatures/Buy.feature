@All
@PurchaseFeatures
Feature: User making valid purchases

  @ValidPurchase
  Scenario: Adding desired quantities of multiple items
    Given User navigates to greenkart home page
    Then User is taken to home page
    And User adds several products with required quantities
    |product|quantity|
    |Brocolli|2|
    |Cucumber|5|
    |Orange|1|
    |Tomato|8|
    |Raspberry|10|
    |Pista|20|
    And User navigates to cart
    Then User should be able to proceed with checkout
    And Item list and quanity should be reflected in cart
    And User should be able to proceed with order placement
    Then User should be taken to terms and conditions page
    And User selects country as "India"
    And User checks terms and conditions
    Then User should be able to complete order placement
    And Order successful message should be displayed
    
    @InvalidCodePurchase
  	Scenario: User placing succesful order but applying invalid promo code
    Given User navigates to greenkart home page
    Then User is taken to home page
    And User adds several products with required quantities
    |product|quantity|
    |Brocolli|2|
    |Cucumber|5|
    |Orange|1|
    |Tomato|8|
    |Raspberry|10|
    |Pista|20|
    And User navigates to cart
    Then User should be able to proceed with checkout
    And Item list and quanity should be reflected in cart
    When User applies invalid promo code as "XXXX"
    Then User should get invalid promo code error
    But User should be able to proceed with order placement