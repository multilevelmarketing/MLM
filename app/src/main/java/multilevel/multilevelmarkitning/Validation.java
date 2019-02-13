package multilevel.multilevelmarkitning;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Validation {

    public static boolean emailValidation(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    public static boolean mobileValidation(String s)
    {

        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");


        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }


}
