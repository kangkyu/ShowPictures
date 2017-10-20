package com.example.android.showpictures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPhoto = (TextView) findViewById(R.id.tv_photo_data);

        String[] dummyPhotoData = {
                "abc.jpg",
                "def.jpg",
        };

        for (String dummyPhoto : dummyPhotoData) {
            mPhoto.append(dummyPhoto + "\n\n\n");
        }
    }
}
