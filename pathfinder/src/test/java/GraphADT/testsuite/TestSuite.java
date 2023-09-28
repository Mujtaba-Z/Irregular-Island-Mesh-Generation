package GraphADT.testsuite;

import GraphADT.tests.AlgorithmTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Test Suite")
@SelectClasses({AlgorithmTest.class})
public class TestSuite {
}
