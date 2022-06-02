package com.example.practical19;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements
        SensorEventListener{
    private SensorManager s;
    private boolean isColor = false;
    private View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = findViewById(R.id.t1);
        v.setBackgroundColor(Color.BLUE);
        s = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH *
                SensorManager.GRAVITY_EARTH);
        if (accelationSquareRoot >= 2) //it will be executed if you shuffle
        {
            Toast.makeText(this, "Device was shuffed",
                    Toast.LENGTH_SHORT).show();
            if (isColor) {
                v.setBackgroundColor(Color.BLUE);
            } else {
                v.setBackgroundColor(Color.CYAN);
            }
            isColor = !isColor;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors

        s.registerListener(this,s.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        s.unregisterListener(this);
    }
}