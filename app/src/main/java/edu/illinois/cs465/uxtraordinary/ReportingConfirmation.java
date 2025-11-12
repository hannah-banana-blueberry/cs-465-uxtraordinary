package edu.illinois.cs465.uxtraordinary;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReportingConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting_confirmation);

        // Grab information from 'Reporting Filter' activity via intent passed
        Intent intent_received = getIntent();
        String buildingChoice = intent.getStringExtra("building_choice");
        String floorChoice = intent.getStringExtra("floor_choice");
        String comment = intent.getStringExtra("report_comments");
    }
}