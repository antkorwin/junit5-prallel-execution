package com.antkorwin.parallelexecution;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Created on 20.08.2018.
 * <p>
 * TODO: replace on javadoc
 *
 * @author Korovin Anatoliy
 */
@ExtendWith(TestSuiteProfilerExtension.class)
class SecondTest {

    @Test
    void name() throws InterruptedException {
        Thread.sleep(10000);
    }
}
