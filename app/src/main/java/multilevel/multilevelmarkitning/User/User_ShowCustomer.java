package multilevel.multilevelmarkitning.User;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import multilevel.multilevelmarkitning.Customer.Customer_show;
import multilevel.multilevelmarkitning.R;

public class User_ShowCustomer extends AppCompatActivity {

    ListView listView;
    ArrayList<String> title=new ArrayList<String>();
    ArrayList<String> subtitle=new ArrayList<String>();
    InputStream in;
    String line;
    String result;
    StringBuffer sb;
    RequestQueue requestQueue;
    ActionBar actionBar;
    AlertDialog.Builder builder;
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
        builder=new AlertDialog.Builder(this);
        actionBar.setTitle("Customers");
        actionBar.setDisplayHomeAsUpEnabled(true);
        requestQueue= Volley.newRequestQueue(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv=(TextView)view.findViewById(R.id.cus_show_title);
                final  String id1=tv.getText().toString();
             //   Toast.makeText(getApplicationContext(),id1,Toast.LENGTH_SHORT).show();
                builder.setTitle("Welcome : "+id1);

                class CustomerProfile extends StringRequest
                {
                    private static final String URL="https://veiled-heat.000webhostapp.com/MLM/Customer/customer_profile.php";
                    private Map<String,String> paremeters;

                    public CustomerProfile(String userid, Response.Listener<String> listener) {
                        super(Method.POST, URL, listener, null);
                        paremeters = new HashMap<>();
                        paremeters.put("cusid",userid);
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        return paremeters;
                    }
                }


                builder.setCancelable(true);
                CustomerProfile customerProfile=new CustomerProfile(id1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        Log.i("Response",response);

                        try
                        {

                            if (new JSONObject(response).getString("success").equals("true"))
                            {
                                builder.setMessage("Name : "+new JSONObject(response).getString("RegName")+"\nProfession : "+
                                        new JSONObject(response).getString("RegProfession")+"\nLevel : "+new JSONObject(response).getString("RegLevel")+"\nParent : "+
                                        new JSONObject(response).getString("RegParent")+"\nEmail : "+new JSONObject(response).getString("RegEmail")+"\nMobile : "+
                                        new JSONObject(response).getString("RegMobile")+"\nGender : "+new JSONObject(response).getString("RegGender")+"\nAddress : "+
                                        new JSONObject(response).getString("RegAddress"));
                                builder.show();


                            }

                            else
                            {

                                Toast.makeText(getApplicationContext(),"id not found",Toast.LENGTH_LONG).show();
                            }


                        }

                        catch (JSONException e)
                        {

                            e.printStackTrace();
                        }

                    }
                });

                requestQueue.add(customerProfile);


            }
        });



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