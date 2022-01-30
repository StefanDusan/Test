package com.intpfyqa.test_rail;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.ArrayList;
import java.util.List;

public class TestFilterInterceptor implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        if (!TestRailConfig.isReportTestRailAllowed()) return methods;

        List<IMethodInstance> result = new ArrayList<>();
        for (IMethodInstance method : methods) {
            TestRailCase testRailCase = method.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TestRailCase.class);
            if (null == testRailCase) continue;
            if (TestRailClient.getTestIdByAutomationRef(TestRailConfig.getTestRailRunId(), testRailCase.value()) != null) {
                result.add(method);
            }
        }
        return result;
    }
}
