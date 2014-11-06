package org.kohsuke.stapler.extensibility;

import com.google.common.collect.Iterators;

import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Partial implementation of {@link ServletContext} that gets the part we care right.
 *
 * @author Kohsuke Kawaguchi
 */
abstract class ServletContextImpl implements ServletContext {
    private Map attributes;

    public final void init() {
        // mockito doesn't call the base constructor, so we have to initialize this from elsewhere
        attributes = new HashMap();
    }

    public final void setAttribute(String s, Object o) {
        attributes.put(s,o);
    }

    public final Object getAttribute(String s) {
        return attributes.get(s);
    }

    public final Enumeration<String> getAttributeNames() {
        return Iterators.asEnumeration(attributes.keySet().iterator());
    }

    public final void removeAttribute(String s) {
        attributes.remove(s);
    }
}
