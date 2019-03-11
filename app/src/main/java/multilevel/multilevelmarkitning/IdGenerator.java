package multilevel.multilevelmarkitning;

import java.util.Random;

public class IdGenerator {

    public static String generateId(String name)
    {
       // String id=new String();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
//        LocalDateTime now = LocalDateTime.now();
//        String id=dtf.format(now);
//        id=id+name;
        Random random=new Random();
        int num=random.nextInt(2000);
        Integer l=new Integer(num);
        String id=l.toString()+name;
        return id;
    }


}
