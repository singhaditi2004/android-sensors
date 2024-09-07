package com.example.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    AppCompatButton accelor, stop, proxi, light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelor = findViewById(R.id.accelor);
        if (sm != null) {
            accelor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Sensor acelor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    if (acelor != null) {
                        sm.registerListener(MainActivity.this, acelor, SensorManager.SENSOR_DELAY_NORMAL);

                    }
                }
            });

            proxi = findViewById(R.id.proxi);
            proxi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Sensor proxi = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                    if (proxi != null) {
                        sm.registerListener(MainActivity.this, proxi, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                }
            });
            light = findViewById(R.id.light);
            light.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Sensor lightt = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
                    if (lightt != null) {
                        sm.registerListener(MainActivity.this, lightt, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                }
            });
            stop = findViewById(R.id.stop);
            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sm.unregisterListener(MainActivity.this);
                    Toast.makeText(MainActivity.this, "Sensor stopped", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(this, "No sensor found", Toast.LENGTH_SHORT).show();
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            TextView txt = findViewById(R.id.sensorsText);
            txt.setText("X: " + event.values[0] + "Y: " + event.values[1] + "Z: " + event.values[2]);
        }
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            TextView txt = findViewById(R.id.sensorsText);
            txt.setText("Values " + event.values[0]);
            if (event.values[0] > 0) {
                Toast.makeText(this, "Object is far", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Object is near", Toast.LENGTH_SHORT).show();
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            TextView txt = findViewById(R.id.sensorsText);
            txt.setText("values of light sensor "+event.values[0]);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}