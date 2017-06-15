package com.aamirabro.extraknife;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * Created by Aamir Abro on 26/12/2016.
 */
@Target(FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface InjectExtra {
    String value();
}
