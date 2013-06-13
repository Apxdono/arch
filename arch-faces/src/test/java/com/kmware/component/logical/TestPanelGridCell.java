package com.kmware.component.logical;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 6/13/13
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(MockitoJUnitRunner.class)
public class TestPanelGridCell {

    @Test
    public void testPanelGridCell(){
          PanelGridCell cell = new PanelGridCell();
          cell.setColspan(5);
          assertEquals(5,cell.getColspan());
    }



}
