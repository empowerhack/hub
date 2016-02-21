Feature: Update Profile

  Scenario: Update profile successfully
    Given I am logged in
    When I go /profile
      And I fill in the field name with TestUserName
      And I fill in the field email with test@email.com
      And I fill in the field availability with 50
      And I check the field private
      And Submit the form form-profile
    Then I see element alert-successful
     And I can not see element alert-error

  Scenario: Update profile unsuccessfully
    Given I am logged in
    When I go /profile
      And I fill in the field name with Test
      And I fill in the field email with test@email.com
      And I fill in the field availability with 50
      And I check the field private
      And Submit the form form-profile
    Then I see element alert-error
     And I can not see element alert-successful
