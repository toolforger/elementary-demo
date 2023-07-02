package com.example.notempty.processor;

import com.karuslabs.elementary.junit.Labels;
import com.karuslabs.elementary.junit.Tools;
import com.karuslabs.elementary.junit.ToolsExtension;
import com.karuslabs.elementary.junit.annotations.Inline;
import com.karuslabs.utilitary.type.TypeMirrors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.lang.model.element.VariableElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ToolsExtension.class)
// I suspect that tests running under ToolsExtension have no easy way to access
// Java compiler error messages like those under JavacExtension have (which is
// shown in BlackBoxTest).
// Anyway, this test showcases access to javax.lang.model.element.Element data,
// i.e. the nodes of a java program AST; so this form of test is particularly
// suited for inspecting Java code, checking how they are set up, and checking
// that an annotation processor has the right decision-making for them. This
// allows for a style where you can write new Java code to test an annotation
// processor against, at the drop of a hat.
// Or you can set up intentionally-invalid cases and check that it triggers the
// right decisions in the annotation processor - this is what the below @Inline
// code does.
@Inline(name = "InvalidCases", source =
    """
        import com.example.notempty.annotation.NotEmpty;
        import com.karuslabs.elementary.junit.annotations.Label;

        class TestSubject {

            @Label("validCase")
            @NotEmpty
            public final String field = "Hello World!";

            @Label("notAMemberVariable")
            public void foo(@NotEmpty String str) {
            }

            @Label("notOfStringType")
            @NotEmpty
            public final int intField = 0;

            @Label("nonConstantInitializer")
            @NotEmpty
            public final String nonConstantlyInitializedField = "HELLO".toLowerCase();

            @Label("noInitializer")
            @NotEmpty
            public String noInitializerField;

            @Label("initializedWithNull")
            @NotEmpty
            public final String nullStringField = null;

            @Case("initializedWithEmptyString")
            @NotEmpty
            public final String emptyField = "";
        }
        """
)
public class WhiteBoxTest {

    private final TypeMirrors types = Tools.typeMirrors();

    @Test
    public void testValidCase(Labels labels) {
        VariableElement element = (VariableElement) labels.get("validCase");
        assertTrue(NotEmptyProcessor.isAMemberVariable(element));
        assertTrue(NotEmptyProcessor.isOfStringType(types, element));
        assertTrue(NotEmptyProcessor.isInitializedWithAConstant(element));
        assertTrue(NotEmptyProcessor.isInitializedWithANonemptyConstant(element));
    }
}
