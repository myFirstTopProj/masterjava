package ru.javaops.web.handler;


import org.slf4j.event.Level;

public class SoapClientLoggingHandler extends SoapLoggingHandler {
    public SoapClientLoggingHandler(Level loggingLevel) {
        super(loggingLevel);
    }

    @Override
    protected boolean isRequest(boolean isOutbound) {
        return isOutbound;
    }
}