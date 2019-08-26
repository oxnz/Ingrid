package io.github.oxnz.Ingrid.dts;

public class HttpExecutionResult {
    private final boolean succ;
    private final String msg;
    public HttpExecutionResult(boolean succ, String msg) {
        this.succ = succ;
        this.msg = msg;
    }

    public boolean succeeded() {
        return succ;
    }

    public String message() {
        return msg;
    }
}
