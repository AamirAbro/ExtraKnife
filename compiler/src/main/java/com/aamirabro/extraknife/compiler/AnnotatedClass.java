package com.aamirabro.extraknife.compiler;

import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

class AnnotatedClass {
    public final String className;
    public final String packageName;
    public final List<AnnotatedField> fields;
    public final TypeElement typeElement;

    public AnnotatedClass(TypeElement typeElement, List<AnnotatedField> fields) {
        this.className = typeElement.getSimpleName().toString();
        this.fields = fields;
        this.typeElement = typeElement;
        this.packageName = generatePackageName(typeElement);
    }

    private String generatePackageName (TypeElement typeElement) {
        String fullName = typeElement.getQualifiedName().toString();
        return fullName.substring(0, fullName.lastIndexOf('.'));
    }

    public void addField (AnnotatedField annotatedField) {
        fields.add(annotatedField);
    }

    public TypeMirror getType() {
        return typeElement.asType();
    }

    @Override
    public String toString() {
        return "AnnotatedClass{" +
                "className='" + className + '\'' +
                ", packageName='" + packageName + '\'' +
                ", fields=" + fields +
                ", typeElement=" + typeElement +
                '}';
    }
}
