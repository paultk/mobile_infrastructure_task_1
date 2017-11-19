package com.example.paulthomaskorsvold.sensorapplicatinthird;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String M_TAG = "MainActivity";
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private TextView mAccXTView;
    private TextView mAccYTView;
    private TextView mAccZTView;
    private Button mGetLatestReadingButton;

//    private SensorData mLatest;

    private volatile String mAccX, mAccY, mAccZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mAccXTView = (TextView) findViewById(R.id.editText1);
        mAccYTView= (TextView) findViewById(R.id.editText2);
        mAccZTView= (TextView) findViewById(R.id.editText3);

        mGetLatestReadingButton = findViewById(R.id.get_latest_reading_button);
        mGetLatestReadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLatest();
            }
        });



//        getLatest();
//        sensorThread();

        postSensorData();

//        dbTest();
    }

    private void getLatest() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Query recent = myRef.child(getString(R.string.sensor_reading_table)).limitToLast(1);
        recent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    SensorData latest = data.getValue(SensorData.class);
                    Log.d(M_TAG, "lastItem:\n" + latest);
                    mAccXTView.setText(latest.getmX());
                    mAccYTView.setText(latest.getmY());
                    mAccZTView.setText(latest.getmZ());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Could not fetch data", Toast.LENGTH_LONG);
            }
        });

    }

    public void dbTest() {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(getString(R.string.sensor_reading_table));

        SensorData testData = new SensorData("1", "2", "3");

        DatabaseReference newRow = myRef.push();
        Log.d(M_TAG, "key: " + newRow.getKey());
        newRow.updateChildren(testData.toMap());

//        myRef.setValue("Hello, World!");
    }

    private void sensorTest() {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAccX = ""  + sensorEvent.values[0];
            mAccY = "" + sensorEvent.values[1];
            mAccZ = "" + sensorEvent.values[2];


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    private void postSensorData() {
        final Runnable r = new Runnable() {
            public void run() {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(getString(R.string.sensor_reading_table));

                int i = 0;
                try {
                    while (true){
                        Log.d(M_TAG, "added: " + ++i + " items");
//                        accX.setText(("starter" + ++i));
                        SensorData testData = new SensorData(mAccX,
                                mAccY, mAccZ);

                        DatabaseReference newRow = myRef.push();
                        Log.d(M_TAG, "key: " + newRow.getKey());
                        newRow.updateChildren(testData.toMap());
                        Thread.sleep(10000);
                    }
                } catch (Exception e) {
                    Log.d(M_TAG, e.getMessage());
                }

            }
        };

        new Thread(r).start();
    }

    private void getRecent() {
        // Last 100 posts, these are automatically the 100 most recent
// due to sorting by push() keys
        /*Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(100);*/
    }
}
