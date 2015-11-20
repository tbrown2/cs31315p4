package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

class StoppedState implements StopwatchState {

	public StoppedState(final StopwatchSMStateView sm) {
		this.sm = sm;
	}

	private final StopwatchSMStateView sm;
	@Override
	public void updateView() {
		sm.updateUIValue();
	}

	@Override
	public void onTick() {
		throw new UnsupportedOperationException("onTick");
	}

	@Override
	public int getId() {
		return R.string.STOPPED;
	}

	@Override
	public void onClick()
	{
		sm.actionAdd(); //for a smooth transition into the adding state
		sm.toAddingState();
		sm.actionStart();
	}
}
