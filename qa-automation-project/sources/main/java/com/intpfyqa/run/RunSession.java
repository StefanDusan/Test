package com.intpfyqa.run;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadFactory;

public abstract class RunSession implements ThreadFactory {

    /**
     * All contexts of session
     */
    private final Set<IApplicationContext> contexts;
    /**
     * Test method who allocates this session at current time
     */
    private Method currentTestMethod;
    /**
     * Is session can be allocated
     */
    private boolean free = true;
    /**
     * Thread of test method who allocates this test at current time
     */
    private Thread allocatedByThread;
    /**
     * Current application's context where test is working in
     */
    protected IApplicationContext currentContext;

    protected RunSession() {
        currentContext = null;
        contexts = new LinkedHashSet<>();
    }

    private static String uniqueId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Active application's context
     *
     * @return Active application's context
     */
    public IApplicationContext getCurrentContext() {
        return currentContext;
    }

    /**
     * Switch active context
     *
     * @param context Context to be set as active
     */
    public void switchToContext(IApplicationContext context) {
        if (!this.contexts.contains(context)) {
            contexts.add(context);
        }
        this.currentContext = context;
    }

    /**
     * Test method who allocates this session
     *
     * @return Test method who allocates this session
     */
    public @Nullable
    Method getMethod() {
        return currentTestMethod;
    }

    /**
     * Allocate session
     * @param testMethod Method who wants to use this session
     */
    void allocate(Method testMethod) {
        if (!free)
            throw new IllegalStateException("Run session is already allocated");
        this.free = false;
        this.currentTestMethod = testMethod;
        this.allocatedByThread = Thread.currentThread();
    }

    /**
     * Is session can be allocated
     *
     * @return <code>true</code> if there no test who allocates session at current time
     */
    boolean isFree() {
        return free;
    }

    /**
     * Release session and allow other tests to allocate it
     */
    public void free() {
        try {
            beforeSessionFree();
        } finally {
            free = true;
            this.currentTestMethod = null;
            this.allocatedByThread = null;
        }
    }

    /**
     * Checks is session belongs to target thread
     * @param thread Thread to be checked
     * @return <code>true</code> if this session is allocated by this thread or by parent of thread
     * @see #newThread(Runnable)
     */
    public boolean isOfThread(Thread thread) {
        if (isFree()) return false;
        String name = allocatedByThread.getName();
        return thread.getName().startsWith(name);
    }

    /**
     * Creates new thread which will be identified as child of current thread by {@link #isOfThread(Thread)}
     * @param r Runnable for new thread
     * @return New thread
     * @see Thread#Thread(Runnable)
     * @see Thread#Thread(Runnable, String)
     */
    public Thread newThread(Runnable r) {
        return allocatedByThread != null ? new Thread(r, allocatedByThread.getName() + uniqueId()) :
                new Thread(r);
    }

    /**
     * Close session
     */
    void close() {
        beforeSessionClose();
        free();
    }

    /**
     * Execute once session is allocated by some test
     */
    protected abstract void onceSessionAllocated();

    /**
     * Execute before mark this session as free
     */
    protected abstract void beforeSessionFree();

    /**
     * Execute before close the session
     */
    protected void beforeSessionClose() {
        contexts.forEach(IApplicationContext::close);
    }

    /**
     * Switch to default (first) application context
     */
    public void switchToDefaultContext() {
        currentContext = getDefaultContext();
    }

    /**
     * Get default (first) application context
     */
    private IApplicationContext getDefaultContext() {
        for (IApplicationContext c : contexts) {
            return c;
        }
        throw new RuntimeException("Current session has no context.");
    }

    /**
     * Close all application contexts except default
     */
    public void closeAllContextsExceptDefault() {
        contexts.stream()
                .filter(c -> !c.equals(getDefaultContext()))
                .forEach(IApplicationContext::close);
    }
}