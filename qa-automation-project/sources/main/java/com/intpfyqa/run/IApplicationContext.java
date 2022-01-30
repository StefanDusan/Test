package com.intpfyqa.run;

import com.intpfyqa.logging.ITakeSnapshot;

public interface IApplicationContext {

    /**
     * Contract name of the application
     *
     * @return Contract name of the application
     */
    String getAlias();

    /**
     * Current test environment where test is executed in
     *
     * @return Environment name
     */
    String getEnvironmentName();

    /**
     * TakeSnapshot of this application if possible
     * @return TakeSnapshot or <code>null</code> if this application does not support snapshots
     */
    ITakeSnapshot getTakeSnapshot();

    /**
     * Close the context (destroy any reserved resources, execution is completed)
     */
    void close();
}
