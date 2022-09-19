package org.example;

import com.karuslabs.utilitary.type.TypeMirrors;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

class Lint {

    final TypeMirrors types;

    final TypeMirror expectedType;

    Lint(TypeMirrors types) {
        this.types = types;
        this.expectedType = types.type(String.class);
    }

    public boolean lint(Element element) {
        if (!(element instanceof VariableElement)) {
            return false;
        }
        var variable = (VariableElement) element;
        return types.isSameType(expectedType, variable.asType());
    }
}
