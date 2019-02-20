package multilevel.multilevelmarkitning;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IdGenerator {

    public static String generateId(String name)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        String id=dtf.format(now).toString();
        id=id+name;
        return id;
    }


}
