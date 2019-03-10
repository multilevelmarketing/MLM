package multilevel.multilevelmarkitning.User;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import multilevel.multilevelmarkitning.R;

public class ListAdapter extends ArrayAdapter<String> {
    private  final Activity context;
    private  final ArrayList<String> maintitle;
    private  final ArrayList<String> subtitle;

    public ListAdapter(Activity context, ArrayList<String> maintitle, ArrayList<String> subtitle) {
        super(context, R.layout.user_cus_showlistitems,maintitle);
        this.maintitle = maintitle;
        this.subtitle = subtitle;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View view=inflater.inflate(R.layout.user_cus_showlistitems,null,true);

        TextView title=(TextView)view.findViewById(R.id.cus_show_title);
        TextView describtion=(TextView)view.findViewById(R.id.cus_show_des);
        title.setText(maintitle.get(position));
        describtion.setText(subtitle.get(position));
        return  view;
    }
}
