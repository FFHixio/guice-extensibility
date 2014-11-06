package org.kohsuke.stapler.extensibility;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Useful to inject the application object without knowing the type of it.
 *
 * <pre>
 * &#64;StaplerRoot @Inject Object app;
 * </pre>
 *
 * @author Kohsuke Kawaguchi
 */
@BindingAnnotation
@Target({FIELD, PARAMETER, METHOD })
@Retention(RUNTIME)
public @interface StaplerRoot {
}
