Feature: Members list

  Scenario: Only public Members are displayed
    Given I am not logged in
      And There are no members
      And There are public 5 Members in the application
      And There are private 5 Members in the application
    When I go /
    Then I see 5 elements of card

  Scenario: Only public and private Members are displayed
    Given I am logged in
      And There are no members
      And There are public 5 Members in the application
      And There are private 5 Members in the application
    When I go /
    Then I see 10 elements of card
