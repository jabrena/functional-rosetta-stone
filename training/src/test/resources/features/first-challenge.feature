Feature: First Challenge
  The program receive an Array of objects and the method search
  need to filter books

  Scenario: Simple search

    Given A set of books
    When I execute the method search
    Then return only books with price > 30.0
