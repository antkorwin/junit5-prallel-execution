package com.antkorwin.parallelexecution;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.engine.TestDescriptor;

/**
 * Created on 20.08.2018.
 *
 * @author Korovin Anatoliy
 */
public class TestSuiteProfilerExtension implements BeforeAllCallback, AfterAllCallback {

    private ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create("antkorwin", "parallel", "test");

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        getStore(context).getOrComputeIfAbsent("startTime",
                                               k -> System.currentTimeMillis());

        TestDescriptor testDescriptor =
                (TestDescriptor) FieldUtils.readField(context.getRoot(),
                                                      "testDescriptor",
                                                      true);

        getStore(context).getOrComputeIfAbsent("testClassCount",
                                               k -> testDescriptor.getChildren().size());
    }

    @Override
    public synchronized void afterAll(ExtensionContext context) throws Exception {

        int testClassCount = (int) getStore(context).get("testClassCount") - 1;

        if (testClassCount <= 0) {

            double duration = ((double) System.currentTimeMillis() -
                               getStartTime(context)) / 1000;

            System.out.println("\n>> Execution Time = " + duration + " sec.\n");
        }

        getStore(context).put("testClassCount", testClassCount);
    }

    private long getStartTime(ExtensionContext context) {
        return (long) getStore(context)
                .get("startTime");
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getRoot().getStore(NAMESPACE);
    }

}
