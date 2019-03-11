package multilevel.multilevelmarkitning;

import java.util.Random;

public class IdGenerator {

    public static String generateId(String name)
    {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
//        LocalDateTime now = LocalDateTime.now();
//        String id=dtf.format(now).toString();
//        id=id+name;
        Random random=new Random();
        int num=random.nextInt(2000);
        Integer l=new Integer(num);
        String id=l.toString();
        return id;
    }


}
