Feature: Third Challenge

  Scenario: Gods with a name starting with H

    Given Greek gods
    When I execute the method search
    Then return only goods with name starting with H

  Scenario: Goods with large names

    Given Greek gods
    When I execute the method searchGodsWithLargeNames
    Then return only gods with names greather than 10 characters

