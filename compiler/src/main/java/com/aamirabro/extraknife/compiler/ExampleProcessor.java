package com.aamirabro.extraknife.compiler;

import com.aamirabro.extraknife.InjectExtra;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by aamirabro on 05/05/2017.
 */

public class ExampleProcessor{}

//extends AbstractProcessor {
//    @Override public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
//    }
//    @Override public Set<String> getSupportedAnnotationTypes() {
//        return Collections.singleton(InjectExtra.class.getName());
//    }
//    @Override public boolean process( Set<? extends TypeElement> annotations, RoundEnvironment env) {
//        Set<? extends Element> elements = env.getElementsAnnotatedWith(InjectExtra.class);
//        for (Element element : elements) {
//            // perform your work
//        }
//        return false;
//    }
//}
