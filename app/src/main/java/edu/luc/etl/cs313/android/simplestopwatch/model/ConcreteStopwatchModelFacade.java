package edu.luc.etl.cs313.android.simplestopwatch.model;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.DefaultClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.DefaultStopwatchStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.StopwatchStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.DefaultTimeModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;
import android.content.Context;

/**
 * An implementation of the model facade.
 *
 * @author laufer
 */
public class ConcreteStopwatchModelFacade implements StopwatchModelFacade {

	private StopwatchStateMachine stateMachine;

    private ClockModel clockModel;

	private TimeModel timeModel;

	public ConcreteStopwatchModelFacade(Context context) //passing current environment state
	{
		timeModel = new DefaultTimeModel();
		clockModel = new DefaultClockModel();
		stateMachine = new DefaultStopwatchStateMachine(timeModel, clockModel, context);
		clockModel.setOnTickListener(stateMachine);
	}

	@Override
	public void onStart() {
		stateMachine.actionInit();
	}

	@Override
	public void setUIUpdateListener(final StopwatchUIUpdateListener listener) {
		stateMachine.setUIUpdateListener(listener);
	}

    @Override
	public void onClick() {
		stateMachine.onClick();
	}

}
