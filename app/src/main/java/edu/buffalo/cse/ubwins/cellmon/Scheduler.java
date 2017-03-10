/**
 * Created by Gautam on 7/26/16.
 * MBP111.0138.B16
 * agautam2@buffalo.edu
 * University at Buffalo, The State University of New York.
 * Copyright © 2016 Gautam. All rights reserved.
 */

package edu.buffalo.cse.ubwins.cellmon;

import android.content.Context;
import android.util.Log;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.*;


public class Scheduler
{
    ScheduleIntentReceiver scheduleIntentReceiver = new ScheduleIntentReceiver();
    LocationFinder locationFinder;
    private static final String TAG = "[CELMON-SCHEDULER]";
    final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public static ScheduledFuture<?> beeperHandle=null;
    public static ScheduledFuture<?> gpsHandle=null;

    public void beep(final Context context)
    {
//        locationFinder = new LocationFinder(context);
        final Runnable beeper = new Runnable()
        {
            public void run()
            {
                //Log.v(LOG, "BEEP");
                try {
                    scheduleIntentReceiver.onScheduleIntentReceiver(context);
                }
                catch (Exception e)
                {
                    Log.e(TAG,"error in executing: It will no longer be run!: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
//        final Runnable gpsBeeper = new Runnable()
//        {
//            public void run()
//            {
//                try {
//                    locationFinder.getLocation();
//                    scheduleIntentReceiver.onScheduleGPS(context);
//                }
//                catch (Exception e)
//                {
//                    Log.e(TAG,"error in executing: It will no longer be run!: " + e.getMessage());
//                    e.printStackTrace();
//                }
//            }
//        };
        beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, 3, SECONDS);
//        gpsHandle = scheduler.scheduleAtFixedRate(gpsBeeper, 0, 15, MINUTES);

    }
    
    public static void stopScheduler()
    {
        scheduler.schedule(new Runnable()
        {
            public void run()
            {
                beeperHandle.cancel(true);
//                gpsHandle.cancel(true);
            }
        }, 1, SECONDS);
    }
}
