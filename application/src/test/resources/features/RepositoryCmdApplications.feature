# Created by Dan at 09/07/2020
Feature: management of commands of applications
 # Enter feature description here

  Scenario: Referencing a list of new application
    Given a new commande with <codeApplication> that not exist in repository
      | codeApplication  |
      | AP00001          |
      | AP00002          |
      | AP00003          |
      | AP00004          |

    When The cmd enrich the repository with applications with this data
      | codeApplication  | shortDescription   | longDescription               | uid        |firstName   |lastName  |
      | AP00001          | Test App           | Long description for Test app | 123456     |John        |Doe       |
      | AP00002          | Test App 1         | Long description for Test app | 123455     |Johna       |Doea      |
      | AP00003          | Test App  2        | Long description for Test app | 123454     |Johnb       |Doeb      |
      | AP00004          | Test App    3      | Long description for Test app | 123453     |Johnc       |Doec      |

    Then a news applications are in the repository with <codeApplication>
      | codeApplication  |
      | AP00001          |
      | AP00002          |
      | AP00003          |
      | AP00004          |



