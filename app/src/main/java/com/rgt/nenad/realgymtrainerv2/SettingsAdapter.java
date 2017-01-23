package com.rgt.nenad.realgymtrainerv2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nenad on 02-Jul-16.
 */
public class SettingsAdapter extends BaseAdapter {


    LayoutInflater inflater;
    List<SettingsItem> items;
    Context Maincontext;

    public SettingsAdapter(Activity context, List<SettingsItem> items) {
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
        final SettingsItem item = items.get(position);
        final ViewHolderSettings vh;

        if(convertView==null) {
            vi = inflater.inflate(R.layout.item_row_settings, parent,false);
            vh =  new ViewHolderSettings((TextView)vi.findViewById(R.id.textViewExercise),(Switch)vi.findViewById(R.id.switchEnable),(LinearLayout)vi.findViewById(R.id.parentLayout));
            vi.setTag(vh);


        }
        else{
            vh = (ViewHolderSettings) vi.getTag();

        }


        vh.getName().setText(item.getName());
        if(item.getActive().equals("1")) {
            vh.getLayout().setBackgroundColor(Maincontext.getResources().getColor(R.color.AchievementOrange));
            vh.getEnable().setChecked(true);
        }
        else {
            vh.getLayout().setBackgroundColor(Maincontext.getResources().getColor(R.color.DarkBackground));
            vh.getEnable().setChecked(false);
        }

        vh.getEnable().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    vh.getLayout().setBackgroundColor(Maincontext.getResources().getColor(R.color.AchievementOrange));
                    SQLiteDatabase db1;
                    db1 = Maincontext.openOrCreateDatabase("rgtbaza", SQLiteDatabase.CREATE_IF_NECESSARY, null);
                    boolean pom2 = db1.enableWriteAheadLogging();
                    ContentValues cv = new ContentValues();
                    cv.put("active", 1);
                    int pom = db1.update("Trening", cv, "ID = ? ", new String[]{item.getID().toString()});
                    db1.close();
                    if(item.getActive().equals("0"))
                        item.setActive("1");
                    else
                        item.setActive("0");


                } else {
                    vh.getLayout().setBackgroundColor(Maincontext.getResources().getColor(R.color.DarkBackground));
                    SQLiteDatabase db1;
                    db1 = Maincontext.openOrCreateDatabase("rgtbaza", SQLiteDatabase.CREATE_IF_NECESSARY, null);
                    boolean pom2 = db1.enableWriteAheadLogging();
                    ContentValues cv = new ContentValues();
                    cv.put("active", 0);
                    int pom = db1.update("Trening", cv, "ID = ? ", new String[]{item.getID().toString()});
                    db1.close();
                    if(item.getActive().equals("0"))
                        item.setActive("1");
                    else
                        item.setActive("0");
                }
            }
        });





        return vi;
    }



}
