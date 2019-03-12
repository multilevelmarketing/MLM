package multilevel.multilevelmarkitning.Customer;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
    String cat;
    String name;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    SharedPreferences.Editor e;
    RequestQueue requestQueue;
    int c1=0,c2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase__commision);
        actionBar=getSupportActionBar();
        actionBar.setTitle("Purchase and commission");
        actionBar.setDisplayHomeAsUpEnabled(true);
        spinnerprocat=(Spinner)findViewById(R.id.cus_pur_pro_type);
        requestQueue = Volley.newRequestQueue(Purchase_Commision.this);
        spinnerproname=(Spinner)findViewById(R.id.cus_pur_pro_name);
        editTextcomamt=(EditText)findViewById(R.id.cus_pur_commission_amt);
        editTextcompercent=(EditText)findViewById(R.id.cus_pur_commission);
        editTextproductcost=(EditText)findViewById(R.id.cus_pur_pro_cost);
        editTextproid=(EditText)findViewById(R.id.cus_pur_pro_id);
        editTextcusid=(EditText)findViewById(R.id.cus_pur_id);
        sharedPreferences=getSharedPreferences("customerLogin",MODE_PRIVATE);
        preferences=getSharedPreferences("productIdData",MODE_PRIVATE);
        e=preferences.edit();
        editor=sharedPreferences.edit();
        editTextcusid.setText(sharedPreferences.getString("username",null));
        getJSONProductCategory("https://veiled-heat.000webhostapp.com/MLM/Customer/prod_cat.php");


        spinnerprocat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectProductName(parent.getItemAtPosition(position).toString());
                e.putString("productCat",parent.getSelectedItem().toString());
//                cat=((TextView)view).getText().toString();
//                c1=1;
               // cat=parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(),cat,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        spinnerproname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                name=((TextView)view).getText().toString();
                e.putString("productName",parent.getSelectedItem().toString());
//                c2=1;
                //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        e.commit();
        getProductId(preferences.getString("productCat",null),preferences.getString("productName",null));
//        String s=cat+name;
//        if(c1==1 && c2==1)
//            Toast.makeText(Purchase_Commision.this,s,Toast.LENGTH_LONG).show();

    }
    void getProductId(String category,String name)
    {
        ProductId productId=new ProductId(category,name,new Response.Listener<String>(){
            @Override

            public void onResponse(String response) {
                Log.i("Response", response);

                try
                {
                    if (new JSONObject(response).get("success").equals("true"))
                    {
                        editTextproid.setText(new JSONObject(response).getString("pid"));
                        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                        //Toast.makeText(Admin_Login.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                    else
                    {
                     //   progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "data not found ", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }


        });

        requestQueue.add(productId);







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
              //  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
        //    System.out.println();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        spinnerprocat.setAdapter(arrayAdapter);
    }

    public void selectProductName(String product)
    {

        ProductList productList=new ProductList(product,new Response.Listener<String>(){
            @Override

            public void onResponse(String response) {
                Log.i("Response", response);
               // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                try
                {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject jsonObject=null;
                    String[] heroes = new String[jsonArray.length()];
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        jsonObject=jsonArray.getJSONObject(i);
                        heroes[i] = jsonObject.getString("name");

                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Purchase_Commision.this, android.R.layout.simple_list_item_1, heroes);
                    spinnerproname.setAdapter(arrayAdapter);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


//                try
//                {
//                   Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
//
//                }
//                catch (JSONException e)
//                {
//                    e.printStackTrace();
//                }
            }


        });

        requestQueue.add(productList);




    }


    public class ProductList extends StringRequest {

        private static final String REGISTER_URL = "https://veiled-heat.000webhostapp.com/MLM/Customer/product_name.php";
        private Map<String, String> parameters;

        public ProductList(String Id,Response.Listener<String> listener) {
            super(Method.POST, REGISTER_URL, listener, null);
            parameters = new HashMap<>();
            parameters.put("product",Id);
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError
        {
            return parameters;
        }
    }





    class ProductId extends StringRequest {

        private static final String REGISTER_URL = "https://veiled-heat.000webhostapp.com/MLM/Customer/product_id.php";
        private Map<String, String> parameters;

        public ProductId(String pCategory,String pName,Response.Listener<String> listener) {
            super(Method.POST, REGISTER_URL, listener, null);
            parameters = new HashMap<>();
            parameters.put("productCat",pCategory);
            parameters.put("productName",pName);
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError
        {
            return parameters;
        }
    }

}
