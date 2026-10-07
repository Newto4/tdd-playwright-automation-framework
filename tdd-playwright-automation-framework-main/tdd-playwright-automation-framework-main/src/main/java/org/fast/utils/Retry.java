package org.fast.utils;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.lang.reflect.Method;

public class Retry implements InvocationInterceptor {

//    private final int MAX_RETRY_COUNT = System.getProperty("RETRY_COUNT") != null ? Integer.parseInt(System.getProperty("RETRY_COUNT")) : 2;
//
//    @Override
//    public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
//        int counter = 0;
//        while (true) {
//            try {
//                invocation.proceed();
//                return;
//            } catch (Throwable t) {
//                if (counter > MAX_RETRY_COUNT) {
//                    throw t;
//                }
//                counter++;
//            }
//            System.out.println("Retrying test: "+invocationContext.getExecutable().getName() + " | Attempt "+counter);
//        }
//
//    }
}
