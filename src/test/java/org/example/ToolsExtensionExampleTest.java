package org.example;

import com.karuslabs.elementary.junit.Cases;
import com.karuslabs.elementary.junit.Tools;
import com.karuslabs.elementary.junit.ToolsExtension;
import com.karuslabs.elementary.junit.annotations.Inline;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ToolsExtension.class)
@Inline(name = "Samples", source = {
    "import com.karuslabs.elementary.junit.annotations.Case;",
    "",
    "class Samples {",
    "  @Case(\"first\") String first;",
    "  @Case String second() { return \"\";}",
    "}"})
class ToolsExtensionExampleTest {

    Lint lint = new Lint(Tools.typeMirrors());

    @Test
    void lint_string_variable(Cases cases) {
        var first = cases.one("first");
        assertTrue(lint.lint(first));
    }

    @Test
    void lint_method_that_returns_string(Cases cases) {
        var second = cases.get(1);
        assertFalse(lint.lint(second));
    }
}
