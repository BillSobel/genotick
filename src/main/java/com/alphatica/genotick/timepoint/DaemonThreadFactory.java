package com.alphatica.genotick.timepoint;

import java.util.concurrent.ThreadFactory;

class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(@SuppressWarnings("NullableProblems") Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.setName("TimePointExecutor thread " + thread.getId());
        return thread;
    }
}
