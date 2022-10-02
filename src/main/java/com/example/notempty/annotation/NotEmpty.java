package com.example.notempty.annotation;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * This annotation is checked for the following conditions:
 *
 * <ul>
 *     <li>Only allowed on member variables.<br>
 *         <i>That's reimplementing the wheel for demonstration purposes:
 *         Java already gives us {@code @Target(FIELD)}.</i>
 *     <li>The member variable must be of type {@link String}.<br>
 *          <i>Now that's nothing the Java annotations can enforce.</i>
 *     <li>The member variable must be initialized with a value for which
 *         {@link StringUtils#isNotEmpty(CharSequence)} holds.<br>
 *         <i>Demonstrates how to call into an external library.</i>
 * </ul>
 */
@Documented
@Retention(SOURCE)
public @interface NotEmpty {
}
