package net.whg.we.event;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class EventCallerBase<T extends Listener> implements EventCaller<T>
{
	protected ArrayList<T> _listeners = new ArrayList<>();
	private LinkedList<T> _pendingAdd = new LinkedList<>();
	private LinkedList<T> _pendingRemove = new LinkedList<>();
	private boolean _isInEvent;
	private Object _lock = new Object();
	private boolean _disposeOnFinish;

	@Override
	public void addListener(T listener)
	{
		synchronized (_lock)
		{
			if (_listeners.contains(listener))
				return;

			if (_isInEvent)
			{
				if (_pendingAdd.contains(listener))
					return;

				_pendingAdd.add(listener);
				return;
			}

			_listeners.add(listener);
			_listeners.sort((o1, o2) -> Integer.compare(o1.getPriority(), o2.getPriority()));
		}
	}

	@Override
	public void removeListener(T listener)
	{
		synchronized (_lock)
		{
			if (_isInEvent)
			{
				_pendingRemove.add(listener);
				return;
			}

			_listeners.remove(listener);
		}
	}

	@Override
	public void dispose()
	{
		synchronized (_lock)
		{
			if (_isInEvent)
			{
				_disposeOnFinish = true;
				return;
			}

			_listeners.clear();
			_pendingAdd.clear();
			_pendingRemove.clear();
		}
	}

	protected void callEvent(int index)
	{
		synchronized (_lock)
		{
			_isInEvent = true;

			for (T t : _listeners)
			{
				try
				{
					runEvent(t, index);
				}
				catch (Exception exception)
				{
					exception.printStackTrace();
				}
			}

			_isInEvent = false;

			if (_disposeOnFinish)
			{
				dispose();
				return;
			}

			for (T t : _pendingAdd)
				_listeners.add(t);

			for (T t : _pendingRemove)
				_listeners.remove(t);

			if (!_pendingAdd.isEmpty())
				_listeners.sort((o1, o2) -> Integer.compare(o1.getPriority(), o2.getPriority()));

			_pendingAdd.clear();
			_pendingRemove.clear();
		}
	}

	protected abstract void runEvent(T t, int index);
}