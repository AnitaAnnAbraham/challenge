package testRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions( 
plugin = {"pretty","html:target/cucumber-report/cucumber.html"},
features = {"src/test/resources/testfeatures" }, 
glue = {"stepDefinitions","hooks"},
tags= "@All")
public class TestRunner {

}
