package com.kmware.web.bbean.session;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SessionDataTest {

	@Mock
	SessionBean sessionBean;
	
	@Test
	public void Test1(){
		String key = "name";
		String value = "Kate";
		when(sessionBean.get(key)).thenReturn(value);
		
		sessionBean.put(key, value);
		sessionBean.put(key, value);
		verify(sessionBean,times(2)).put(key, value);
		assertEquals(sessionBean.get(key), value);
	}
}
