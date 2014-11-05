package org.kohsuke.stapler.extensibility;

import com.google.inject.Injector;
import org.kohsuke.MetaInfServices;
import org.kohsuke.stapler.Dispatcher;
import org.kohsuke.stapler.Facet;
import org.kohsuke.stapler.MetaClass;
import org.kohsuke.stapler.RequestImpl;
import org.kohsuke.stapler.ResponseImpl;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.WebApp;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * Holder of Guice injector.
 *
 * TODO: move to Stapler
 *
 * @author Kohsuke Kawaguchi
 */
@MetaInfServices
public class GuiceFacet extends Facet {
    public Injector injector;

    @Override
    public void buildViewDispatchers(MetaClass owner, List<Dispatcher> dispatchers) {
        // doesn't contribute to views
    }

    @Override
    public boolean handleIndexRequest(RequestImpl req, ResponseImpl rsp, Object node, MetaClass nodeMetaClass) throws IOException, ServletException {
        // doesn't contribute to views
        return false;
    }

    public static Injector get(StaplerRequest req) {
        return get(req.getWebApp());
    }

    public static Injector get(WebApp webApp) {
        return webApp.getFacet(GuiceFacet.class).injector;
    }

    public static Injector get() {
        return get(WebApp.getCurrent());
    }
}
