package multilevel.multilevelmarkitning.Customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.Map;

import multilevel.multilevelmarkitning.R;

import static android.content.Context.MODE_PRIVATE;

public class TotalAmount extends Fragment {

    TextView textView;
    SharedPreferences preferences;
    RequestQueue requestQueue;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.totalamount, container, false);

        requestQueue= Volley.newRequestQueue(getContext());
        preferences=getActivity().getSharedPreferences("cusLogin",MODE_PRIVATE);
        String UserID=preferences.getString("customerid",null);

        TotalComission totalComission=new TotalComission(UserID,new Response.Listener<String>(){
            @Override

            public void onResponse(String response) {
                Log.i("Response", response);

                try
                {
                    textView=(TextView)container.findViewById(R.id.textView3);
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject jsonObject=null;
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        jsonObject=jsonArray.getJSONObject(i);
                        textView.setText(jsonObject.getString("com"));

                    }
                //    Log.i("totalamount",new JSONObject(response).getString("com"));
                  //  Toast.makeText(getContext(),new JSONObject(response).getString("com"),Toast.LENGTH_SHORT).show();

                //   textView.setText("good");

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }


        });

        requestQueue.add(totalComission);




        return rootView;
    }


    public class TotalComission extends StringRequest {

        private static final String REGISTER_URL = "https://veiled-heat.000webhostapp.com/MLM/comission/getTotalcom.php";
        private Map<String, String> parameters;

        public TotalComission(String Id, Response.Listener<String> listener) {
            super(Method.POST, REGISTER_URL, listener, null);
            parameters = new HashMap<>();
            parameters.put("cusid",Id);


        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError
        {
            return parameters;
        }
    }

}
