Feature: Guest viewing public pages

  Scenario: Homepage is public
    Given I am not logged in
    When I go /
    Then I see the text Members
      And I see the text Login

  Scenario: Login page is public
    Given I am not logged in
    When I go /login
    Then I see the text Login with
