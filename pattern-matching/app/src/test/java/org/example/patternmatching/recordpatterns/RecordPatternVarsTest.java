package org.example.patternmatching.recordpatterns;

import org.junit.jupiter.api.Test;

class RecordPatternVarsTest {
    @Test void recordPatternVarsWork() {
        RecordPatternVars.printAngleFromXAxis(new RecordPatternVars.Point(1.0, 2.0));
        RecordPatternVars.printBoxContents(new RecordPatternVars.Box<>("str"));
        RecordPatternVars.recordInference(new RecordPatternVars.MyPair<>("str", 3));
    }
}
