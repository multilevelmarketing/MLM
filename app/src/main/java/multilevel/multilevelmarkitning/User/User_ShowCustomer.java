package multilevel.multilevelmarkitning.User;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import multilevel.multilevelmarkitning.R;

public class User_ShowCustomer extends AppCompatActivity {

    ListView listView;
    ArrayList<String> title;
    ArrayList<String> subtitle;
    InputStream in;
    String line;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__show_customer);
        listView=(ListView)findViewById(R.id.cusShow_list);




    }
   void getData()
   {
       try {
           URL url=new URL("https://veiled-heat.000webhostapp.com/MLM/Customer/CustomerShowAll.php");
           HttpURLConnection con=(HttpURLConnection)url.openConnection();
           con.setRequestMethod("GET");
           in=new BufferedInputStream(con.getInputStream());
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
        try {
            BufferedReader bf=new BufferedReader(new InputStreamReader(in));
            while(line=bf.readLine()!=null)
            {

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
