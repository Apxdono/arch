package com.kmware.web.bbean.session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.kmware.dao.CommonDAO;
import com.kmware.dao.DAOMessage;
import com.kmware.dao.ICommonDAO;
import com.kmware.model.City;

@RunWith(MockitoJUnitRunner.class)
public class CommonCRUDTest {
	
	@Mock
	ICommonDAO dao;
	
	@Test
	public void DuplicatePrimaryKey(){
		City c1 = new City();
		c1.setDisplayName("Moscow");
		c1.setCountry(null);
		c1.setId("1");
		
		City c2 = new City();
		c2.setDisplayName("Kiev");
		c2.setCountry(null);
		c2.setId("2");
		
		when(dao.create(c1)).thenReturn(DAOMessage.OK);
		when(dao.create(c2)).thenReturn(DAOMessage.UniqueConstraintViolation);
		assertEquals(dao.create(c1), DAOMessage.OK);
		assertEquals(dao.create(c2), DAOMessage.UniqueConstraintViolation);
		
	}

	@Test
	public void OptimisticLockTest(){
		City c1 = new City();
		c1.setDisplayName("Moscow");
		c1.setCountry(null);
		c1.setId("1");
		
		City c2 = new City();
		c2.setDisplayName("Kiev");
		c2.setCountry(null);
		c2.setId("2");
		
		when(dao.update(c1)).thenReturn(new Object[]{DAOMessage.OK,c1});
		
		Object[] result1 = dao.update(c1);
		DAOMessage firstMessage = (DAOMessage) result1[0];
		City firstCity = (City) result1[1];
		
		when(dao.update(c1)).thenReturn(new Object[]{DAOMessage.OptimisticLock,c1});
		
		Object[] result2 = dao.update(c1);
		DAOMessage secondMessage = (DAOMessage) result2[0];
		City secondCity = (City) result2[1];
		
		verify(dao,times(2)).update(c1);
		assertEquals(firstMessage, DAOMessage.OK);
		assertEquals(secondMessage, DAOMessage.OptimisticLock);
		assertNotEquals(firstCity, null);
		assertNotEquals(secondCity, null);
		assertEquals(firstCity, c1);
		assertEquals(secondCity, c1);
	}
	
}
