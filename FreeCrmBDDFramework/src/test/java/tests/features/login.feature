Feature: Free CRM Login Feature
  Scenario: Free CRM Login Test Scenario
    Given I have chosen to log in
    And Login page is opened
    When I enter email address and password
    And I click on login button
    Then I should see a home page