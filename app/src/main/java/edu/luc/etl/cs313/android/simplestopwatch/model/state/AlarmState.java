package edu.luc.etl.cs313.android.simplestopwatch.model.state;
import edu.luc.etl.cs313.android.simplestopwatch.R;

/**
 * Created by Tom on 11/18/2015.
 */
class AlarmState implements StopwatchState {

	private final StopwatchSMStateView sm;

	public AlarmState(final StopwatchSMStateView sm)
	{
		this.sm = sm;
	}

	@Override
	public void updateView() {} //don't need to do anything for this method

	@Override
	public int getId() {
		return R.string.ALARM;
	}

	@Override
	public void onTick() {
		sm.actionAlarm();
	} //it will keep beeping every tick of the clock

	@Override
	public void onClick() //when you click the button in this state it will stop the action and revert to the initial stopped state
	{
		sm.actionStop();
		sm.Reset();
		sm.toStoppedState();
	}
}
