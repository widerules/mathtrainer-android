package es.jmaragon.trainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends Activity {
	
	private final int PENALTY = 3000;
	
	private Intent triggerIntent;
	private Intent scoresIntent;
	private Bundle scoresBundle;
	
	private int correctAnswers = 0;
	private int numOperations = 20;
	private double realTimeTaken = 1000000;
	private double timeTaken;
	
	
	private TextView correctAnswersText;
	private TextView timeTakenText;
	private TextView realTimeTakenText;
	private TextView penaltyText;
	private Button okButton;
	private Button scoresButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Create the bundle
		scoresBundle = new Bundle();
		
		this.setContentView(R.layout.results);
        correctAnswersText = (TextView)findViewById(R.id.correctAnswersText);
        timeTakenText = (TextView)findViewById(R.id.timeTakenText);
        realTimeTakenText = (TextView)findViewById(R.id.realTimeTakenText);
        penaltyText = (TextView)findViewById(R.id.penaltyText);
        okButton = (Button)findViewById(R.id.okButton);
        scoresButton = (Button)findViewById(R.id.scoresButton);
        
        // Get the values from the Bundle
        triggerIntent = this.getIntent();
        if (triggerIntent != null) {
        	this.correctAnswers = triggerIntent.getBundleExtra("results").getInt("correctAnswers");
        	this.numOperations = triggerIntent.getBundleExtra("results").getInt("numOperations");
        	this.realTimeTaken = triggerIntent.getBundleExtra("results").getLong("timeTaken");
        }
        
        // Calculate penalty
        int wrongAnswers = this.numOperations - this.correctAnswers;
        long penaltyTime = PENALTY * wrongAnswers;
        this.timeTaken = this.realTimeTaken + penaltyTime;        
        
        // Set the text results
        this.correctAnswersText.setText(correctAnswers + " out of " + numOperations);
        this.timeTakenText.setText(timeTaken/1000 + " seconds");
        this.realTimeTakenText.setText(realTimeTaken/1000 + " seconds");
        this.penaltyText.setText("+" 
			        		+ PENALTY/1000 
			        		+ " x " 
			        		+ wrongAnswers
			        		+ " wrong answers = " 
			        		+ penaltyTime/1000 
			        		+ " seconds");
        
        // Put new score in bundle
        this.scoresBundle.putDouble("newScore", this.timeTaken);
        
        // Assign the button behaviour
        okButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				ResultsActivity.this.finish();
			}
		});
        
        // Assign the button behaviour for high scores
        scoresButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				ResultsActivity.this.scoresIntent = new Intent(ResultsActivity.this, ScoresActivity.class);
				ResultsActivity.this.scoresIntent.putExtra("scoresBundle", scoresBundle);
				ResultsActivity.this.startActivity(scoresIntent);
			}
		});        
	}

}
