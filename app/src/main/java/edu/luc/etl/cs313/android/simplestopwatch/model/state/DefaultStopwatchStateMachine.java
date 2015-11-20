package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;

/**
 * An implementation of the state machine for the stopwatch.
 *
 * @author laufer
 */
public class DefaultStopwatchStateMachine implements StopwatchStateMachine {

	public DefaultStopwatchStateMachine(final TimeModel timeModel, final ClockModel clockModel, Context c) {
		this.timeModel = timeModel;
		this.clockModel = clockModel;
		final Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		this.sound = RingtoneManager.getRingtone(c, defaultRingtoneUri);
	}

	private Ringtone sound;

	private final TimeModel timeModel;

	private final ClockModel clockModel;

	/**
	 * The internal state of this adapter component. Required for the State pattern.
	 */
	private StopwatchState state;

	public void setState(final StopwatchState state) {
		this.state = state;
		uiUpdateListener.updateState(state.getId());
	}

	private StopwatchUIUpdateListener uiUpdateListener;

	@Override
	public void setUIUpdateListener(final StopwatchUIUpdateListener uiUpdateListener) {
		this.uiUpdateListener = uiUpdateListener;
	}

	// forward event uiUpdateListener methods to the current state
	@Override public void onClick() { state.onClick(); }
	@Override public void onTick()  { state.onTick(); }

	@Override public void updateUIValue() { uiUpdateListener.updateTime(timeModel.getValue()); }

	//
	public StopwatchState getState(){
		return state;
	}


	// known states
	private final StopwatchState STOPPED      = new StoppedState(this);
	private final StopwatchState RUNNING      = new RunningState(this);
	private final StopwatchState ADDING       = new AddingState(this);
	private final StopwatchState ALARM     = new AlarmState(this);

	// transitions
	@Override public void toRunningState()      { setState(RUNNING); }
	@Override public void toStoppedState()      { setState(STOPPED); }
	@Override public void toAddingState()       { setState(ADDING); }
	@Override public void toAlarmState()        { setState(ALARM); }

	// actions
	@Override public void actionInit()       { toStoppedState();}
	@Override public void actionStart()      { clockModel.start(); }
	@Override public void actionStop()       { clockModel.stop(); }
	@Override public void actionAdd()        { timeModel.addValue(); actionUpdateView(); }
	@Override public void actionDec()        { timeModel.decValue(); actionUpdateView(); }
	@Override public void actionUpdateView() { state.updateView(); }
	@Override public void Reset()            { timeModel.setValue(0);}
	@Override public int  actionGet()        { return timeModel.getValue(); }
	@Override public void actionAlarm()
	{
		System.out.println(sound);
		sound.play();
	}
}