package com.rgt.nenad.realgymtrainerv2;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyCustomAdapter extends BaseExpandableListAdapter {


    private LayoutInflater inflater;
    private ArrayList<Parent> mParent;
    private Context myContext;

    public MyCustomAdapter(Context context, ArrayList<Parent> parent){
        mParent = parent;
        myContext = context;
        inflater = LayoutInflater.from(context);

    }


    @Override
    //counts the number of group/parent items so the list knows how many times calls getGroupView() method
    public int getGroupCount() {
        return mParent.size();
    }

    @Override
    //counts the number of children items so the list knows how many times calls getChildView() method
    public int getChildrenCount(int i) {
        return mParent.get(i).getArrayChildren().size();
    }

    @Override
    //gets the title of each parent/group
    public Object getGroup(int i) {
        return mParent.get(i).getTitle();
    }

    @Override
    //gets the name of each item
    public Object getChild(int i, int i1) {
        return mParent.get(i).getArrayChildren().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    //in this method you must set the text to see the parent/group on the list
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {

        ViewHolder holder = new ViewHolder();
        holder.groupPosition = groupPosition;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_parent, viewGroup,false);
        }

        TextView textView = (TextView) view.findViewById(R.id.list_item_text_view);
        textView.setText(getGroup(groupPosition).toString());
       //

        String pomStr = mParent.get(groupPosition).getTipVezbe();
        int alpha = 127;
        if(pomStr == "Grudi")
            textView.setBackgroundColor(Color.argb(alpha,44,171,222));
        else if(pomStr.equals("Biceps"))
            textView.setBackgroundColor(Color.argb(alpha, 175, 92, 73));
        else if(pomStr.equals("Noge"))
            textView.setBackgroundColor(Color.argb(alpha, 136, 76, 227));
        else if(pomStr.equals("Ramena"))
            textView.setBackgroundColor(Color.argb(alpha, 248, 242, 69));
        else if(pomStr.equals("Trbusnjaci")){
            //textView.setBackgroundColor(Color.argb(alpha,108,166,105));
        }

        else if(pomStr.equals("Triceps"))
            textView.setBackgroundColor(Color.argb(alpha, 31, 166, 146));
        else if(pomStr.equals("Ledja"))
            textView.setBackgroundColor(Color.argb(alpha, 242, 67, 176));


        view.setTag(holder);

        //return the entire view
        return view;
    }

    @Override
    //in this method you must set the text to see the children on the list
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {

        ViewHolder holder = new ViewHolder();
        holder.childPosition = childPosition;
        holder.groupPosition = groupPosition;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_child, viewGroup,false);
        }

        TextView textView1 = (TextView) view.findViewById(R.id.list_item_text_child);
        textView1.setText(mParent.get(groupPosition).getArrayChildren().get(childPosition).getTitle());


        TextView textView2 = (TextView) view.findViewById(R.id.FirstSetVal);
        textView2.setText(mParent.get(groupPosition).getArrayChildren().get(childPosition).getFirstSet());

        TextView textView3 = (TextView) view.findViewById(R.id.SecondSetVal);
        textView3.setText(mParent.get(groupPosition).getArrayChildren().get(childPosition).getSecondSet());

        TextView textView4 = (TextView) view.findViewById(R.id.ThirdSetVal);
        textView4.setText(mParent.get(groupPosition).getArrayChildren().get(childPosition).getThirdSet());

        TextView textView5 = (TextView) view.findViewById(R.id.FourthSetVal);
        textView5.setText(mParent.get(groupPosition).getArrayChildren().get(childPosition).getFourthSet());

        LinearLayout videoBtn = (LinearLayout) view.findViewById(R.id.VideoBtnLayout);
        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(haveNetworkConnection()) {
                    String URL = null;
                    int LineNumber = mParent.get(groupPosition).getArrayChildren().get(childPosition).getBrURL();
                    if (LineNumber == -1) {
                        Toast.makeText(myContext, "This exercise iz added by user, so there is no video demonstration!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {

                        BufferedReader br = new BufferedReader(new InputStreamReader(myContext.getAssets().open("vezbevideo.txt")));
                        for (int j = 0; j < LineNumber; ++j) {
                            br.readLine();


                        }

                        URL = br.readLine();


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Uri uri = Uri.parse(URL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    myContext.startActivity(intent);
                }
                else{
                    Toast.makeText(myContext, "There is no Internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String pomStr = mParent.get(groupPosition).getArrayChildren().get(childPosition).getSlikaLink();

        ImageView ImageView6 = (ImageView) view.findViewById(R.id.imageViewChild);
        if(pomStr == "Grudi")
            ImageView6.setImageResource(R.drawable.grudiposter2);
        else if(pomStr.equals("Biceps"))
            ImageView6.setImageResource(R.drawable.bicepsposter2);
        else if(pomStr.equals("Noge"))
            ImageView6.setImageResource(R.drawable.nogeposter2);
        else if(pomStr.equals("Ramena"))
            ImageView6.setImageResource(R.drawable.ramenaposter2);
        else if(pomStr.equals("Trbusnjaci")){
            //ImageView6.setImageResource(R.drawable.trbusnjaciposter1);
        }

        else if(pomStr.equals("Triceps"))
            ImageView6.setImageResource(R.drawable.tricepsposter2);
        else if(pomStr.equals("Ledja"))
            ImageView6.setImageResource(R.drawable.ledjaposter2);


        view.setTag(holder);

        //return the entire view
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        /* used to make the notifyDataSetChanged() method work */
        super.registerDataSetObserver(observer);
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) myContext.getSystemService(myContext.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    protected class ViewHolder {
        protected int childPosition;
        protected int groupPosition;
        protected Button button;
    }
}
