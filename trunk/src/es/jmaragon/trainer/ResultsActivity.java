package es.jmaragon.trainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends Activity {
	
	private Intent triggerIntent;
	
	private int correctAnswers = 0;
	private int numOperations = 20;
	private long timeTaken = 1000000;
	
	private TextView correctAnswersText;
	private TextView timeTakenText;
	private Button okButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.results);
        correctAnswersText = (TextView)findViewById(R.id.correctAnswersText);
        timeTakenText = (TextView)findViewById(R.id.timeTakenText);
        okButton = (Button)findViewById(R.id.okButton);
        
        // Get the values from the Bundle
        triggerIntent = this.getIntent();
        if (triggerIntent != null) {
        	this.correctAnswers = triggerIntent.getBundleExtra("results").getInt("correctAnswers");
        	this.numOperations = triggerIntent.getBundleExtra("results").getInt("numOperations");
        	this.timeTaken = triggerIntent.getBundleExtra("results").getLong("timeTaken");
        }
        
        // Set the text results
        this.correctAnswersText.setText(correctAnswers + " out of " + numOperations);
        this.timeTakenText.setText(((double)timeTaken)/1000 + " seconds");
        
        // Assign the button behaviour
        okButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				ResultsActivity.this.finish();
			}
		});
        
	}

}
