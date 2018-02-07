package org.bubulescu.periodicreceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button btnStart, btnStop;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private static final int ALARM_INTERVAL = 1000 * 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAlarm();
        initWidgets();
        setupListeners();
    }

    private void initAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void initWidgets() {
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
    }

    private void setupListeners() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), ALARM_INTERVAL, pendingIntent);

                Toast.makeText(getApplicationContext(), "Alarm startan!", Toast.LENGTH_LONG).show();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pendingIntent);

                Toast.makeText(getApplicationContext(), "Alarm zaustavljen..", Toast.LENGTH_LONG).show();
            }
        });
    }
}
