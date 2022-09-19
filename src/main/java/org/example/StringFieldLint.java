package org.example;

import com.karuslabs.utilitary.Logger;
import com.karuslabs.utilitary.type.TypeMirrors;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

class StringFieldLint extends AbstractProcessor {

    Elements elements;

    TypeMirrors types;

    Logger logger;

    @Override
    public void init(ProcessingEnvironment environment) {
        super.init(environment);
        elements = environment.getElementUtils(); // (1)
        types = new TypeMirrors(elements, environment.getTypeUtils()); // (2)
        logger = new Logger(environment.getMessager()); // (3)
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment round) {
        var elements = round.getElementsAnnotatedWith(Case.class); // (4)
        for (var element : elements) {
            if (!(element instanceof VariableElement)) {
                logger.error(element, "Element is not a variable"); // (5)
                continue;
            }
            var variable = (VariableElement) element;
            if (!types.isSameType(variable.asType(), types.type(String.class))) { // (6) (7) (8)
                logger.error(element, "Element is not a string"); // (9)
                continue;
            }
        }
        return false;
    }
}




