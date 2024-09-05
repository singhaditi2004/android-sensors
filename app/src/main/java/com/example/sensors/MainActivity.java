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
    AppCompatButton accelor, stop;

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
        } else {
            Toast.makeText(this, "No sensor found", Toast.LENGTH_SHORT).show();
        }
        stop=findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.unregisterListener(MainActivity.this);
                Toast.makeText(MainActivity.this, "Sensor stopped", Toast.LENGTH_SHORT).show();

            }
        });
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
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}