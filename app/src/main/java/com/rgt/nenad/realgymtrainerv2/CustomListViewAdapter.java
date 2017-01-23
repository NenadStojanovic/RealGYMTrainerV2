package com.rgt.nenad.realgymtrainerv2;

/**
 * Created by Nenad on 23-Jun-16.
 */


        import java.io.Console;
        import java.util.List;
        import java.util.Objects;

        import com.rgt.nenad.realgymtrainerv2.Vezba;

        import android.app.Activity;
        import android.content.Context;
        import android.content.SharedPreferences;
        import android.database.DataSetObserver;
        import android.graphics.Color;
        import android.graphics.drawable.Drawable;
        import android.os.Debug;
        import android.support.v4.content.ContextCompat;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

public class CustomListViewAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<Vezba> items;
    Context Maincontext;

    public CustomListViewAdapter(Activity context, List<Vezba> items) {
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
        final Vezba item = items.get(position);
        final ViewHolder vh;

        if(convertView==null) {
            vi = inflater.inflate(R.layout.item_row, parent,false);
            vh = new ViewHolder((EditText) vi.findViewById(R.id.editTextSet1),(EditText) vi.findViewById(R.id.editTextSet2),(EditText) vi.findViewById(R.id.editTextSet3),(EditText) vi.findViewById(R.id.editTextSet4),(LinearLayout) vi.findViewById(R.id.linearLayout2),(LinearLayout) vi.findViewById(R.id.linearLayout3),(LinearLayout) vi.findViewById(R.id.linearLayout4),(LinearLayout) vi.findViewById(R.id.linearLayout5));
            vi.setTag(vh);

            Log.d("****111**********asd", item.getNaziv().toString());
        }
        else{
            vh = (ViewHolder) vi.getTag();
            Log.d("****222**********asd", item.getNaziv().toString());
        }
        vh.t1 = (TextView) vi.findViewById(R.id.textViewSet1);
        vh.t2 = (TextView) vi.findViewById(R.id.textViewSet2);
        vh.t3 = (TextView) vi.findViewById(R.id.textViewSet3);
        vh.t4 = (TextView) vi.findViewById(R.id.textViewSet4);
        vh.l1 = (LinearLayout) vi.findViewById(R.id.linearLayoutBtn1);
        vh.l2 = (LinearLayout) vi.findViewById(R.id.linearLayoutBtn2);
        vh.l3 = (LinearLayout) vi.findViewById(R.id.linearLayoutBtn3);
        vh.l4 = (LinearLayout) vi.findViewById(R.id.linearLayoutBtn4);
        vh.btnComp = (LinearLayout) vi.findViewById(R.id.linearLayoutBtnComplited);
        vh.child = (LinearLayout) vi.findViewById(R.id.childLayout);
        /*final e1 = (EditText) vi.findViewById(R.id.editTextSet1);
        final EditText e2 = (EditText) vi.findViewById(R.id.editTextSet2);
        final EditText e3 = (EditText) vi.findViewById(R.id.editTextSet3);
        final EditText e4 = (EditText) vi.findViewById(R.id.editTextSet4);
        final LinearLayout lin1 = (LinearLayout) vi.findViewById(R.id.linearLayout2);
        final LinearLayout lin2 = (LinearLayout) vi.findViewById(R.id.linearLayout3);
        final LinearLayout lin3 = (LinearLayout) vi.findViewById(R.id.linearLayout4);
        final LinearLayout lin4 = (LinearLayout) vi.findViewById(R.id.linearLayout5);*/

        vh.t5 = (TextView) vi.findViewById(R.id.NaslovVezbe);

        vh.t1.setText(item.getFirstSet());
        vh.t2.setText(item.getSecondSet());
        vh.t3.setText(item.getThirdSet());
        vh.t4.setText(item.getFourthSet());
        vh.t5.setText(item.getNaziv());

        vh.l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(vh.e1.getText().toString())==0)
                    vh.e1.setText("15");
                vh.lin1.setBackgroundColor(Color.argb(127, 255, 118, 0));

                float weightSum = Float.parseFloat(item.getFirstSet())*Integer.parseInt(vh.e1.getText().toString())+Float.parseFloat(item.getSecondSet())*Integer.parseInt(vh.e2.getText().toString())+
                        Float.parseFloat(item.getThirdSet())*Integer.parseInt(vh.e3.getText().toString())+Float.parseFloat(item.getFourthSet())*Integer.parseInt(vh.e4.getText().toString());
                SharedPreferences sp = Maincontext.getSharedPreferences("AchievementPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putFloat("Weights_Sum", weightSum);
                editor.apply();
                vh.e1.setEnabled(false);
                vh.l1.setEnabled(false);

            }
        });

        vh.l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(vh.e2.getText().toString())==0)
                     vh.e2.setText("12");
                vh.lin2.setBackgroundColor(Color.argb(127, 255, 118, 0));
                vh.e2.setEnabled(false);
                vh.l2.setEnabled(false);
                float weightSum = Float.parseFloat(item.getFirstSet())*Integer.parseInt(vh.e1.getText().toString())+Float.parseFloat(item.getSecondSet())*Integer.parseInt(vh.e2.getText().toString())+
                        Float.parseFloat(item.getThirdSet())*Integer.parseInt(vh.e3.getText().toString())+Float.parseFloat(item.getFourthSet())*Integer.parseInt(vh.e4.getText().toString());
                SharedPreferences sp = Maincontext.getSharedPreferences("AchievementPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putFloat("Weights_Sum", weightSum);
                editor.apply();
            }
        });

        vh.l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(vh.e3.getText().toString())==0)
                      vh.e3.setText("10");
                vh.lin3.setBackgroundColor(Color.argb(127, 255, 118, 0));
                vh.e3.setEnabled(false);
                vh.l3.setEnabled(false);
                float weightSum = Float.parseFloat(item.getFirstSet())*Integer.parseInt(vh.e1.getText().toString())+Float.parseFloat(item.getSecondSet())*Integer.parseInt(vh.e2.getText().toString())+
                        Float.parseFloat(item.getThirdSet())*Integer.parseInt(vh.e3.getText().toString())+Float.parseFloat(item.getFourthSet())*Integer.parseInt(vh.e4.getText().toString());
                SharedPreferences sp = Maincontext.getSharedPreferences("AchievementPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putFloat("Weights_Sum", weightSum);
                editor.apply();

            }
        });

        vh.l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(vh.e4.getText().toString())==0)
                     vh.e4.setText("8");
                vh.lin4.setBackgroundColor(Color.argb(127, 255, 118, 0));
                vh.e4.setEnabled(false);
                vh.l4.setEnabled(false);
                float weightSum = Float.parseFloat(item.getFirstSet())*Integer.parseInt(vh.e1.getText().toString())+Float.parseFloat(item.getSecondSet())*Integer.parseInt(vh.e2.getText().toString())+
                        Float.parseFloat(item.getThirdSet())*Integer.parseInt(vh.e3.getText().toString())+Float.parseFloat(item.getFourthSet())*Integer.parseInt(vh.e4.getText().toString());
                SharedPreferences sp = Maincontext.getSharedPreferences("AchievementPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putFloat("Weights_Sum", weightSum);
                editor.apply();

            }
        });

        vh.btnComp.setTag(vh);
        vh.btnComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int suma=0;

                ViewHolder vh2 = (ViewHolder)v.getTag();
                vh2.child.setBackgroundColor(Color.argb(127, 255, 118, 0));
                suma=Integer.parseInt(vh2.e1.getText().toString()) + Integer.parseInt(vh2.e2.getText().toString()) + Integer.parseInt(vh2.e3.getText().toString()) +Integer.parseInt(vh2.e4.getText().toString());
                double percent = getPercent(45,suma);
                SaveToPref((int) percent,Maincontext,vh2.t5.getText().toString()+"suma");
                if(percent<80){
                    SaveToPref(1,Maincontext,vh2.t5.getText().toString());
                }
                else if(percent>=90){

                    SaveToPref(2,Maincontext,vh2.t5.getText().toString());

                }
                else{
                    SaveToPref(3,Maincontext,vh2.t5.getText().toString());
                }

               // Log.d("****BTN**********asd",vh2.t5.getText().toString());
                vh2.e1.setEnabled(false);
                vh2.l1.setEnabled(false);
                vh2.e2.setEnabled(false);
                vh2.l2.setEnabled(false);
                vh2.e3.setEnabled(false);
                vh2.l3.setEnabled(false);
                vh2.e4.setEnabled(false);
                vh2.l4.setEnabled(false);
                vh.btnComp.setEnabled(false);

            }
        });
        //vh.btnComp.setFocusable(false);

        return vi;
    }

    public void SaveToPref(int p, Context context,String prefName){

        SharedPreferences sp = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(prefName, p);
        editor.apply();

    }

    public double getPercent(int ukupno, int trazeno){
        double pom;
        pom = Math.ceil((100*trazeno)/ukupno);
        return pom;
    }

    public double getFromPercent(int odCega, int kolikoPosto){
        double pom;
        pom = Math.ceil((odCega*kolikoPosto)/100);
        return pom;
    }
}
