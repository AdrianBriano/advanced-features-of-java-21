package com.example;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.ValueLayout;

import static java.lang.foreign.Arena.*;

public class Main {

    public static void main(String[] args) throws Throwable {
        try (Arena arena = ofConfined()) {
            final var linker = Linker.nativeLinker();
            final var symbolLookup = linker.defaultLookup();

            final var memorySegment = symbolLookup.find("strlen").orElseThrow();

            final var functionDescriptor = FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS);

            final var methodHandle = linker.downcallHandle(memorySegment, functionDescriptor);

            final var segmentAllocator = arena.allocateFrom("Hello world");

            final var result = (long) methodHandle.invokeExact(segmentAllocator);

            System.out.println(result);
        }
    }

}
