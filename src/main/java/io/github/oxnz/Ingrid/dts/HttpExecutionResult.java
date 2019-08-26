package io.github.oxnz.Ingrid.dts;

public class HttpExecutionResult {
    public boolean succeeded() {
        return succ;
    }

    private final boolean succ;
    private final String msg;

    public HttpExecutionResult(boolean succ, String msg) {
        this.succ = succ;
        this.msg = msg;
    }

    public String message() {
        return msg;
    }
}
