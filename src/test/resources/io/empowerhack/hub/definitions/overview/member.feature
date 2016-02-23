Feature: Members list

  Scenario: Only public Members are displayed
    Given I am not logged in
      And There are public Members in the application
      And There are private Members in the application
    When I go /
    Then I see elements user-public
     And I can not see elements user-private

  Scenario: Only public and private Members are displayed
    Given I am logged in
      And There are public Members in the application
      And There are private Members in the application
    When I go /
    Then I see elements user-public
