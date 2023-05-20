package com.example.yelpassignment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.time.Instant;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.util.ArrayList;


//references:
//for volley implementation: https://abhiandroid.com/programming/volley
// setting cal icon referenced from stack overflow: https://stackoverflow.com/questions/43766335/move-up-navigation-icon-in-toolbar-to-the-right


public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private RequestQueue mRequestQueue;
    private RequestQueue mRequestQueue1;
    private RequestQueue mRequestQueue2;
    private RequestQueue m1;

    private StringRequest mStringRequest;
    private StringRequest mStringRequest1;
    private StringRequest mStringRequest2;
    private StringRequest ms1;
    AutoCompleteTextView key_word;

    ArrayList<String>  auto_str=new ArrayList<>();
    String []final_auto_str={" "};
    //private String url = "https://assignment-8-368122.wl.r.appspot.com/searchFor/yelpAPI/";

    String keyw;
    int dist;
    String cat_val;
    String loc_val;
    private int count=0;


 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

     // setting calendar icon for bookings.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());

        imageView.setImageResource(R.drawable.reservation);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);

        layoutParams.bottomMargin=30;
        layoutParams.topMargin=20;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);
        actionBar.setHomeAsUpIndicator(R.drawable.reservation);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getApplicationContext(),"You clicked bookings.",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity2.this,ReservationDisplayActivity.class);
                startActivity(intent);
            }
        });


        Button clr = findViewById(R.id.clear_button);
        Button sub=  findViewById(R.id.submit_button);
     //String []COUNTRIES={"hello","world"};

    // AutoCompleteTextView key_word=findViewById(R.id.keyword_input);
     //key_word.setThreshold(2);
     key_word=findViewById(R.id.keyword_input);

     key_word.addTextChangedListener(new TextWatcher() {
         public void onTextChanged(CharSequence cs, int s, int b, int c) {
             Log.i("Key:", cs.toString());
             if(cs.toString().length()>2){

                 callAutoComplete();


             }
         }
         public void afterTextChanged(Editable editable) { }
         public void beforeTextChanged(CharSequence cs, int i, int j, int
                 k) { }
     });


  //   ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
  //           android.R.layout.select_dialog_item, final_auto_str);
   //      key_word.setAdapter(adapter);
  //   adapter.setNotifyOnChange(true);








        EditText distance=findViewById(R.id.distance_input);
        Spinner cat=findViewById(R.id.category_input);
        EditText loc=findViewById(R.id.location_input);
        CheckBox ch=findViewById(R.id.checkbox_input);

        //reset function
        clr.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                key_word.setText("");
                distance.setText("");
                cat.setSelection(0);
                loc.setText("");
                loc.setEnabled(true);
                loc.setVisibility(View.VISIBLE);
                ch.setChecked(false);
                TableLayout t_layout = (TableLayout) findViewById(R.id.result_table);
                t_layout.removeAllViews();

            }
        });  // reset function ends.

        //checkbox toggle operation
        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(ch.isChecked()) {
                    loc.setText("");
                    loc.setEnabled(false);
                    loc.setVisibility(View.INVISIBLE);

                }
                else {
                    loc.setText("");
                    loc.setEnabled(true);
                    loc.setVisibility(View.VISIBLE);

                }
            }
        }); //checkbox ends.

        //submit button
        sub.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                TableLayout tableLayout=(TableLayout) findViewById(R.id.result_table);
                tableLayout.removeAllViews();

                if(key_word.getText().toString().matches("")){
                    key_word.setError("This Field is required");
                }
                else if(distance.getText().toString().matches("")){
                    distance.setError("This Field is required");
                }
                else if(loc.isEnabled() && loc.getText().toString().matches("") && !ch.isChecked()) {
                   loc.setError("This field is required");
                }

                else {  // main else

                    keyw=key_word.getText().toString();
                    dist= Integer.parseInt(distance.getText().toString());
                    cat_val=cat.getSelectedItem().toString();

                    if(ch.isChecked() && !loc.isEnabled()){
                        // call ipinfo
                        getipInfoData(keyw,dist,cat_val);

                    }

                    else {
                        loc_val=loc.getText().toString();
                        JSONObject data = new JSONObject();
                        try {
                            data.put("keyword",keyw);
                            data.put("distance",dist);
                            data.put("category",cat_val);
                            data.put("latitude","");
                            data.put("longitude","");
                            data.put("loc",loc_val);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String str_data=data.toString();
                        sendData(str_data);

                    }


                } // end main else



            }
        });  // end submit


    } // oncreate ends.


    private  String[] callAutoComplete(){

        //auto_str.clear();
        m1 = Volley.newRequestQueue(this);

        ms1 = new StringRequest(Request.Method.GET,"https://assignment-8-368122.wl.r.appspot.com/autocomplete/"+key_word.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "AutoTextView:" + response.toString());

                try {
                    String temp_data="";
                    String temp_data1="";
                    JSONObject a_data=new JSONObject(response.toString());

                    if(a_data.has("categories")) {
                        temp_data = a_data.getString("categories");
                        JSONArray arr=new JSONArray(temp_data);

                        for(int i=0; i<arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            auto_str.add(obj.getString("title"));
                            Log.i(TAG, "value" + auto_str.get(i));

                        }
                    } // end if


                    if(a_data.has("terms")){

                        temp_data1=a_data.getString("terms");
                        JSONArray new_arr=new JSONArray(temp_data1);

                        for(int i=0; i<new_arr.length(); i++) {
                            JSONObject obj = new_arr.getJSONObject(i);
                            auto_str.add(obj.getString("text"));
                            Log.i(TAG, "value" + auto_str.get(i));

                        }


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG, "Error :" + error.toString());
            }
        });

        m1.add(ms1);
        String [] auto_res=new String[auto_str.size()];

        for(int i=0; i<auto_str.size();i++){
            auto_res[i]=auto_str.get(i);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, auto_str);
        key_word.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        key_word.setThreshold(2);



        return auto_res;

    }


    private void getipInfoData(String keyw, int dist, String cat_val){

        mRequestQueue1 = Volley.newRequestQueue(this);

        mStringRequest1 = new StringRequest(Request.Method.GET,"https://ipinfo.io/json?token=your token", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject data = new JSONObject();
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    String loc= obj.getString("loc");
                    String[] loc1=loc.split(",");
                    String lat=loc1[0];
                    String lng=loc1[1];

                    data.put("keyword",keyw);
                    data.put("distance",dist);
                    data.put("category",cat_val);
                    data.put("latitude",lat);
                    data.put("longitude",lng);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String str_data=data.toString();
                sendData(str_data);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG, "Error :" + error.toString());
            }
        });

        mRequestQueue1.add(mStringRequest1);

    }//getipnfo ends









    private void sendData(String str_data) {

        String url = "https://assignment-8-368122.wl.r.appspot.com/searchFor/yelpAPI/"+str_data;
        //RequestQueue initialized
         mRequestQueue = Volley.newRequestQueue(this);
        //String Request initialized
         mStringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                //EditText e=findViewById(R.id.sample_text);
                //e.setText(response.toString());
                Log.i(TAG, "Response :" + response.toString().length());
                try {
                    fillData(response.toString());
                } catch (JSONException e) {

                    //Log.i(TAG, "Response--" + response.toString());
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG, "Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);

    } //sendData ends.


   private void  fillData(String res_str) throws JSONException {

       //WebView w=findViewById(R.id.web_view);
       JSONArray jsonArray = new JSONArray(res_str);
       String str="";
       String st="";
      // str="<table style='width:100% ; border-collapse: collapse; background-color:rgb(211, 211, 211)'>";

       TableLayout t_layout = (TableLayout) findViewById(R.id.result_table);

       for(int i=0; i<jsonArray.length(); i++) {
           JSONObject obj = jsonArray.getJSONObject(i);
           String t_url = obj.getString("img_url");
           String bname=obj.getString("bus_name");
           String rating=obj.getString("rating");
           int dist=obj.getInt("dist");
           //str=str+"<tr style='border-bottom: 2pt solid black;'><td style='text-align: center'>"+(i+1)+"</td><td><img src='"+t_url+"' width='100' height='90'  alt='No Image' /></td><td style='text-align: center' onclick='javascript:myfunc("+i+")'>"+bname+"</td><td>"+rating+"</td><td style='text-align: center'>"+dist+"</td></tr>";

           TableRow row= new TableRow(this);
           row.setId(Integer.valueOf(i));
           //View.OnClickListener onClickListener =businessClick() ;
           row.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   TableRow tr = (TableRow) view;
                   String id="";
                   int val=tr.getId();
                //   Toast.makeText(getApplicationContext(), "Row" +val, Toast.LENGTH_LONG).show();
                   try {
                        id=obj.getString("id");
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
                   getBusinessDetails(id);


               }
           });
           TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
           row.setLayoutParams(lp);

           TextView s_no = new TextView(this);
           s_no.setWidth(200);
           s_no.setHeight(250);
           s_no.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

           ImageView img_url=new ImageView(this);
         //  img_url.setWidth(300);
        //   img_url.setHeight(250);
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               img_url.setForegroundGravity(Gravity.CENTER);
           }


           TextView name=new TextView(this);
           name.setWidth(300);
           name.setHeight(250);
           name.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

           TextView rate=new TextView(this);
           rate.setWidth(200);
           rate.setHeight(250);
           rate.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

           TextView distance=new TextView(this);
           distance.setWidth(150);
           distance.setHeight(250);
           distance.setGravity(Gravity.CENTER);


          s_no.setText(String.valueOf(i+1));
         //img_url.setText(String.valueOf(t_url));
          name.setText(String.valueOf(bname));
          rate.setText( String.valueOf(rating));
          distance.setText(String.valueOf(dist));

          // Instant Picasso = null;

           Picasso.get().load(t_url.toString()).resize(200, 200).centerCrop(Gravity.CENTER).placeholder(R.drawable.reservation).into(img_url);


           row.addView(s_no);
           row.addView(img_url);
           row.addView(name);
           row.addView(rate);
           row.addView(distance);

          // row.setBackgroundResource(R.color.grey);
           row.setBackgroundResource(R.drawable.bottom_border);
           t_layout.addView(row,i);



       }
     //  str=str+"</table><script>function myfunc(i){"+st+"=i}</script>";
       //w.loadData(str,"text/html","utf-8");
      // w.
       //Toast.makeText(getApplicationContext(), "Response :" + st.toString(), Toast.LENGTH_LONG).show();



    }//fillData ends




    private void getBusinessDetails(String id) {


        String url ="https://assignment-8-368122.wl.r.appspot.com/searchFor/cardDetails/"+id;
        //RequestQueue initialized
        mRequestQueue2 = Volley.newRequestQueue(this);
        //String Request initialized
        mStringRequest2 = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                //EditText e=findViewById(R.id.sample_text);
                //e.setText(response.toString());
                Log.i(TAG, "Response :" + response.toString());
                Intent intent=new Intent(MainActivity2.this,CardDetails.class);
                intent.putExtra("res_data",response.toString());
                startActivity(intent);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG, "Error :" + error.toString());
            }
        });

        mRequestQueue2.add(mStringRequest2);


    }



}






