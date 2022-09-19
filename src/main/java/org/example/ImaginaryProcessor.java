package org.example;

import com.karuslabs.utilitary.AnnotationProcessor;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Set;

@SupportedAnnotationTypes({"*"})
class ImaginaryProcessor extends AnnotationProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment round) {
        var elements = round.getElementsAnnotatedWith(Case.class);
        for (var element : elements) {
            if (element instanceof VariableElement) {
                var variable = (VariableElement) element;
                if (!types.isSameType(variable.asType(), types.type(String.class))) {
                    logger.error(element, "Element is not a string");
                }
            } else {
                logger.error(element, "Element is not a variable");
            }
        }
        return false;
    }
}
