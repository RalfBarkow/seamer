package com.gregorriegler.seamer.core;

import java.util.List;
import java.util.function.Supplier;

import static java.util.Arrays.asList;

public class SeamRecordingsBuilder<T> {

    private final ArgCandidates argCandidates = new ArgCandidates();
    private final SeamRecorder<T> recorder;

    public SeamRecordingsBuilder(Seam<T> seam, Invocations invocations) {
        this.recorder = new SeamRecorder<T>(seam, invocations);
    }

    public SeamRecordingsBuilder<T> addArgCandidates(int i, Object... candidates) {
        argCandidates.addCandidates(i, asList(candidates));
        return this;
    }

    public SeamRecordingsBuilder<T> addArgCandidates(int i, Supplier<List<Object>> supplier) {
        argCandidates.addCandidates(i, supplier);
        return this;
    }

    public void shuffleArgsAndRecord() {
        List<Object[]> argCombinations = argCandidates.shuffle();
        for (Object[] args : argCombinations) {
            recorder.invokeAndRecord(args);
        }
    }
}
