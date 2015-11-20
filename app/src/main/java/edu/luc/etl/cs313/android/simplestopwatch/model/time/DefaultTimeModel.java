package edu.luc.etl.cs313.android.simplestopwatch.model.time;

/**
 * An implementation of the timer model.
 */
public class DefaultTimeModel implements TimeModel {

	private int value = 0;

	@Override
	public void addValue()
	{
		value++;
	}

	@Override
	public int getValue() {return value;}

	@Override
	public void decValue() { value--; }

	@Override
	public void setValue(int x)
	{
		value = x;
	}
}