package com.aamirabro.extraknife;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * Creates class for injections
 */
@Target(TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface AllowInjectExtras {}