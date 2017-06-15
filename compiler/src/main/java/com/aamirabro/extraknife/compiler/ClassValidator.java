package com.aamirabro.extraknife.compiler;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import javax.lang.model.element.Modifier;

final class ClassValidator {
    static boolean hasValidAccess (Element annotatedClass) {

        return !annotatedClass.getModifiers().contains(Modifier.PRIVATE)
                && !annotatedClass.getModifiers().contains(Modifier.STATIC)
                && !annotatedClass.getModifiers().contains(Modifier.FINAL);

    }

    static boolean isAllowedType(Element annotatedFiled) {
        TypeMirror elementType = annotatedFiled.asType();
        TypeKind kind = elementType.getKind();
        return kind == TypeKind.INT
                || kind == TypeKind.LONG
                || kind == TypeKind.FLOAT
                || kind == TypeKind.DOUBLE
                || kind == TypeKind.BOOLEAN;
    }

    static boolean isActivity(TypeElement annotatedClass) {
        return isSubtypeOfType(annotatedClass, "android.app.Activity");
    }

    static private boolean isSubtypeOfType(TypeElement element, String otherType) {
        return isSubtypeOfType(element.asType(), otherType);
    }

    static private boolean isSubtypeOfType(TypeMirror typeMirror, String otherType) {
        if (isTypeEqual(typeMirror, otherType)) {
            return true;
        }
        if (typeMirror.getKind() != TypeKind.DECLARED) {
            return false;
        }
        DeclaredType declaredType = (DeclaredType) typeMirror;
        List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
        if (typeArguments.size() > 0) {
            StringBuilder typeString = new StringBuilder(declaredType.asElement().toString());
            typeString.append('<');
            for (int i = 0; i < typeArguments.size(); i++) {
                if (i > 0) {
                    typeString.append(',');
                }
                typeString.append('?');
            }
            typeString.append('>');
            if (typeString.toString().equals(otherType)) {
                return true;
            }
        }
        Element element = declaredType.asElement();
        if (!(element instanceof TypeElement)) {
            return false;
        }
        TypeElement typeElement = (TypeElement) element;
        TypeMirror superType = typeElement.getSuperclass();
        if (isSubtypeOfType(superType, otherType)) {
            return true;
        }
        for (TypeMirror interfaceType : typeElement.getInterfaces()) {
            if (isSubtypeOfType(interfaceType, otherType)) {
                return true;
            }
        }
        return false;
    }

    static private boolean isTypeEqual(TypeMirror typeMirror, String otherType) {
        return otherType.equals(typeMirror.toString());
    }
}
