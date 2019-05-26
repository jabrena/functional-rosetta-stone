Feature: Fifth Challenge

  Scenario: Return a list of valid web address

    Given Web Address
    When I execute the method search
    Then return only valid web address

