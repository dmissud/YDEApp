package org.yde.ydeapp.domain;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * To run cucumber test
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
    plugin = {"pretty", "html:target/cucumber-reports.html"})
class DomainCucumberTest {

}
