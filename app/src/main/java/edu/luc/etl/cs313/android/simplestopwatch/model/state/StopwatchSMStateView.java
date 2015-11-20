package edu.luc.etl.cs313.android.simplestopwatch.model.state;

/**
 * The restricted view states have of their surrounding state machine.
 * This is a client-specific interface in Peter Coad's terminology.
 *
 * @author laufer
 */
interface StopwatchSMStateView {

	// transitions
	void toRunningState();
	void toStoppedState();
	void toAlarmState();
	void toAddingState();

	// actions
	void actionInit();
	void actionStart();
	void actionStop();
		//add 1
	void actionAdd();
		//subtract 1
	void actionDec();
		//set timemodel value to 0
	void Reset();
	void actionUpdateView();
	//get the value
	int actionGet();
	//Alarm
	void actionAlarm();


	// state-dependent UI updates
	void updateUIValue();
}
