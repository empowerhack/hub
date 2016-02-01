Feature: Guest viewing public pages

  Scenario: Landing page show correct information
    Given I am not logged in
    When I go to /
    Then I see the text Homepage
      And I close the browser

  Scenario: Home page redirects to landing page
    Given I am not logged in
    When I go to /home
    Then I get redirected to /
      And I see the text Homepage
      And I close the browser
