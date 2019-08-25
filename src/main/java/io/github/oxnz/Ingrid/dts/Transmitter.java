package io.github.oxnz.Ingrid.dts;

public interface Transmitter<T, R> {

    R transmit(T data);

}
