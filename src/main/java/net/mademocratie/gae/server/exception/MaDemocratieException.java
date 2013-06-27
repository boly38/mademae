package net.mademocratie.gae.server.exception;

public class MaDemocratieException extends Exception {
    public MaDemocratieException(String msg, Throwable t) {
        super(msg, t);
    }

    public MaDemocratieException(String s) {
        super(s);
    }

    public MaDemocratieException() {
    }
}
