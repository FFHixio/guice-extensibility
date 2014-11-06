package org.kohsuke.stapler.extensibility;

import org.junit.Assert;
import org.junit.Test;
import org.kohsuke.stapler.WebApp;
import org.mockito.Mockito;

import javax.servlet.ServletContextEvent;

/**
 * @author Kohsuke Kawaguchi
 */
public class ExtensibleWebAppMainTest extends Assert {
    @Test
    public void createWorld() throws InterruptedException {
        ExtensibleWebAppMain app = new ExtensibleWebAppMain(TheRoot.class) {
            @Override
            protected String getApplicationName() {
                return "FOO";
            }
        };
        ServletContextImpl context = Mockito.mock(ServletContextImpl.class);
        context.init();
        app.contextInitialized(new ServletContextEvent(context));

        for (int i=0; i<10; i++) {
            Thread.sleep(100);

            Object theApp = WebApp.get(context).getApp();
            if (theApp!=null) {
                assertTrue(String.valueOf(theApp), theApp instanceof TheRoot);
                return;
            }
        }

        fail("Failed to create?");
    }

    public static class TheRoot {}
}
