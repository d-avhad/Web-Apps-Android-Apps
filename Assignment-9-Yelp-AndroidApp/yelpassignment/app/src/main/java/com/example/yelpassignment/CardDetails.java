package com.example.yelpassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


// to create tabbed activity reference taken from:
//https://codedocu.com/Google/Android/Development/Android-Controls/Android-Code_colon_-Tabbed-Activity-with-different-fragments?2631
public class CardDetails extends AppCompatActivity {
    private static final String TAG = CardDetails.class.getName();
    private String res_data;
    String title="";
    String cat="";
    String addr="";
    String price="";
    String phone="";
    String status="";
    String info="";
    String img_url="";
    String[]img_str;
    String [] review_data_name=new String[4];
    String [] review_data_rating=new String[4];
    String [] review_data_text=new String[4];
    String [] review_data_date=new String[4];

    Double lat=0.0;
    Double lng=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_card_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            res_data = data.getString("res_data");
           // Toast.makeText(getApplicationContext(), "Resp :" , Toast.LENGTH_LONG).show();//display the response on screen
        }

        try {
            //get name
            JSONObject obj = new JSONObject(res_data.toString());
            title= obj.getString("name");
            lat=obj.getDouble("lat");
            lng=obj.getDouble("lng");


            // get review data
            String tmp= obj.getString("review_data");
           // tmp=tmp.replaceAll("\\[", "").replaceAll("\\]","");

            JSONArray arr=new JSONArray(tmp);
            getReviewData(arr);


            //get category
            String temp=obj.getString("category");
            if(temp.matches("")){
                cat="N/A";
            } else {
                JSONArray jsonArray = new JSONArray(temp);
                cat= parseDetails(jsonArray);
            }

            //get address
            addr=obj.getString("address");
            if (addr.matches("")) {
                addr = "N/A";
            } else {
                addr=addr.replace("\"", "");
            }

            // get price
            if(obj.has("price")) {
                price = obj.getString("price");
                if (price.matches("")) {
                    price = "N/A";
                } else {
                    price = obj.getString("price");
                }
            } else {
                price="N/A";
            }

            // get phone
            if(obj.has("phone")) {
            phone= obj.getString("phone");
            if(phone.matches("")){
                phone="N/A";
            } } else {
                phone="N/A";
            }

            //get status
            if(obj.has("stat")) {
                String stat_str = obj.getString("stat");
                if (stat_str.matches("")) {
                    status = "N/A";
                } else {
                    JSONArray arr1 = new JSONArray(stat_str);
                    JSONObject obj1 = arr1.getJSONObject(0);
                    status = obj1.getString("is_open_now");
                    // Log.i(TAG, "***RES*** :" +status);
                }
            } else status="N/A";

            if(obj.has("business_url")){
            info= obj.getString("business_url");
            if(info.matches("")){
                info="N/A";
            } } else {
                info="N/A";
            }

            img_url=obj.getString("img_url");


            img_url=img_url.replaceAll("\\[", "").replaceAll("\\]","");
            img_url=img_url.replaceAll("\\\\", "");
            img_url=img_url.replace("\"", "");
            img_str=img_url.split(",");
            Log.i(TAG, "***RES*** :" +img_str[0]+img_str[1]);





    } catch (JSONException e) {
            e.printStackTrace();
        }

        getSupportActionBar().setTitle(title);
        setContentView(R.layout.activity_card_details);

        Bundle bundle = new Bundle();
        bundle.putString("addr",addr);
        bundle.putString("price",price);
        bundle.putString("phone",phone);
        bundle.putString("bus_status",status);
        bundle.putString("cat",cat);
        bundle.putString("bus_info",info);

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if (position==0){
                            tab.setText("BUSINESS DETAILS ");
                        }
                        else if (position==1){
                            tab.setText("MAP LOCATION");
                        }
                        else if (position==2){
                            tab.setText("REVIEWS");
                        }

                    }
                }).attach();




    }  //oncreate ends

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    //and this to handle actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.facebook) {
            //Toast.makeText(getApplicationContext(),"facebook.",Toast.LENGTH_SHORT).show();

            String url_str="http://www.facebook.com/sharer/sharer.php?u="+info;
            Uri bus_uri = Uri.parse(url_str);
            Intent intent = new Intent(Intent.ACTION_VIEW, bus_uri);
            startActivity(intent);
        }

        if (id == R.id.twitter) {
            String url_str="https://twitter.com/intent/tweet?text=Check%20"+title+"%20on%20Yelp%0A&url="+info;
            Uri bus_uri = Uri.parse(url_str);
            Intent intent = new Intent(Intent.ACTION_VIEW, bus_uri);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onSupportNavigateUp() {
        //Toast.makeText(getApplicationContext(),"You clicked bookings.",Toast.LENGTH_SHORT).show();
        finish();
        return true;
    }


    private String parseDetails(JSONArray jsonArray) throws JSONException {

        String res_str="";
        for(int i=0; i<jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            res_str = res_str+ obj.getString("title");
            res_str=res_str+" | ";


        }

        res_str=res_str.substring(0, res_str.length() - 2);

        return res_str;

    } //parse ends



    private void getReviewData(JSONArray arr) throws  JSONException{


        Log.i(TAG, "***Array*** :" +arr.toString());

        String res_str="";
        String tmp="";
        for(int i=0; i<arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            //Log.i(TAG, "***str*** :" +obj.toString());

           tmp=obj.getString("user");
           JSONObject tmp_obj=new JSONObject(tmp);
           review_data_name[i]=tmp_obj.getString("name");
            Log.i(TAG, "***name*** :" +review_data_name[i]);

           review_data_rating[i]= obj.getString("rating");
           review_data_text[i]= obj.getString("text");
           review_data_date[i]= obj.getString("time_created");
           review_data_date[i]=(review_data_date[i].split(" "))[0];


        }


    }


    public String sendCat(){
        return cat;
    }
    public String sendAdd(){
        return addr;
    }
    public String sendPrice(){
        return price;
    }
    public String sendPhone(){
        return phone;
    }
    public String sendStatus(){
        return status;
    }
    public String sendInfo(){
        return info;
    }

    public String[] getImg_str() {
        return img_str;
    }


    public String sendTitle() {
        return title;
    }

    public String[] getReview_data_date() {
        return review_data_date;
    }

    public String[] getReview_data_name() {
        return review_data_name;
    }

    public String[] getReview_data_rating() {
        return review_data_rating;
    }

    public String[] getReview_data_text() {
        return review_data_text;
    }

      public double getlat() {
        return lat;
     }

    public double getlng() {
        return lng;
    }
}