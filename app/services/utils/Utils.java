package services.utils;

import java.lang.reflect.Field;

/**
 * Created by Kris on 2015-05-14.
 */
public class Utils {
    public static boolean isMemberOfClass(String field, Class someClass){
        try {
            Field someField = someClass.getField(field);
        } catch (SecurityException e){
            System.out.println("Security Exception");
            //field exists but is private
            return true;
        } catch (NoSuchFieldException e) {
            System.out.println("No such Field Exception");
            //field does not exists
            return false;
        }

        return true;
    }
}
