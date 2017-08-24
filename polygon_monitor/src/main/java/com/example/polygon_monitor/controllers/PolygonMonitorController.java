package com.example.polygon_monitor.controllers;

import android.content.Context;
import android.content.Intent;

import com.example.polygon_monitor.services.HandleMessageService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 8/23/2017.
 */

public class PolygonMonitorController {

    public static final String POLYGON_MONITOR_TAG = "polygon_monitor";
    private final String ACTION_NAME = "com.example.polygon_monitor.action";

    private volatile static PolygonMonitorController instance = null;

    private PolygonMonitorController() {

    }

    public static PolygonMonitorController getInstance() {

        if (instance == null) {
            synchronized (PolygonMonitorController.class) {
                if (instance == null) {
                    instance = new PolygonMonitorController();
                }
            }
        }

        return instance;
    }

    public void handleFirebaseMessage(Context context, String message) {

        if (isMessageValid(message)) {

            Intent intent = new Intent(context, HandleMessageService.class);
            intent.putExtra(HandleMessageService.MESSAGE, message);
            context.startService(intent);
        }
    }

    private boolean isMessageValid(String message) {

        try {
            JSONObject jsonObject = new JSONObject(message);
            if (jsonObject.optInt(ACTION_NAME, -1) == -1) {
                return false;
            }
        } catch (JSONException e) {
            return false;
        }

        return true;
    }
}
