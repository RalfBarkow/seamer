package com.gregorriegler.seamer.core;

@FunctionalInterface
public interface SeamWith2Arguments<A1, A2, R> extends Seam<R> {

    R apply(A1 a1, A2 a2);

    @Override
    @SuppressWarnings("unchecked")
    default R invoke(Object... args) {
        return apply((A1) args[0], (A2) args[1]);
    }
}