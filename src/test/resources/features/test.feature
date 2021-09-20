Feature: Operation Steps Tests
  
  Scenario: list of Operations
    Given Operations register on database
    When the are required with the stock id '<stockId>'
    Then the response is the http status <code>
 
    Examples: 
      | stockId  | code | 
      | vale5   |   200 |
      | petr4   |  200 |  
      | vale2   |   404 | 
      | pet     |  404 |  
