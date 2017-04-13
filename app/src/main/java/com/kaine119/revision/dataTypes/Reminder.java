package com.kaine119.revision.dataTypes;


import android.graphics.drawable.Drawable;

public class Reminder {
    public String mTitle;
    public String mSubject;
    public String mDate;
    public Drawable mAvatarLocation;
    
    public Reminder(String title, String subject, String date, Drawable avatarLocation) {
        mTitle = title;
        mSubject = subject;
        mDate = date;
        mAvatarLocation = avatarLocation;
    }
}
