package es.jmaragon.trainer;

import java.io.FileInputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ScoresActivity extends Activity {
	
	private Intent triggerIntent;	
	private double newScore;
	
	private ListView scoreList;
	private Button okButton;
	
	private FileInputStream scoreFile;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.scores);
        scoreList = (ListView)findViewById(R.id.highScores);
        okButton = (Button)findViewById(R.id.okButton);       
        
        // Get the values from the Bundle
        triggerIntent = this.getIntent();
        if (triggerIntent != null && this.triggerIntent.getBundleExtra("scoresBundle") != null) {
        	this.newScore = triggerIntent.getBundleExtra("scoresBundle").getDouble("newScore");
        }
        
        // Open the scores file to read
        InputStream scoreFile = this.getResources().openRawResource(R.raw.mathtrainer_android_scores);
        
        
        
        // Assign the button behaviour
        okButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				ScoresActivity.this.finish();
			}
		});
        
	}
	
}
