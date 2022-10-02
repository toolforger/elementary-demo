package com.example.notempty.processor;

import com.example.notempty.annotation.NotEmpty;
import com.karuslabs.utilitary.AnnotationProcessor;
import com.karuslabs.utilitary.type.TypeMirrors;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Set;

import static javax.lang.model.SourceVersion.RELEASE_17;

/**
 * The annotation processor that implements the semantics behind
 * {@link NotEmpty @NotEmpty}.
 */
@SupportedAnnotationTypes({"*"})
@SupportedSourceVersion(RELEASE_17)
public class NotEmptyProcessor extends AnnotationProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment round) {
        var elements = round.getElementsAnnotatedWith(NotEmpty.class);
        for (var element : elements) {
            if (!isAMemberVariable(element)) {
                logger.error(element, IS_NOT_A_MEMBER_VARIABLE);
                continue;
            }
            VariableElement variable = (VariableElement) element;
            if (!isOfStringType(types, variable)) {
                logger.error(element, IS_NOT_OF_STRING_TYPE);
                continue;
            }
            if (!isInitializedWithAConstant(variable)) {
                logger.error(element, NO_CONSTANT_INITIALIZER);
                continue;
            }
            if (!isInitializedWithANonemptyConstant(variable)) {
                logger.error(element, INITIALIZER_EMPTY);
                continue;
            }
        }
        return false;
    }

    // This demo code was written with each condition forked out into a separate function, so it can test each expected
    // failure mode in a separate test.
    // This is totally overblown for such simple conditions as the ones we use here, and done only to demonstrate how to
    // make conditions testable in this way.

    static boolean isAMemberVariable(Element element) {
        // Do NOT use `element instanceof VariableElement` here!
        // The authors seem to consider the runtime class of an `Element` to be a pretty accidental implementation
        // detail, and recommend using `getKind()` instead; as an added bonus, it is granular enough to distinguish all
        // the different kinds of Java code elements, including whether it's an instance field or something else.
        return element.getKind() == ElementKind.FIELD;
    }

    static final String IS_NOT_A_MEMBER_VARIABLE = "Code element is not a member variable.";

    static boolean isOfStringType(TypeMirrors types, VariableElement variable) {
        return types.isSameType(variable.asType(), types.type(String.class));
    }

    static final String IS_NOT_OF_STRING_TYPE = "Variable is not of type String.";

    static boolean isInitializedWithAConstant(VariableElement variable) {
        return variable.getConstantValue() != null;
    }

    static final String NO_CONSTANT_INITIALIZER = "Variable is not initialized with a constant.";

    static boolean isInitializedWithANonemptyConstant(VariableElement variable) {
        return !StringUtils.isEmpty(variable.getConstantValue().toString());
    }

    static final String INITIALIZER_EMPTY = "Variable is not initialized with a nonempty constant.";
}
