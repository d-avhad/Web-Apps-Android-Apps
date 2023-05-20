package com.example.yelpassignment;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link page1#newInstance} factory method to
 * create an instance of this fragment.
 */

// modal reference: https://mkyong.com/android/android-custom-dialog-example/
// Date picker: https://www.geeksforgeeks.org/how-to-disable-previous-or-future-dates-in-datepicker-in-android/

public class page1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "cat";
    private static final String TAG = CardDetails.class.getName();
    String m_email="";
    String m_date="";
    String m_time=" ";
    //String m_email_patt ="\[a-zA-Z0-9\._\-\]+@[a-z]+\.+[a-z]+";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public page1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment page1.
     */
    // TODO: Rename and change types and number of parameters
    public static page1 newInstance(String param1, String param2) {
        page1 fragment = new page1();
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

    @SuppressLint("SuspiciousIndentation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        CardDetails c = (CardDetails) getActivity();
        SharedPreferences pref = c.getSharedPreferences("MyBookings", 0);
        SharedPreferences.Editor editor = pref.edit();

        String title=c.sendTitle();
        String cat= c.sendCat();
        String addr=c.sendAdd();
        addr=addr.replaceAll("\\[", "").replaceAll("\\]","");

        String price=c.sendPrice();
        String phone=c.sendPhone();
        String st=c.sendStatus();
        String[] img_str=c.getImg_str();
        int len=img_str.length;

        if (st.equals("false")){
            st="Closed";
        } else{
            st="Open Now";
        }
        String info=c.sendInfo();

        View inf = inflater.inflate(R.layout.fragment_page1, container, false);
        TextView tcat = (TextView) inf.findViewById(R.id.cat_input);
        tcat.setText(cat);

        TextView tadd = (TextView) inf.findViewById(R.id.addr_input);
        tadd.setText(addr);

        TextView pr = (TextView) inf.findViewById(R.id.price_input);
        pr.setText(price);

        TextView ph = (TextView) inf.findViewById(R.id.phone_input);
        ph.setText(phone);

        TextView stat= (TextView) inf.findViewById(R.id.status_input);
        if (st.equals("Closed")){
            stat.setTextColor(Color.RED);
        }
        else {
            stat.setTextColor(Color.GREEN);
        }
        stat.setText(st);

       TextView minf= (TextView) inf.findViewById(R.id.moreinfo_input);
       minf.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Uri bus_uri = Uri.parse(info);
               Intent intent = new Intent(Intent.ACTION_VIEW, bus_uri);
               startActivity(intent);
           }
       });
       // minf.setText(info);

        ImageView img1= (ImageView) inf.findViewById(R.id.img1);
        ImageView img2= (ImageView) inf.findViewById(R.id.img2);
        ImageView img3= (ImageView) inf.findViewById(R.id.img3);

        if(len==1) {

            Glide.with(this)
                    .load((img_str[0]))
                    .placeholder(R.drawable.reservation).fitCenter()
                    .error(R.drawable.reservation)
                    .into(img1);

        } else if (len==2) {
            Glide.with(this)
                    .load((img_str[0]))
                    .placeholder(R.drawable.reservation).fitCenter()
                    .error(R.drawable.reservation)
                    .into(img1);
            Glide.with(this)
                    .load((img_str[1]))
                    .placeholder(R.drawable.reservation).fitCenter()
                    .error(R.drawable.reservation)
                    .into(img2);


        } else if (len==3) {

            Glide.with(this)
                    .load((img_str[0]))
                    .placeholder(R.drawable.reservation)
                    .error(R.drawable.reservation)
                    .into(img1);
            Glide.with(this)
                    .load((img_str[1]))
                    .placeholder(R.drawable.reservation)
                    .error(R.drawable.reservation)
                    .into(img2);
            Glide.with(this)
                    .load((img_str[2]))
                    .placeholder(R.drawable.reservation)
                    .error(R.drawable.reservation)
                    .into(img3);

        }


        Button b= inf.findViewById(R.id.reserve_button);

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

               // String m_email="";

                // custom dialog
                final Dialog dialog = new Dialog(c);
                dialog.setContentView(R.layout.modal);
                dialog.getWindow().setLayout(1100,1200);
                dialog.getWindow().setGravity(Gravity.CENTER);
                TextView text = (TextView) dialog.findViewById(R.id.modal_title);
                text.setText(title);

                EditText email=dialog.findViewById(R.id.modal_email_input);



                // Date input ui
                TextView d=dialog.findViewById(R.id.modal_date_input);

                d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar ca = Calendar.getInstance();
                        int year = ca.get(Calendar.YEAR);
                        int month = ca.get(Calendar.MONTH);
                        int day = ca.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                c, new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        d.setText((monthOfYear + 1)+ "-" + dayOfMonth  + "-" + year);

                                    }
                                },
                                year, month, day);

                        datePickerDialog.getDatePicker().setMinDate(ca.getTimeInMillis());
                        datePickerDialog.show();
                    }
                });


                // time input ui

                TextView t= dialog.findViewById(R.id.modal_time_input);
                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Calendar ca = Calendar.getInstance();
                        int hour = ca.get(Calendar.HOUR_OF_DAY);
                        int minute = ca.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(c,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        t.setText(hourOfDay + ":" + minute);
                                    }
                                }, hour, minute, false);
                        timePickerDialog.show();
                    }
                });


                TextView cancel_but =  dialog.findViewById(R.id.modal_cancel);
                // if button is clicked, close the custom dialog
                cancel_but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });



              //  Toast.makeText(c,m_email,Toast.LENGTH_LONG).show();



                TextView sub_but=dialog.findViewById(R.id.modal_submit);
              //  m_email=m_email.trim();


                sub_but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        m_email=email.getText().toString();
                        m_date=d.getText().toString();
                        m_time=t.getText().toString();
                        String regex = "^(.+)@(.+)$";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(m_email);
                        String []temp=m_time.split(":");
                        int hour=0;
                        int min=0;
                        if(temp[0]!=""){
                        hour=Integer.parseInt(temp[0]);
                        min=Integer.parseInt(temp[1]);}


                        if (!(matcher.matches()))
                        {
                            Toast.makeText(c,"Invalid Email Address",Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }

                        else if((hour<10 || hour>17)) {

                            Toast.makeText(c,"Time should be 10am and 5pm",Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        }
                        else {

                            String combine=title+","+m_date+","+m_time+","+m_email;
                            editor.putString(title, combine);
                            if (pref.contains("list_names")) {

                                String str=pref.getString("list_names","");
                                str=str+","+title;
                                editor.putString("list_names",str);


                            } else
                            {
                                editor.putString("list_names",title);
                            }
                           // editor.remove("list_names");
                            editor.commit();
                            Toast.makeText(c,"Reservation Booked",Toast.LENGTH_LONG).show();
                            dialog.dismiss();


                        }


                    }
                });



                // set the custom dialog components - text, image and button
                //TextView text = (TextView) dialog.findViewById(R.id.email_label);
               // text.setText(title);
                dialog.show();
            }
        });


       // return inflater.inflate(R.layout.fragment_page1, container, false);
return inf;

    }


}