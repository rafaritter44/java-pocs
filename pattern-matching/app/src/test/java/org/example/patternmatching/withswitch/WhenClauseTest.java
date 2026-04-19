package org.example.patternmatching.withswitch;

import org.junit.jupiter.api.Test;

class WhenClauseTest {
    @Test void whenClauseWorks() {
        WhenClause.test("s");
        WhenClause.test("str");
        WhenClause.test(123);
    }
    @Test void whenClauseWorksWithRecords() {
        WhenClause.testSwitch(new WhenClause.R(7));
        WhenClause.testSwitch(new WhenClause.R(3));
    }
}
