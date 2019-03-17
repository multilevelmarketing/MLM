package multilevel.multilevelmarkitning.Admin;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import multilevel.multilevelmarkitning.R;


public class Admin_Show extends Fragment {






    InputStream in;
    String line;
    String result;
    StringBuffer sb;
    static String[][] data;
    static String[] header={"id","name","mobile"};




    public void adminshow ()
    {
        try {
            URL url=new URL("https://veiled-heat.000webhostapp.com/MLM/Admin/UserShow.php");
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
            data=new String[jsonArray.length()][3];
            int k=0;
            for(int i=0;i<jsonArray.length();i++) {
                k=0;
                for (int j = 0; j <3 ; j++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    if(k==0)
                    {
                        data[i][j]=jsonObject.getString("User_Id");
                    }
                    else if(k==1)
                    {
                        data[i][j]=jsonObject.getString("User_Name")    ;
                    }
                    else if(k==2)
                    {
                        data[i][j]=jsonObject.getString("Mobile");
                    }
                    k++;

                }
            }



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }






   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.admin_user_show, container, false);
       StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());



       final TableView<String[]> tableView = (TableView<String[]>) view.findViewById(R.id.showcustomer);
       tableView.setHeaderBackgroundColor(Color.parseColor("#B3E5FC"));
       tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(getActivity(),header));
       tableView.setColumnCount(3);
       adminshow();
       tableView.setDataAdapter(new SimpleTableDataAdapter(getActivity(), data));

       return view;
    }





}
