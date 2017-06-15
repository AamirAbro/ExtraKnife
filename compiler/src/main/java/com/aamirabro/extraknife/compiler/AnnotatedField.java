package com.aamirabro.extraknife.compiler;

import com.aamirabro.extraknife.InjectExtra;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

/**
 * Created by aamirabro on 27/12/2016.
 */
public class AnnotatedField {
    public final String variableName;
    public final String key;
    public final Element annotatedFiled;
    public final TypeKind type;

    public AnnotatedField(Element annotatedFiled) {
        this.variableName = annotatedFiled.getSimpleName().toString();
        this.annotatedFiled = annotatedFiled;
        this.key = annotatedFiled.getAnnotation(InjectExtra.class).value();
        this.type = annotatedFiled.asType().getKind();
    }

    @Override
    public String toString() {
        return "AnnotatedField{" +
                "variableName='" + variableName + '\'' +
                ", key='" + key + '\'' +
                ", annotatedFiled=" + annotatedFiled +
                ", type=" + type +
                '}';
    }
}
