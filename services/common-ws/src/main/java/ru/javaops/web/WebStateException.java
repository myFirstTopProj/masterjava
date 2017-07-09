package ru.javaops.web;


import com.google.common.base.Throwables;
import ru.javaops.masterjava.ExceptionType;

import javax.xml.ws.WebFault;

@WebFault(name = "webStateException", targetNamespace = "http://common.javaops.ru/")
public class WebStateException extends Exception {
    private FaultInfo faultInfo;

    public WebStateException(String message, FaultInfo faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public WebStateException(Exception e) {
        this(ExceptionType.SYSTEM, e);
    }

    public WebStateException(ExceptionType type, Throwable cause) {
        super(Throwables.getRootCause(cause).toString(), cause);
        this.faultInfo = new FaultInfo(type);
    }

    public FaultInfo getFaultInfo() {
        return faultInfo;
    }

    @Override
    public String toString() {
        return faultInfo.toString() + '\n' + super.toString();
    }
}
