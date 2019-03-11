package multilevel.multilevelmarkitning.Customer;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
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
        editTextcusid=(EditText)findViewById(R.id.cus_pur_id);
        sharedPreferences=getSharedPreferences("customerLogin",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editTextcusid.setText(sharedPreferences.getString("username",null));
        getJSONProductCategory("https://veiled-heat.000webhostapp.com/MLM/Customer/prod_cat.php");








    }




    private void getJSONProductCategory(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] heroes = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            heroes[i] = obj.getString("Type");
         //   System.out.println();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        spinnerprocat.setAdapter(arrayAdapter);
    }
}
