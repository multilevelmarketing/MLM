package multilevel.multilevelmarkitning.Customer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import multilevel.multilevelmarkitning.R;

public class Purchase_Commision extends AppCompatActivity {

    ActionBar actionBar;
    Spinner spinnerprocat,spinnerproname;
    EditText editTextcusid,editTextproid,editTextproductcost,editTextcompercent,editTextcomamt;
    String[] title;
    InputStream in;
    String line;
    String result;
    StringBuffer sb;
    Button btn;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase__commision);
        actionBar=getSupportActionBar();
        actionBar.setTitle("Purchase and commission");
        actionBar.setDisplayHomeAsUpEnabled(true);
        spinnerprocat=(Spinner)findViewById(R.id.cus_pur_pro_type);
        spinnerproname=(Spinner)findViewById(R.id.cus_pur_pro_name);
        editTextcomamt=(EditText)findViewById(R.id.cus_pur_commission_amt);
        editTextcompercent=(EditText)findViewById(R.id.cus_pur_commission);
        editTextproductcost=(EditText)findViewById(R.id.cus_pur_pro_cost);
        editTextproid=(EditText)findViewById(R.id.cus_pur_pro_id);
        editTextcusid=(EditText)findViewById(R.id.cus_pur_pro_id);
        loginPreferences=getSharedPreferences("customerLogin",MODE_PRIVATE);
        loginPrefsEditor=loginPreferences.edit();
        editTextcusid.setText(loginPreferences.getString("username",null));








    }




    void getProductCategory()
    {
        try {
            URL url=new URL("https://veiled-heat.000webhostapp.com/MLM/Customer/prod_cat.php");
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
            title=new String[jsonArray.length()];
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObject=jsonArray.getJSONObject(i);
                title[i]=jsonObject.getString("Type")
              //  title.add(jsonObject.getString("Type"));

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
