package com.intpfyqa.gui.web.selenium.elements.wait;

import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Clock;
import java.time.Duration;

/**
 * Web Element Wait
 */
public class WebElementWait extends FluentWait<IParent> {

    public final static Duration DEFAULT_SLEEP_TIMEOUT = Duration.ofMillis(500);

    /**
     * Wait will ignore instances of NotFoundException that are encountered (thrown) by default in
     * the 'until' condition, and immediately propagate all others.  You can add more to the ignore
     * list by calling ignoring(exceptions to add).
     *
     * @param parentElement The WebElement instance to pass to the expected conditions
     * @param timeOut       The timeout in seconds when an expectation is called
     * @see WebElementWait#ignoring(Class) equals
     */
    public WebElementWait(IParent parentElement, Duration timeOut) {
        this(parentElement, java.time.Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER, timeOut, DEFAULT_SLEEP_TIMEOUT);
    }

    /**
     * Wait will ignore instances of NotFoundException that are encountered (thrown) by default in
     * the 'until' condition, and immediately propagate all others.  You can add more to the ignore
     * list by calling ignoring(exceptions to add).
     *
     * @param parentElement The WebElement instance to pass to the expected conditions
     * @param timeOut       The timeout when an expectation is called
     * @param sleepInMillis The duration in milliseconds to sleep between polls.
     */
    public WebElementWait(IParent parentElement, Duration timeOut, Duration sleepInMillis) {
        this(parentElement, java.time.Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER, timeOut, sleepInMillis);
    }

    /**
     * @param parentElementProvider The WebElement instance to pass to the expected conditions
     * @param clock                 The clock to use when measuring the timeout
     * @param sleeper               Object used to make the current thread go to sleep.
     * @param timeOut               The timeout in seconds when an expectation is
     * @param sleepTimeOut          The timeout used whilst sleeping. Defaults to 500ms called.
     */
    protected WebElementWait(IParent parentElementProvider, Clock clock, Sleeper sleeper, Duration timeOut,
                             Duration sleepTimeOut) {
        super(parentElementProvider, clock, sleeper);
        withTimeout(timeOut);
        pollingEvery(sleepTimeOut);
        ignoring(NotFoundException.class);
        ignoring(NoSuchElementException.class);
        ignoring(TimeoutException.class);
    }
}
