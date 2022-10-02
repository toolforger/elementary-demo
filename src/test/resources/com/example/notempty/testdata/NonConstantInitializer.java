package com.example.notempty.testdata;

import com.example.notempty.annotation.NotEmpty;

import java.time.Instant;

public class NonConstantInitializer {

    @NotEmpty
    public final String emptyField = Instant.now().toString();
}
