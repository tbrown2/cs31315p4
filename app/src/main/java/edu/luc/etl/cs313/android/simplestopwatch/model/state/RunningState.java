package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

class RunningState implements StopwatchState {

	public RunningState(final StopwatchSMStateView sm) {
		this.sm = sm;
	}

	private final StopwatchSMStateView sm;

	@Override
	public void onClick()
	{ //if someone clicks the buttong while the program is in running state
	// stop the action, reset the value to 0, transition to start view
		sm.actionStop();
		sm.Reset();
		sm.actionUpdateView();
		sm.toStoppedState();
	}


	@Override
	public void onTick() { //decrements the timer by one every second while there is time left,
	// when out of time, transitions to the AlarmingState
		if (sm.actionGet() > 0)
		{
			sm.actionDec();
		}
		else
		{
			sm.toAlarmState();
		}
	}

	@Override
	public void updateView() {
		sm.updateUIValue();
	}

	@Override
	public int getId() {
		return R.string.RUNNING;
	}
}
