package com.android.cameraapp.util;

import android.widget.TextView;

import com.android.cameraapp.R;

public class FollowingTimeResolveAdapter {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    public static String getFollowingDate(long time, String date, TextView view, int type) {
        long three_days = 259200000;
        long time_elapsed = System.currentTimeMillis() - time;
        if (time_elapsed > three_days) {
            return date;
        } else {
            if (type == 1) {
                return view.getContext().getString(R.string.now_follows_you);
            }
            else if (type == 2) {
                return view.getContext().getString(R.string.you_are_now_following);
            }
            else if (type == 3) {
                return view.getContext().getString(R.string.has_liked);
            }
            else return "asdsa";
        }
        //259200000
    }
}
