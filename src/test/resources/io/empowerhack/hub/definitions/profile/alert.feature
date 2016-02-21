Feature: Alert to update my profile is displayed until it is updated

  Scenario: Initial login
    Given I have not logged in before
    When I am logged in
    Then I see element alert-info

  Scenario: First login but not updated profile
    Given I am logged in
    When I go /profile
    Then I see element alert-info
      And I fill in the field name with TestName
      And Submit the form form-profile
    Then I can not see element alert-info
      And I see element alert-successful

  Scenario: Second login with updated profile
    Given I am logged in
    When I go /
    Then I can not see element alert-info
