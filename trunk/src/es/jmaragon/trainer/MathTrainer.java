package es.jmaragon.trainer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MathTrainer extends Activity {
	
    static final int MENU_NEW_GAME = 0;
    static final int MENU_HIGH_SCORES = 1;
    static final int MENU_QUIT = 99;
    
    static final int DIALOG_CONFIRM_EXIT = 100;
    
    private Intent newGameIntent;
    private Button startButton;
    private RadioGroup difficultyGroup;
    private RadioButton selectedDifficulty;
    private RadioGroup operationNumGroup;
    private RadioButton selectedOperationNumber;
    private Bundle newGameOptions;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        newGameIntent = new Intent(MathTrainer.this, GameActivity.class);
        newGameOptions = new Bundle();
        
        // Set up the Start button
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				newGameIntent.putExtra("newGameOptions", newGameOptions);
				startActivity(newGameIntent);
			}
		});
        
        // Set up the difficulty level radio buttons
        selectedDifficulty = (RadioButton)findViewById(R.id.easyChoice);
        selectedDifficulty.setChecked(true);
        
        // Set up the operation number radio buttons
        selectedOperationNumber = (RadioButton)findViewById(R.id._20);
        selectedOperationNumber.setChecked(true);
        
        difficultyGroup = (RadioGroup)findViewById(R.id.difficultyOption);
        difficultyGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				newGameOptions.putInt("difficulty", checkedId);
			}
		});
        newGameOptions.putInt("difficulty", selectedOperationNumber.getId());
        
        operationNumGroup = (RadioGroup)findViewById(R.id.lengthOption);
        operationNumGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId != -1) {
					newGameOptions.putInt("operationNum",
									  	  Integer.parseInt(((RadioButton)findViewById(checkedId)).getText().toString()));
				}
			}
		});
        newGameOptions.putInt("operationNum",
        					  Integer.parseInt((selectedOperationNumber.getText().toString())));
        
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    menu.add(0, MENU_NEW_GAME, MENU_NEW_GAME, R.string.menu_new_game);
	    menu.add(0, MENU_HIGH_SCORES, MENU_HIGH_SCORES, R.string.menu_high_scores);
	    menu.add(0, MENU_QUIT, MENU_QUIT, R.string.menu_quit_game);
	    return super.onCreateOptionsMenu(menu);
    }

	@Override
	protected Dialog onCreateDialog(int id) {
		
		Dialog dialog = super.onCreateDialog(id);
		
		switch (id) {
		case DIALOG_CONFIRM_EXIT:
			return this.createExitConfirmationDialog();
		}
		
		return dialog;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result = super.onOptionsItemSelected(item);
		
		switch (item.getItemId()) {
		case MENU_NEW_GAME:
			this.newGameIntent = new Intent(this, GameActivity.class);
			this.newGameIntent.putExtra("newGameOptions", newGameOptions);
			this.startActivity(newGameIntent);
			return true;
		case MENU_HIGH_SCORES:
			return true;
		case MENU_QUIT:
			this.showDialog(DIALOG_CONFIRM_EXIT);
			return true;
		}
		
		return result;
	}
	
	private Dialog createExitConfirmationDialog() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.dialog_confirm_exit_message)
		       .setCancelable(false)
		       .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                MathTrainer.this.finish();
		           }
		       })
		       .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		return builder.create();
		
	}
    
}