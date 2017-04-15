package com.kaine119.revision;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaine119.revision.dataTypes.Topic;
import com.kaine119.revision.services.ReviewAlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;
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
        
        
        
        // Weekly Review
        // TEMPORARY: populate the list from an array resource
        // TODO: actually implement a proper list.
        Resources resources = getResources();
        String[] titles = resources.getStringArray(R.array.review_topic_titles);
        String[] subjects = resources.getStringArray(R.array.review_topic_subjects);
        String[] dates = resources.getStringArray(R.array.reminder_dates);
        TypedArray ta = resources.obtainTypedArray(R.array.review_topic_subjectAvatars);
        Drawable[] images = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            images[i] = ta.getDrawable(i);
        }
        ta.recycle();
        List<Topic> reminders = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            reminders.add(new Topic(titles[i], subjects[i], dates[i], images[i]));
        }
        
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reminder_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ReviewAdapter(reminders));
        
        
        // Recurring notification for prompting
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 15);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReviewAlarmReceiver.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        
        // TEMP: cancel button for reminder
        Button cancelButton = (Button) findViewById(R.id.cancelReminder);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pendingIntent);
            }
        });
    }
    
    
    
    // List of topics due to come up for weekly review
    class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
        
        class ReviewViewHolder extends RecyclerView.ViewHolder {
            TextView titleText;
            TextView subjectText;
            ImageView subjectAvatar;
            
            public ReviewViewHolder(View itemView) {
                super(itemView);
                this.titleText = (TextView) itemView.findViewById(R.id.reminder_title_text);
                this.subjectText = (TextView) itemView.findViewById(R.id.reminder_subject_text);
                this.subjectAvatar = (ImageView) itemView.findViewById(R.id.reminder_subject_avatar);
            }
        }
        
        List<Topic> mTopics;
    
        public ReviewAdapter(List<Topic> topics) {
            mTopics = topics;
        }
    
        @Override
        public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
            return new ReviewViewHolder(v);
        }
    
        @Override
        public void onBindViewHolder(ReviewViewHolder holder, int position) {
            holder.titleText.setText(mTopics.get(position).mTitle);
            holder.subjectText.setText(mTopics.get(position).mSubject);
            holder.subjectAvatar.setImageDrawable(mTopics.get(position).mAvatarLocation);
        }
    
        @Override
        public int getItemCount() {
            return mTopics.size();
        }
    
        
        
    }
    
}
