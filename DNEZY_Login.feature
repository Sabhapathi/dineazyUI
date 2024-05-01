Feature: Login Feature
  In order to perform successful login
  As a user
  I have to enter correct username and password

  @positive
  Scenario: Login to Dineazy
    Given user is on Dineazy website
    When user enters "girish.pr@itprofound.com" and "Test@1234"
    And user login
    Then user should see Home page

  @negative
  Scenario Outline: Dineazy Login Negative tests
    Given user is on Dineazy website
    When user enters "<username>" and "<password>"
    And user login
    Then user should see "<error>"

  Examples:
  | username                | password            |error                 |
  | incorrectemail@abc.com  | ioiuoi              |User not found        |
  | girish.pr@itprofound.com| incorrect password  |Invalid credentials.  |
  | abc@xyz.com             | xyz                 |User not found        |