#Author: camiloeduardo.ortiz@gmail.com

@review
Feature: Assess the review functionality
  Test the review functions
  
  Background:
  	Given An "User" with valid credentials
    And The user is logged in

  Scenario: Create a review
    Given I am in an users profile page
    When I hover and click on the four star rating on a profile
    Then I should see the review page
    When I fill all the review data and submit it
    Then I should see a confirmation window
    And If I go to my profile I should see my review
