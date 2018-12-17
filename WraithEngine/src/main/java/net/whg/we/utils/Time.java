package net.whg.we.utils;

/**
 * A utility class for measure game time. Time is based of the system clock, and
 * should be updated once every frame, at the start of the frame, for best
 * results.
 *
 * @author TheDudeFromCI
 */
public class Time
{
	private static float _time;
	private static float _delta;
	private static long _startMs = -1;

	/**
	 * Called once at the start of a frame to calculate the amount of time passed,
	 * and the delta time of the previous frame.
	 */
	public static void updateTime()
	{
		long time = System.nanoTime();

		if (_startMs == -1)
		{
			_startMs = time;
			_time = 0f;
			_delta = 0f;
			return;
		}

		long passed = time - _startMs;
		_startMs = time;

		_delta = (float) (passed / 1000000000.0);
		_time += _delta;
	}

	/**
	 * Gets the current amount of time passed in seconds since the Timer was first
	 * called.
	 *
	 * @return The amount of time passed in seconds.
	 */
	public static float time()
	{
		return _time;
	}

	/**
	 * Gets the current delta time in seconds of the previous frame.
	 * 
	 * @return The delta time of the previous frame in seconds.
	 */
	public static float deltaTime()
	{
		return _delta;
	}
}
