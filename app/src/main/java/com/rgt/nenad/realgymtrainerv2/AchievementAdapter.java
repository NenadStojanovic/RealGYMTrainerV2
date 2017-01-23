package com.rgt.nenad.realgymtrainerv2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nenad on 01-Jul-16.
 */
public class AchievementAdapter extends BaseAdapter {


    LayoutInflater inflater;
    List<Achievement> items;
    Context Maincontext;

    public AchievementAdapter(Activity context, List<Achievement> items) {
        super();
        Maincontext = context;
        this.items = items;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getViewTypeCount(){
        return getCount();
    }
    @Override
    public int getItemViewType(int position){
        return position;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View vi=convertView;
        final Achievement item = items.get(position);
        final ViewHolderAchievement vh;

        if(convertView==null) {
            vi = inflater.inflate(R.layout.item_row_ach, parent,false);
            vh =  new ViewHolderAchievement((TextView)vi.findViewById(R.id.textViewMessage), (ImageView)vi.findViewById(R.id.imageViewAchievement), (LinearLayout)vi.findViewById(R.id.parentLayout));
            vi.setTag(vh);


        }
        else{
            vh = (ViewHolderAchievement) vi.getTag();

        }

        vh.getMessage().setText(item.getName().toString());
        if(item.getType().equals("1"))
            vh.getImage().setImageResource(R.drawable.obim_achievement);
        else if(item.getType().equals("2"))
            vh.getImage().setImageResource(R.drawable.trening_achievement);
        else
            vh.getImage().setImageResource(R.drawable.weight_achievement);

        if(item.getActive().equals("1"))
        {
            vh.getParent().setBackgroundColor(Maincontext.getResources().getColor(R.color.AchievementOrange));
            vh.getParent().setEnabled(false);
        }
        else  if(item.getActive().equals("2"))
        {
            vh.getParent().setBackgroundColor(Maincontext.getResources().getColor(R.color.FontGreen));
            vh.getParent().setEnabled(true);
        }
        else
        {
            vh.getParent().setEnabled(false);

        }


        vh.getParent().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                item.setActive("1");
                vh.getParent().setBackgroundColor(Maincontext.getResources().getColor(R.color.AchievementOrange));
                vh.getParent().setEnabled(false);
                SQLiteDatabase db1;
                db1 = Maincontext.openOrCreateDatabase("rgtbaza", SQLiteDatabase.CREATE_IF_NECESSARY, null);
                boolean pom2 = db1.enableWriteAheadLogging();
                ContentValues cv = new ContentValues();
                cv.put("active", 1);
                int pom = db1.update("Dostignuca", cv, "ID = ? ", new String[]{item.getId().toString()});
                db1.close();
            }
        });






        return vi;
    }

}
