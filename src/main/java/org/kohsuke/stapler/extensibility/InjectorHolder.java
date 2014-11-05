package org.kohsuke.stapler.extensibility;

import com.google.inject.Injector;

/**
 * Abstraction for how we get {@link Injector}.
 *
 * This allows us to test much of the stuff without deploying it as a webapp.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class InjectorHolder {
    public abstract Injector get();

    public static InjectorHolder HOLDER;

    public static Injector getInjector() {
        return HOLDER.get();
    }
}
