package com.rgt.nenad.realgymtrainerv2;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCustomAdapter extends BaseExpandableListAdapter {


    private LayoutInflater inflater;
    private ArrayList<Parent> mParent;

    public MyCustomAdapter(Context context, ArrayList<Parent> parent){
        mParent = parent;
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

        view.setTag(holder);

        //return the entire view
        return view;
    }

    @Override
    //in this method you must set the text to see the children on the list
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {

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

        String pomStr = mParent.get(groupPosition).getArrayChildren().get(childPosition).getSlikaLink();

        ImageView ImageView6 = (ImageView) view.findViewById(R.id.imageViewChild);
        /*if(pomStr == "Grudi")
            ImageView6.setImageResource(R.drawable.grudiposter);
        else if(pomStr.equals("Ruke"))
            ImageView6.setImageResource(R.drawable.rukeposter);
        else if(pomStr.equals("Noge"))
            ImageView6.setImageResource(R.drawable.nogeposter);
        else if(pomStr.equals("Ramena"))
            ImageView6.setImageResource(R.drawable.ramenaposter);
        else if(pomStr.equals("Trbusnjaci"))
            ImageView6.setImageResource(R.drawable.trbusnjaciposter);
        else if(pomStr.equals("Triceps"))
            ImageView6.setImageResource(R.drawable.tricepsposter);
        else if(pomStr.equals("Ledja"))
            ImageView6.setImageResource(R.drawable.ledjaposter);*/


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

// Intentionally put on comment, if you need on click deactivate it
/*  @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder)view.getTag();
        if (view.getId() == holder.button.getId()){

           // DO YOUR ACTION
        }
    }*/


    protected class ViewHolder {
        protected int childPosition;
        protected int groupPosition;
        protected Button button;
    }
}
