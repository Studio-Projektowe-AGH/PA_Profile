package services.utils;

import models.BusinessUserProfile;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UtilsTest {

    @Test
    public void isMemberOfClassTest(){
        boolean valueForCorrectField = Utils.isMemberOfClass("name", BusinessUserProfile.class);
        assertTrue("Problem with checking right field", valueForCorrectField);

        boolean valueForWrongField = Utils.isMemberOfClass("thereIsNoSuchField", BusinessUserProfile.class);
        assertFalse("Problem with checking wrong field", valueForWrongField);

    }

}