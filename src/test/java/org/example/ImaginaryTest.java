package org.example;

import com.karuslabs.elementary.Results;
import com.karuslabs.elementary.junit.JavacExtension;
import com.karuslabs.elementary.junit.annotations.Classpath;
import com.karuslabs.elementary.junit.annotations.Options;
import com.karuslabs.elementary.junit.annotations.Processors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(JavacExtension.class)
@Options("-Werror")
@Processors({ImaginaryProcessor.class})
@Classpath("org.example.testdata.ValidCase")
class ImaginaryTest {

    @Test
    void process_string_field(Results results) {
        assertEquals(1, results.find().errors().count());
    }

    @Test
    @Classpath("org.example.testdata.InvalidCase")
    void process_int_field(Results results) {
        assertEquals(1, results.find().errors().contains("Element is not a string").count());
    }
}
