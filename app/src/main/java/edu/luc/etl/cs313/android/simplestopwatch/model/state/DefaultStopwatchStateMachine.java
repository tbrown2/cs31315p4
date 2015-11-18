package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;

/**
 * An implementation of the state machine for the stopwatch.
 *
 * @author laufer
 */
public class DefaultStopwatchStateMachine implements StopwatchStateMachine {

	public DefaultStopwatchStateMachine(final TimeModel timeModel, final ClockModel clockModel) {
		this.timeModel = timeModel;
		this.clockModel = clockModel;
	}

	private final TimeModel timeModel;

	private final ClockModel clockModel;

	/**
	 * The internal state of this adapter component. Required for the State pattern.
	 */
	private StopwatchState state;

	protected void setState(final StopwatchState state) {
		this.state = state;
		uiUpdateListener.updateState(state.getId());
	}

	private StopwatchUIUpdateListener uiUpdateListener;

	@Override
	public void setUIUpdateListener(final StopwatchUIUpdateListener uiUpdateListener) {
		this.uiUpdateListener = uiUpdateListener;
	}

	// forward event uiUpdateListener methods to the current state
	// these must be synchronized because events can come from the
	// UI thread or the timer thread
	@Override public synchronized void onClick() { state.onClick(); }
	@Override public synchronized void onTick()      { state.onTick(); }

	@Override public void updateUIRuntime() { uiUpdateListener.updateTime(timeModel.getRuntime()); }
	@Override public void updateUILaptime() { uiUpdateListener.updateTime(timeModel.getLaptime()); }

	// known states
	private final StopwatchState STOPPED     = new StoppedState(this);
	private final StopwatchState RUNNING     = new RunningState(this);
	private final StopwatchState ALARM_STATE = new AlarmState(this);
	private final StopwatchState ADDING_STATE = new AddingState(this);

	// transitions
	@Override public void toRunningState()    { setState(RUNNING); }
	@Override public void toStoppedState()    { setState(STOPPED); }
	@Override public void toAlarmState() { setState(ALARM_STATE); }
	@Override public void toAddingState() { setState(ADDING_STATE); }

	// actions
	@Override public void actionInit()       { toStoppedState(); actionReset(); }
	@Override public void actionReset()      { timeModel.resetRuntime(); actionUpdateView(); }
	@Override public void actionStart()      { clockModel.start(); }
	@Override public void actionStop()       { clockModel.stop(); }
	@Override public void actionLap()        { timeModel.setLaptime(); }
	@Override public void actionInc()        { timeModel.incRuntime(); actionUpdateView(); }
	@Override public void actionUpdateView() { state.updateView(); }
}
