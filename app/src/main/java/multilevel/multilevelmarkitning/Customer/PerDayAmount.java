package multilevel.multilevelmarkitning.Customer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.util.HashMap;
import java.util.Map;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import multilevel.multilevelmarkitning.R;

import static android.content.Context.MODE_PRIVATE;

public class PerDayAmount extends Fragment {
    static String[] header={"productid","commission","time"};
    static String[][] data;
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    AlertDialog.Builder builder;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.perdayamount, container, false);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        requestQueue=Volley.newRequestQueue(getContext());
        builder=new AlertDialog.Builder(getContext());

        sharedPreferences=getActivity().getSharedPreferences("cusLogin",MODE_PRIVATE);
        final String cusid=sharedPreferences.getString("customerid",null);


        final TableView<String[]> tableView = (TableView<String[]>) rootView.findViewById(R.id.cus_perdayamount);
        tableView.isScrollContainer();
        tableView.setHeaderBackgroundColor(Color.parseColor("#B3E5FC"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(getActivity(),header));
        tableView.setColumnCount(3);

        tableView.addDataClickListener(new TableDataClickListener<String[]>() {
            @Override
            public void onDataClicked(int rowIndex, String[] clickedData) {
                builder.setTitle("Welcome "+cusid);
                builder.setMessage("Product Id : "+data[rowIndex][0]+"\n"+"Commission : "+data[rowIndex][1]+"\n"+"Time : "+data[rowIndex][2]);
                builder.setCancelable(true);
                builder.show();
            }
        });


        ComissionGet comissionGet=new ComissionGet(cusid,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.i("Response", response);

                try
                {

                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject jsonObject=null;
                    data=new String[jsonArray.length()][3];
                    int k=0;
                    for(int i=0;i<jsonArray.length();i++) {
                        k=0;
                        for (int j = 0; j <3 ; j++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            if(k==0)
                            {

                                data[i][j]=jsonObject.getString("product_id");
                            }
                            else if(k==1)
                            {
                                data[i][j]=jsonObject.getString("comission")    ;
                            }
                            else if(k==2)
                            {

                                data[i][j]=jsonObject.getString("time");
                            }
                            k++;


                        }
                    }
                    tableView.setDataAdapter(new SimpleTableDataAdapter(getActivity(), data));

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }


        });

        requestQueue.add(comissionGet);






        return rootView;
    }

}
