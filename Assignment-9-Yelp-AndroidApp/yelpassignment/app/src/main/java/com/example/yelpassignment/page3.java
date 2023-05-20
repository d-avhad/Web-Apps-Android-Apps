package com.example.yelpassignment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link page3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class page3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public page3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment page3.
     */
    // TODO: Rename and change types and number of parameters
    public static page3 newInstance(String param1, String param2) {
        page3 fragment = new page3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CardDetails c = (CardDetails) getActivity();
        String name[]=c.getReview_data_name();
        String rating[]=c.getReview_data_rating();
        String date[]=c.getReview_data_date();
        String text[]=c.getReview_data_text();

        View inf = inflater.inflate(R.layout.fragment_page3, container, false);
        TableLayout t_layout = (TableLayout)inf.findViewById(R.id.review_table);

        for(int i=0; i< name.length-1 && name[i]!=null; i++) {
            TableRow row = new TableRow(c);
            //row.setId(Integer.valueOf(i));
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            // set name
            String sourceString ="<b>"+String.valueOf(name[i])+"</b>";
            //mytextview.setText(Html.fromHtml(sourceString));
            TextView s_no = new TextView(c);
            s_no.setSingleLine(false);
            s_no.setWidth(1000);
            s_no.setHeight(400);
            //s_no.setTypeface(s_no.getTypeface(), Typeface.BOLD);
            s_no.setGravity(Gravity.LEFT);

            s_no.setText(Html.fromHtml((sourceString+"<br> Rating:"+rating[i]+"/5<br><br>"+text[i]+"<br><br>"+date[i])));
            s_no.setTextColor(Color.BLACK);




            row.addView(s_no);
           // row.addView(rate);
            if(i!=name.length-2)
                row.setBackgroundResource(R.drawable.review_border);
            t_layout.addView(row,i);

        }


        return inf;

    }
}