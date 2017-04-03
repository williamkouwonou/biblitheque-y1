package  com.biblio.service.util;

import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.RandomStringUtils;

/**
 * Utility class for generating random Strings.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;

    private RandomUtil() {
    }

    /**
     * Generates a password.
     *
     * @return the generated password
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }
    /**
     * Generates a password.
     *
     * @param length
     * @return the generated password
     */
    public static String generateAlphaNumerique(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    /**
     * Generates an activation key.
     *
     * @return the generated activation key
     */
    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }
    public static String generatecode(int DEF_COUNT) {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
    * Generates a reset key.
    *
    * @return the generated reset key
    */
    public static String generateResetKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }
    
    public static String getRref() {

        Date d = new Date();
        Calendar ca = Calendar.getInstance();
        String ref = ca.get(Calendar.YEAR) + "" + concat(2, ca.get(Calendar.MONTH)) + "" + concat(2, ca.get(Calendar.DAY_OF_MONTH)) + "" + concat(2, ca.get(Calendar.HOUR)) + "" + concat(2, ca.get(Calendar.MINUTE)) + "" + concat(2, ca.get(Calendar.SECOND)) + "" + concat(4, ca.get(Calendar.MILLISECOND));
        return ref;
    }
public static String concat(Integer i, Integer number) {
        if (i == null || number == null) {
            return "";
        }

        String s = String.valueOf(number);
        while (i > s.length()) {
            s = "0" + s;
        }
        return s;
    }
}
