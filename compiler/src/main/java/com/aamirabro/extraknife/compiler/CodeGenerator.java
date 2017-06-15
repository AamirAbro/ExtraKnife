package com.aamirabro.extraknife.compiler;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;

import static com.squareup.javapoet.ClassName.get;
import static com.squareup.javapoet.MethodSpec.methodBuilder;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.STATIC;

final class CodeGenerator {

    private static final String CLASS_NAME = "StringUtil";

//    public static TypeSpec generateClass(List<AnnotatedClass> classes) {
//        TypeSpec.Builder builder =  classBuilder(CLASS_NAME)
//                .addModifiers(PUBLIC, FINAL);
//        for (AnnotatedClass anno : classes) {
//            builder.addMethod(makeCreateStringMethod(anno));
//        }
//        return builder.build();
//    }

    /**
     * @return a createString() method that takes annotatedClass's type as an input.
     */
    private static MethodSpec makeCreateStringMethod(AnnotatedClass annotatedClass) {
//        StringBuilder builder = new StringBuilder();
//        builder.append(String.format("return \"%s{\" + ", annotatedClass.className));
//        for (String variableName : annotatedClass.variableNames) {
//            builder.append(String.format(" \"%s='\" + String.valueOf(instance.%s) + \"',\" + ",
//                    variableName, variableName));
//        }
//        builder.append("\"}\"");
//        return methodBuilder("createString")
//                .addJavadoc("@return string suitable for {@param instance}'s toString()")
//                .addModifiers(PUBLIC, STATIC)
//                .addParameter(get(annotatedClass.getType()), "instance")
//                .addStatement(builder.toString())
//                .returns(String.class)
//                .build();
        return null;
    }

    public static TypeSpec generateClass(AnnotatedClass annotatedClass) {
        TypeSpec.Builder classBuilder =  classBuilder(String.format("%s_ExtrasInjector", annotatedClass.className))
                .addModifiers(FINAL);

        MethodSpec.Builder methodBuilder = methodBuilder("injectExtras")
                .addModifiers(Modifier.PROTECTED, STATIC)
                .addParameter(get(annotatedClass.getType()), "activity");

        for (AnnotatedField field : annotatedClass.fields) {
            if (field.type == TypeKind.INT) {
                methodBuilder.addStatement("activity.$N = activity.getIntent().getIntExtra($S, 0)", field.variableName, field.key);
            } else if (field.type == TypeKind.LONG) {
                methodBuilder.addStatement("activity.$N = activity.getIntent().getLongExtra($S, 0)", field.variableName, field.key);
            } else if (field.type == TypeKind.DOUBLE) {
                methodBuilder.addStatement("activity.$N = activity.getIntent().getDoubleExtra($S, 0)", field.variableName, field.key);
            } else if (field.type == TypeKind.FLOAT) {
                methodBuilder.addStatement("activity.$N = activity.getIntent().getFloatExtra($S, 0)", field.variableName, field.key);
            } else if (field.type == TypeKind.BOOLEAN) {
                methodBuilder.addStatement("activity.$N = activity.getIntent().getBooleanExtra($S, false)", field.variableName, field.key);
            }
        }
        return classBuilder
                .addMethod(methodBuilder.build())
                .build();
    }
}
