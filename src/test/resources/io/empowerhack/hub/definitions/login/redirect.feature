Feature: Successful logins redirect to request page

  Scenario: After a successful login I will be redirected to request page
    Given I go /
    When I am logged in
    Then I get redirected to the / page
