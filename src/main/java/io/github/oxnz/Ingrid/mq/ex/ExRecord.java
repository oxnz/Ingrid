package io.github.oxnz.Ingrid.mq.ex;

public class ExRecord {
    private final long id;

    public ExRecord(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ExRecord{" +
                "id=" + id +
                '}';
    }
}
