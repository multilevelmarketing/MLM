package multilevel.multilevelmarkitning.User;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

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
    ArrayList<String> title=new ArrayList<String>();
    ArrayList<String> subtitle=new ArrayList<String>();
    InputStream in;
    String line;
    String result;
    StringBuffer sb;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__show_customer);
        listView=(ListView)findViewById(R.id.cusShow_list);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        getData();
        ListAdapter listAdapter=new ListAdapter(User_ShowCustomer.this,title,subtitle);
        listView.setAdapter(listAdapter);
        actionBar=getSupportActionBar();
        actionBar.setTitle("Customers");
        actionBar.setDisplayHomeAsUpEnabled(true);



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
            sb=new StringBuffer();
            while((line=bf.readLine())!=null)
            {
                sb.append(line+"\n");
            }
            in.close();
            result=sb.toString();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try{
            JSONArray jsonArray=new JSONArray(result);
            JSONObject jsonObject=null;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObject=jsonArray.getJSONObject(i);
                title.add(jsonObject.getString("ID"));
                subtitle.add(jsonObject.getString("Name"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}