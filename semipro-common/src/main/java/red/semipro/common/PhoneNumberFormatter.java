package red.semipro.common;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import javax.validation.constraints.NotNull;

public class PhoneNumberFormatter {

    public static String formatE164(@NotNull final String number) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        PhoneNumber phoneNumber = null;
        try {
            phoneNumber = phoneNumberUtil.parse(number, "JP");
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        return phoneNumberUtil.format(phoneNumber, PhoneNumberFormat.E164);
    }

    public static String formatNational(@NotNull final String number) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        PhoneNumber phoneNumber = null;
        try {
            phoneNumber = phoneNumberUtil.parse(number, "JP");
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        return phoneNumberUtil.format(phoneNumber, PhoneNumberFormat.NATIONAL).replaceAll("-", "");
    }

    public static void main(String[] args) throws NumberParseException {
        System.out.println(formatE164("09012345678"));
        System.out.println(formatNational("+819012345678"));
    }
}
