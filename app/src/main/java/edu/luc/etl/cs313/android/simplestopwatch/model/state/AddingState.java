package edu.luc.etl.cs313.android.simplestopwatch.model.state;

/**
 This state is when a user is actively pressing the ADD-RESET button, it is the transitions from the Stopped state,
 and goes to the running state when a user waits three seconds after pressing the ADD-RESET button
 */

import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.android.StopwatchAdapter;

class AddingState extends StopwatchAdapter implements StopwatchState {

	private final StopwatchSMStateView sm; //this is our state view
	private int tick; //this integer will keep track of the number of seconds
	private final int maximum = 99; //this is the cap for our ticks

	public AddingState (final StopwatchSMStateView sm)
	{
		this.sm = sm; //pass the state and set it as the value
		tick = 0; //make sure the seconds are reset to 0
	}

	@Override
	public void onClick() //when the user clicks the button
	{
		//if the user has less then 99 seconds, then the add one to the timer and reset the tick count
		if (sm.actionGet() < maximum)
		{
			tick = 0;
			sm.actionAdd();
		}
		//else the user has 99 and then automatically go into the running state
		else
		{
			sm.actionAlarm();
			sm.toRunningState();
		}
	}

	@Override
	public void onTick()
	{
		//measurement of time in the adding state, tick count will increase each second
		this.tick++;
		if (tick == 3) //after three seconds, make an alarm and go into the running state
		{
			sm.actionAlarm();
			sm.toRunningState();
		}
	}

	@Override
	public void updateView()
	{
		sm.updateUIValue();
	}

	@Override
	public int getId() {
		return R.string.ADD;
	}
}