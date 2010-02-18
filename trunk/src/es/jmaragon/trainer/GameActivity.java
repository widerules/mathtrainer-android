package es.jmaragon.trainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity {
	
	private static final int SUM = 0;
	private static final int SUBTRACTION = 1;
	private static final int MULTIPLICATION = 2;
	private static final int DIVISION = 3;
	
	private Intent triggerIntent;
	private Intent resultsIntent;
	private Bundle results;
	
	private TextView operationText;
	private Button rightButton;
	private Button wrongButton;
	
	private int difficultyLevel = R.id.easyChoice;
	private int numOperations = 20;
	
	private Random randomizer;
	private int maxValue = 10;
	private int numOperators = 4;
	private Map<String,Boolean> opStrings;
	private Object[] opStrs;
	private int opCounter = 0;
	
	private int correctAnswers = 0;
	private long startTime;
	private long endTime;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        resultsIntent = new Intent(GameActivity.this, ResultsActivity.class);
        results = new Bundle();
        
        this.setContentView(R.layout.game);
        operationText = (TextView)findViewById(R.id.operationText);
        rightButton = (Button)findViewById(R.id.rightButton);
        wrongButton = (Button)findViewById(R.id.wrongButton);
        
        triggerIntent = this.getIntent();
        if (triggerIntent != null) {
        	this.difficultyLevel = triggerIntent.getBundleExtra("newGameOptions").getInt("difficulty");
        	this.numOperations = triggerIntent.getBundleExtra("newGameOptions").getInt("operationNum");
        }
        
        switch (this.difficultyLevel) {
		case R.id.easyChoice:
			numOperators = 2;
			maxValue = 10;
			break;
		case R.id.hardChoice:
			numOperators = 4;
			maxValue = 21;
			break;
		}
        
        this.randomizer = new Random();
        this.randomizer.setSeed(System.currentTimeMillis());
        opStrings = generateOperations();
        opStrs = opStrings.keySet().toArray();
        
        // Add actions to the buttons
        rightButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if ( opStrings.get(operationText.getText()) ) {
					correctAnswers++;
				}
				
				if (++opCounter == numOperations) {
					// End has been reached
					GameActivity.this.finishGame();
				}
				else {
					operationText.setText((String)opStrs[opCounter]);
				}
			}
		});
        
        // Add actions to the buttons
        wrongButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if ( !opStrings.get(operationText.getText()) ) {
					correctAnswers++;
				}
				
				if (++opCounter == numOperations) {
					// End has been reached
					GameActivity.this.finishGame();
				}
				else {
					operationText.setText((String)opStrs[opCounter]);
				}
			}
		});
        
        // Display the first operation
        operationText.setText((String)opStrs[0]);
       
        // Make a note of the start time
        startTime = System.currentTimeMillis();
    }
		
	private Map<String, Boolean> generateOperations() {
		
		Map<String, Boolean> operations = new HashMap<String, Boolean>(this.numOperations);
		
		for (int i = 0; i < this.numOperations; i++) {
			int operand1 = randomizer.nextInt(maxValue);
			int operand2 = randomizer.nextInt(maxValue);
			boolean correct = randomizer.nextBoolean();
			int operator = randomizer.nextInt(numOperators);
			
			operations.put(this.buildOperationString(operand1, operand2, operator, correct), correct);
		}
		
		return operations;
	}
	
	private String buildOperationString(int op1, int op2, int operator, boolean correct) {
		StringBuilder opStr = new StringBuilder();
		String operatorStr = null;
		int realResult = 0;
		int result = 0;
		
		switch (operator) {
		case SUM:
			operatorStr = " + ";
			realResult = op1 + op2;
			break;
		case SUBTRACTION:
			operatorStr = " - ";
			realResult = op1 - op2;
			break;
		case MULTIPLICATION:
			operatorStr = " \u00D7 ";
			realResult = op1 * op2;
			break;
		case DIVISION:
			operatorStr = " \u00F7 ";
			realResult = op1 / op2;
			break;
		}
		
		// Obtain the result (real or not)
		if (correct) {
			result = realResult;
		}
		else {
			do {
				result = randomizer.nextInt(maxValue);
			} while (result == realResult);
		}
		
		opStr.append(op1)
		 	 .append(operatorStr)
		 	 .append(op2)
		 	 .append(" = ")
		 	 .append(result);
		
		return opStr.toString();
	}
	
	private void finishGame() {
		// Make a note of the end time
		endTime = System.currentTimeMillis();
		
		// Place the results in the Bundle
		this.results.putInt("numOperations", this.numOperations);
		this.results.putInt("correctAnswers", this.correctAnswers);
		this.results.putLong("timeTaken", this.endTime - this.startTime);
		
		this.finish();
		
		// Call the results activity
		this.resultsIntent.putExtra("results", results);
		this.startActivity(this.resultsIntent);
	}
	
}
