package com.aamirabro.extraknife.compiler;

import com.aamirabro.extraknife.InjectExtra;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.*;
import javax.annotation.processing.Messager;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static com.squareup.javapoet.JavaFile.builder;
import static java.util.Collections.singleton;
import static javax.lang.model.SourceVersion.latestSupported;
import static javax.tools.Diagnostic.Kind.*;

@AutoService(Processor.class)
public class InjectExtraProcessor extends AbstractProcessor {

    private static final String ANNOTATION = "@" + InjectExtra.class.getSimpleName();

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return singleton(InjectExtra.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<TypeElement, AnnotatedClass> annotatedClassesMap = new HashMap();
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(InjectExtra.class)) {
            /*
            Our annotation is defined with @Target(value=TYPE). Therefore, we can assume that
            this annotatedElement is a TypeElement.
             */
            TypeElement enclosingElement = (TypeElement) annotatedElement.getEnclosingElement();
            //TypeVariable annotatedFiled = (TypeVariable) annotatedElement;
            if (!isValid(enclosingElement, annotatedElement)) {
                return true;
            }

            AnnotatedClass annotatedClass = null;
            try {
                annotatedClass = buildAnnotatedClass(annotatedClassesMap.get(enclosingElement), enclosingElement, annotatedElement);
                annotatedClassesMap.put(enclosingElement, annotatedClass);
            } catch (NoPackageNameException | IOException e) {
                String message = String.format("Couldn't process class %s: %s", annotatedElement,
                        e.getMessage());
                messager.printMessage(ERROR, message, annotatedElement);
            }

            messager.printMessage(NOTE, "annotatedElement : " + annotatedElement.toString());
        }
        try {
            generate(annotatedClassesMap);
        } catch (NoPackageNameException | IOException e) {
            messager.printMessage(ERROR, "Couldn't generate class");
        }
        return true;
    }

    private boolean isValid(TypeElement annotatedClass, Element annotatedElement) {

        if (!ClassValidator.isActivity(annotatedClass)) {
            String message = String.format("Classes annotated with %s must be an Activity.",
                    ANNOTATION);
            messager.printMessage(ERROR, message, annotatedClass);
            return false;
        }

        if (!ClassValidator.hasValidAccess(annotatedElement)) {
            String message = String.format("Fields annotated with %s must be Package protected and non final.",
                    ANNOTATION);
            messager.printMessage(ERROR, message, annotatedClass);
            return false;
        }

        if (!ClassValidator.isAllowedType(annotatedElement)) {
            String message = String.format("Fields annotated with %s must be int",
                    ANNOTATION);
            messager.printMessage(ERROR, message, annotatedElement);
            return false;
        }

        return true;
    }

//    private AnnotatedClass buildAnnotatedClass(TypeElement annotatedClass)
//            throws NoPackageNameException, IOException {
//        ArrayList<String> variableNames = new ArrayList<>();
//        for (Element element : annotatedClass.getEnclosedElements()) {
//            if (!(element instanceof VariableElement)) {
//                continue;
//            }
//            VariableElement variableElement = (VariableElement) element;
//            variableNames.add(variableElement.getSimpleName().toString());
//        }
//        return new AnnotatedClass(annotatedClass , variableNames);
//    }

    private AnnotatedClass buildAnnotatedClass(AnnotatedClass annotatedClass, TypeElement enclosingField, Element annotatedFiled)
            throws NoPackageNameException, IOException {

        if (annotatedClass == null) {
            annotatedClass = new AnnotatedClass(enclosingField, new ArrayList<AnnotatedField>());
        }

        annotatedClass.addField(new AnnotatedField(annotatedFiled));

        return annotatedClass;
    }

    private void generate(Map<TypeElement, AnnotatedClass> annos) throws NoPackageNameException, IOException {

        for (AnnotatedClass annotatedClass : annos.values()) {
            messager.printMessage(NOTE, annotatedClass.toString());

            TypeSpec generatedClass = CodeGenerator.generateClass(annotatedClass);

            JavaFile javaFile = builder(annotatedClass.packageName, generatedClass).build();
            javaFile.writeTo(processingEnv.getFiler());

        }

//        String packageName = getPackageName(processingEnv.getElementUtils(),
//                annos.get(0).typeElement);
//        TypeSpec generatedClass = CodeGenerator.generateClass(annos);




    }
}

