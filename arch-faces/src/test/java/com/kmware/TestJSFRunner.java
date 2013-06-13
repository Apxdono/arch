package com.kmware;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 6/13/13
 * Time: 7:33 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestJSFRunner {

    @Mock
    FacesContext facesContext;

    @Mock
    Application application;

    @Test
    public void testInjectingFacesContext() {
        assertNotNull(facesContext);
    }

    @Test
    public void testInjectingApplication() {
        assertNotNull(application);
    }

    /**
     * Test using faces context.
     */
    @Test
    public void testUsingFacesContext() {
        UIViewRoot viewRoot = mock(UIViewRoot.class);
        when(facesContext.getViewRoot()).thenReturn(viewRoot);
        assertSame(facesContext.getViewRoot(), viewRoot);
        assertEquals(facesContext.getViewRoot(), viewRoot);
    }
}
