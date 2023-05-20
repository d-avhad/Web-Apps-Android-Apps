package com.example.yelpassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

// Referenced from link provided in document:
//https://www.digitalocean.com/community/tutorials/android-recyclerview-swipe-to-delete-undo#code
//https://www.geeksforgeeks.org/swipe-to-delete-and-undo-in-android-recyclerview/

public class ReservationDisplayActivity extends AppCompatActivity {

    private RecyclerView courseRV;
    private TextView no_book;
    private ArrayList<RecyclerInput> recyclerDataArrayList;
    private RecyclerInputViewAdapter recyclerViewAdapter;
    Context mContext;
    private Paint mClearPaint;
    private ColorDrawable mBackground;
    private int backgroundColor;
    private Drawable deleteDrawable;
    private int intrinsicWidth;
    private int intrinsicHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_reservation_display);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SharedPreferences pref = getSharedPreferences("MyBookings", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String name="";
        String[] tmp = new String[5];
        mBackground = new ColorDrawable();
        mContext=ReservationDisplayActivity.this;
        backgroundColor = Color.parseColor("#b80f0a");
        mClearPaint = new Paint();
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        deleteDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_delete);
        intrinsicWidth = deleteDrawable.getIntrinsicWidth();
        intrinsicHeight = deleteDrawable.getIntrinsicHeight();

        courseRV = findViewById(R.id.idRVCourse);
        no_book=findViewById(R.id.no_bookings);
        recyclerDataArrayList = new ArrayList<>();

        int count=1;
        String past="";

        if(pref.contains("list_names")) {

            if(pref.getString("list_names","").length()<1){
                no_book.setVisibility(View.VISIBLE);
                courseRV.setVisibility(View.GONE);
            } else {

            no_book.setVisibility(View.GONE);
            courseRV.setVisibility(View.VISIBLE);

            name = pref.getString("list_names", "");
            tmp = name.split(",");

            // commenting here.  }


            for (int i = 0; i < tmp.length && tmp[i] != "" && tmp != null && tmp[i] != null; i++) {

                //editor.remove(tmp[i]);
                if (pref.contains(tmp[i])) {

                    String str_tmp = pref.getString(tmp[i], "");
                    String[] val_tmp = str_tmp.split(",");
                    String n = val_tmp[0];
                    // past=n;
                    String dat = val_tmp[1];
                    String tim = val_tmp[2];
                    String mai = val_tmp[3];

                    if (!n.matches(past)) {


                        recyclerDataArrayList.add(new RecyclerInput((count) + "  " + n, dat, tim, mai));
                        count++;
                        past = n;

                    }

                }
            }
            // editor.remove("list_names");
            // editor.commit();


            recyclerViewAdapter = new RecyclerInputViewAdapter(recyclerDataArrayList, this);

            LinearLayoutManager manager = new LinearLayoutManager(this);
            courseRV.setLayoutManager(manager);
            courseRV.setAdapter(recyclerViewAdapter);

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                @Override
                public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                    View itemView = viewHolder.itemView;
                    int itemHeight = itemView.getHeight();

                    boolean isCancelled = dX == 0 && !isCurrentlyActive;

                    if (isCancelled) {
                        clearCanvas(c, itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        return;
                    }

                    mBackground.setColor(backgroundColor);
                    mBackground.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    mBackground.draw(c);

                    int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                    int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
                    int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
                    int deleteIconRight = itemView.getRight() - deleteIconMargin;
                    int deleteIconBottom = deleteIconTop + intrinsicHeight;

                    deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
                    deleteDrawable.draw(c);

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                }

                private void clearCanvas(Canvas c, Float left, Float top, Float right, Float bottom) {
                    c.drawRect(left, top, right, bottom, mClearPaint);

                }


                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                    RecyclerInput deletedCourse = recyclerDataArrayList.get(viewHolder.getAdapterPosition());

                    //  Toast.makeText((Context) ReservationDisplayActivity.this,deletedCourse.getTitle().substring(1),Toast.LENGTH_LONG).show();

                    int position = viewHolder.getAdapterPosition();
                    recyclerDataArrayList.remove(viewHolder.getAdapterPosition());
                    recyclerViewAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                    Snackbar.make(courseRV, "Removing Existing Reservation", Snackbar.LENGTH_LONG).show();
                    if (pref.contains((deletedCourse.getTitle().toString()).substring(1).trim())) {
                        editor.remove((deletedCourse.getTitle().toString()).substring(1).trim());
                    }

                    String li = pref.getString("list_names", "");
                    li = li.replaceAll((deletedCourse.getTitle().toString()).substring(1).trim(), "");
                    String [] q= li.split(",");
                    String new_str="";
                    for(int i=0; i<q.length; i++){

                        if(q[i]!=null && !q[i].matches(",") && !q[i].matches("")){
                            new_str=new_str+q[i]+"," ;

                        }
                    }
                   // Toast.makeText((Context) ReservationDisplayActivity.this,new_str,Toast.LENGTH_LONG).show();

                    editor.remove("list_names");
                    editor.putString("list_names", new_str);
                    editor.commit();

                    if(pref.getString("list_names","").length()<1){
                        no_book.setVisibility(View.VISIBLE);
                        courseRV.setVisibility(View.GONE);
                    }
                }

            }).attachToRecyclerView(courseRV);


        }  } else { no_book.setVisibility(View.VISIBLE);
                    courseRV.setVisibility(View.GONE);}


    }

    @Override
    public boolean onSupportNavigateUp() {
        //Toast.makeText(getApplicationContext(),"You clicked bookings.",Toast.LENGTH_SHORT).show();
        finish();
        return true;
    }
}