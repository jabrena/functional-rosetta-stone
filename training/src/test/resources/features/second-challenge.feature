Feature: Second Challenge

  Scenario: Novels in Physics

    Given Novel Prizes
    When I execute the method search
    Then return only winners in the physics speciality

  Scenario: Novels in Physics in 2018

    Given Novel Prizes
    When I search
    Then return only winners in the physics speciality

