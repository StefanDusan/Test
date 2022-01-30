package com.intpfyqa.run;

import org.testng.internal.thread.TestNGThreadFactory;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadFactory;

public class RunSessions {

    private static final Set<RunSession> sessions = new HashSet<>();

    public static synchronized RunSession current() {
        synchronized (sessions) {
            for (RunSession session : sessions) {
                if (session.isOfThread(Thread.currentThread())) {
                    return session;
                }
            }
        }

        throw new IllegalStateException("RunSession is not created!");
    }

    public static synchronized boolean hasSession(Method method) {
        return getSession(method) != null;
    }

    public static synchronized RunSession getSession(Method method) {
        synchronized (sessions) {
            for (RunSession session : sessions) {
                if (null != session.getMethod() && session.getMethod().equals(method)) {
                    return session;
                }
            }
        }

        return null;
    }

    public synchronized static ThreadFactory threadFactory() {
        try {
            return current();
        } catch (IllegalStateException ignore) {
            return new TestNGThreadFactory("method=RunSessions");
        }
    }

    public static void releaseAll() {
        synchronized (sessions) {
            sessions.forEach(s -> {
                try {
                    s.close();
                } catch (Throwable ignore) {
                }
            });
            sessions.clear();
        }
    }

    public static void close(RunSession session) {
        synchronized (sessions) {
            if (!sessions.contains(session)) return;
            session.close();
            sessions.remove(session);
        }
    }

    public static Optional<RunSession> allocateAnyFreeSession(Method method) {
        Optional<RunSession> freeSession;
        synchronized (sessions) {
            freeSession = sessions.stream().filter(RunSession::isFree).findFirst();
            freeSession.ifPresent(runSession -> runSession.allocate(method));
        }
        freeSession.ifPresent(RunSession::onceSessionAllocated);
        return freeSession;
    }

    public static void registerSession(RunSession session, Method method) {
        synchronized (sessions) {
            sessions.add(session);
            session.allocate(method);
        }
        session.onceSessionAllocated();
    }
}
