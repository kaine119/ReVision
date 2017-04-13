package com.kaine119.revision;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaine119.revision.dataTypes.Reminder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        
        
        
        // Reminders
        // TEMPORARY: populate the list from an array resource
        // TODO: actually implement a proper list.
        Resources resources = getResources();
        String[] titles = resources.getStringArray(R.array.reminder_titles);
        String[] subjects = resources.getStringArray(R.array.reminder_subjects);
        String[] dates = resources.getStringArray(R.array.reminder_dates);
        TypedArray ta = resources.obtainTypedArray(R.array.reminder_subjectAvatars);
        Drawable[] images = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            images[i] = ta.getDrawable(i);
        }
        ta.recycle();
        List<Reminder> reminders = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            reminders.add(new Reminder(titles[i], subjects[i], dates[i], images[i]));
        }
        
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reminder_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ReminderAdapter(reminders));
        
        
    }
    
    
    

    class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {
        
        class ReminderViewHolder extends RecyclerView.ViewHolder {
            TextView titleText;
            TextView subjectText;
            ImageView subjectAvatar;
            TextView dateText;
            
            public ReminderViewHolder(View itemView) {
                super(itemView);
                this.titleText = (TextView) itemView.findViewById(R.id.reminder_title_text);
                this.subjectText = (TextView) itemView.findViewById(R.id.reminder_subject_text);
                this.subjectAvatar = (ImageView) itemView.findViewById(R.id.reminder_subject_avatar);
                this.dateText = (TextView) itemView.findViewById(R.id.reminder_date_text);
            }
        }
        
        List<Reminder> mReminders;
    
        public ReminderAdapter(List<Reminder> reminders) {
            mReminders = reminders;
        }
    
        @Override
        public ReminderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
            return new ReminderViewHolder(v);
        }
    
        @Override
        public void onBindViewHolder(ReminderViewHolder holder, int position) {
            holder.titleText.setText(mReminders.get(position).mTitle);
            holder.subjectText.setText(mReminders.get(position).mSubject);
            holder.subjectAvatar.setImageDrawable(mReminders.get(position).mAvatarLocation);
            holder.dateText.setText(mReminders.get(position).mDate);
        }
    
        @Override
        public int getItemCount() {
            return mReminders.size();
        }
    
        
        
    }
    
}
