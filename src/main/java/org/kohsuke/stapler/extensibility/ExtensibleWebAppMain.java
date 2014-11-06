package org.kohsuke.stapler.extensibility;

import com.cloudbees.sdk.extensibility.ExtensionFinder;
import com.cloudbees.sdk.extensibility.ExtensionList;
import com.cloudbees.sdk.extensibility.lifecycle.Startable;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.kohsuke.stapler.WebApp;
import org.kohsuke.stapler.framework.AbstractWebAppMain;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for web applications.
 *
 * <p>
 * Applications that use stapler can use this as the base class,
 * and then register that as the servlet context listener.
 *
 * @param <T>
 *      The type of the root object instance
 * @author Kohsuke Kawaguchi
 */
public abstract class ExtensibleWebAppMain<T> extends AbstractWebAppMain<T> {

    private ClassLoader world;

    protected ExtensibleWebAppMain(Class<T> rootType) {
        super(rootType);
    }

    @Override
    public Object createApplication() throws Exception {
        final Injector injector = createInjector();

        WebApp.get(context).getFacet(GuiceFacet.class).injector = injector;
        InjectorHolder.HOLDER = new InjectorHolder() {
            @Override
            public Injector get() {
                return injector;
            }
        };

        for (Startable s : new ExtensionList<Startable>(Startable.class).list(injector)) {
            try {
                s.start();
            } catch (Exception e) {
                throw new Exception(s+" failed to start",e);
            }
        }

        return injector.getInstance(rootType);
    }

    public Injector createInjector() {
        world = createWorld();
        Thread.currentThread().setContextClassLoader(world);

        List<Module> modules = new ArrayList<Module>();
        assembleModules(modules);
        return Guice.createInjector(modules);
    }

    protected void assembleModules(List<Module> modules) {
        modules.add(new ExtensionFinder(world));
        modules.add(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ClassLoader.class).toInstance(world);
                bind(ServletContext.class).toInstance(context);
                bind(rootType);
            }
        });
    }

    protected ClassLoader createWorld() {
        return Thread.currentThread().getContextClassLoader();
    }
}
