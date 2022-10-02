package com.example.notempty.processor;

import com.karuslabs.elementary.Results;
import com.karuslabs.elementary.junit.JavacExtension;
import com.karuslabs.elementary.junit.annotations.Classpath;
import com.karuslabs.elementary.junit.annotations.Options;
import com.karuslabs.elementary.junit.annotations.Processors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.tools.Diagnostic;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.example.notempty.processor.NotEmptyProcessor.INITIALIZER_EMPTY;
import static com.example.notempty.processor.NotEmptyProcessor.IS_NOT_A_MEMBER_VARIABLE;
import static com.example.notempty.processor.NotEmptyProcessor.IS_NOT_OF_STRING_TYPE;
import static com.example.notempty.processor.NotEmptyProcessor.NO_CONSTANT_INITIALIZER;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Demonstrates black-box testing with {@link JavacExtension}:<br>
 * Pass Java code to the Java compiler, inspect diagnostic messages.
 * <p>
 * This is low-granularity: One set of messages for all the source code.<br>
 * Meaning you'll want to write tiny source code separately for each test,
 * which can get awkward.
 * <p>
 * {@link WhiteBoxTest} demonstrates an alternate API
 */
// This is a black-box test, so we need JavacExtension:
@ExtendWith(JavacExtension.class)
// We don't expect warnings, so let JavacExtension pass -Werror to the Java
// compiler.
@Options("-Werror")
// Tell JavacExtension which annotation processor(s) to pass to the Java
// compiler.
@Processors({NotEmptyProcessor.class})
// A common Java source file to pass to the Java compiler for each test case.
// Per-test @Classpath annotations go in addition to the common file.
// We don't use that for this demo, just mentioning that the option is there.
// @Classpath("com.example.notempty.testdata.XXX")
public class BlackBoxTest {

    @Test
    @Classpath("com.example.notempty.testdata.ValidCase")
        // @Classpath nicely separates the code into a separate Java file, which
        // can be supported by the IDE.<br>
        // The downside is that it puts the source code in the resource directory
        // and makes it harder to correlate test logic and sources used.<br>
        // The opposite trade-off would be the @Inline annotation: Not checked by
        // the IDE, but your Java source code is near to the test that uses it.
        // (Accidentally, BlackBoxTest uses @Classpath and WhiteBoxTest uses
        // @Inline, which is probably the reverse of what you'd do in practice.)
    void testValidCase(Results results) {
        assertErrorCount(results, x -> true, 0);
    }

    @Test
    @Classpath("com.example.notempty.testdata.NotAMemberVariable")
    void testNotAMemberVariable(Results results) {
        assertErrorCount(
            results,
            x -> x.getMessage(Locale.ENGLISH).contains(IS_NOT_A_MEMBER_VARIABLE),
            1);
    }

    @Test
    @Classpath("com.example.notempty.testdata.NotOfStringType")
    void testNotOfStringType(Results results) {
        assertErrorCount(
            results,
            x -> x.getMessage(Locale.ENGLISH).contains(IS_NOT_OF_STRING_TYPE),
            1);
    }

    @Test
    @Classpath("com.example.notempty.testdata.NonConstantInitializer")
    void testNonConstantInitializer(Results results) {
        assertErrorCount(
            results,
            x -> x.getMessage(Locale.ENGLISH).contains(NO_CONSTANT_INITIALIZER),
            1);
    }

    @Test
    @Classpath("com.example.notempty.testdata.NoInitializer")
    void testNoInitializer(Results results) {
        assertErrorCount(
            results,
            x -> x.getMessage(Locale.ENGLISH).contains(NO_CONSTANT_INITIALIZER),
            1);
    }

    @Test
    @Classpath("com.example.notempty.testdata.InitializedWithNull")
    void testInitializedWithNull(Results results) {
        assertErrorCount(
            results,
            x -> x.getMessage(Locale.ENGLISH).contains(NO_CONSTANT_INITIALIZER),
            1);
    }

    @Test
    @Classpath("com.example.notempty.testdata.InitializedWithEmptyString")
    void testInitializedWithEmptyString(Results results) {
        assertErrorCount(
            results,
            x -> x.getMessage(Locale.ENGLISH).contains(INITIALIZER_EMPTY),
            1);
    }

    private static void assertErrorCount(Results results, Predicate<Diagnostic<?>> pred, int expected) {
        assertEquals(expected,
            results.diagnostics.stream()
                .filter(x -> x.getKind() == Diagnostic.Kind.ERROR)
                .filter(pred)
                .count(),
            () -> """
                      Compiler error count deviates from expectations.
                      All messages, including warnings and possibly unrelated errors, are:
                      """ +
                  results.diagnostics.stream()
                      .map(x -> "    " + x.toString() + "\n")
                      .collect(Collectors.joining())
        );
    }
}
