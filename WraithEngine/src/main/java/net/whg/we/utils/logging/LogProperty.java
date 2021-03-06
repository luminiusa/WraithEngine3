package net.whg.we.utils.logging;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import net.whg.we.utils.Poolable;

/**
 * This class represents a single log statement that is sent to the logger,
 * along with all Other log information that should be attached to it.
 */
public class LogProperty implements Poolable
{
	public static final String TIME_PROPERTY = "Time";
	public static final String THREAD_PROPERTY = "Thread";
	public static final String MESSAGE_PROPERTY = "Message";
	public static final String SEVERITY_PROPERTY = "Severity";
	public static final String INDENT_PROPERTY = "Indent";

	private Map<String, String> _properties = new HashMap<>();
	private StringBuilder sb = new StringBuilder();

	public LogProperty()
	{
		init();
	}

	public LogProperty(LogProperty other)
	{
		for (String key : other._properties.keySet())
			_properties.put(key, other._properties.get(key));
	}

	@Override
	public void init()
	{
		setTimeStamp(LocalTime.now());
		setThreadName(Thread.currentThread().getName());
		setMessage("");
		setSeverity(Log.INFO);
		setIndent(0);
	}

	@Override
	public void dispose()
	{
		_properties.clear();
	}

	public int getPropertyCount()
	{
		return _properties.size();
	}

	public LogProperty setProperty(String property, String value)
	{
		_properties.put(property, value);
		return this;
	}

	public String getProperty(String property)
	{
		return _properties.get(property);
	}

	public LogProperty setTimeStamp(LocalTime time)
	{
		setProperty(TIME_PROPERTY, String.format("%02d:%02d:%02d", time.getHour(), time.getMinute(),
				time.getSecond()));
		return this;
	}

	public String getTimeStamp()
	{
		return getProperty(TIME_PROPERTY);
	}

	public LogProperty setThreadName(String thread)
	{
		setProperty(THREAD_PROPERTY, thread);
		return this;
	}

	public String getThreadName()
	{
		return getProperty(THREAD_PROPERTY);
	}

	public LogProperty setMessage(String message)
	{
		setProperty(MESSAGE_PROPERTY, message);
		return this;
	}

	public LogProperty setMessage(String message, Object... args)
	{
		setMessage(String.format(message, args));
		return this;
	}

	public String getMessage()
	{
		return getProperty(MESSAGE_PROPERTY);
	}

	public LogProperty setSeverity(int severity)
	{
		String s;

		switch (severity)
		{
			case Log.TRACE:
				s = "Trace";
				break;

			case Log.DEBUG:
				s = "Debug";
				break;

			case Log.INFO:
				s = "Info";
				break;

			case Log.WARN:
				s = "Warn";
				break;

			case Log.ERROR:
				s = "Error";
				break;

			case Log.FATAL:
				s = "Fatal";
				break;

			default:
				throw new IllegalArgumentException("Unknown severity level!");
		}

		setProperty(SEVERITY_PROPERTY, s);
		return this;
	}

	public String getSeverity()
	{
		return getProperty(SEVERITY_PROPERTY);
	}

	public LogProperty setIndent(int indent)
	{
		if (indent < 0)
			throw new IllegalArgumentException("Inindent cannot be negative!");

		setProperty(INDENT_PROPERTY, Integer.toString(indent));
		return this;
	}

	public int getIndent()
	{
		return Integer.parseInt(getProperty(INDENT_PROPERTY));
	}

	public LogProperty clearProperty(String property)
	{
		_properties.remove(property);

		if (property.equals(MESSAGE_PROPERTY))
			setMessage("");
		if (property.equals(SEVERITY_PROPERTY))
			setSeverity(Log.INFO);
		if (property.equals(TIME_PROPERTY))
			setTimeStamp(LocalTime.now());
		if (property.equals(THREAD_PROPERTY))
			setThreadName(Thread.currentThread().getName());
		if (property.equals(INDENT_PROPERTY))
			setIndent(0);

		return this;
	}

	@Override
	public String toString()
	{
		sb.setLength(0);

		String message = getMessage();
		int characterIndex = 0;

		lineLoop:
		do
		{
			sb.append('[').append(getTimeStamp()).append(']');
			sb.append('[').append(getSeverity()).append(']');
			sb.append('[').append(getThreadName()).append(']');

			int indent = getIndent() * Log.SPACES_PER_INDENT + 1;
			sb.ensureCapacity(sb.length() + indent);
			for (int i = 0; i < indent; i++)
				sb.append(' ');

			while (characterIndex < message.length())
			{
				char c = message.charAt(characterIndex++);
				sb.append(c);

				if (c == '\n')
					continue lineLoop;
			}

			break;
		}
		while (true);

		return sb.toString();
	}

	public String toMapString()
	{
		return _properties.toString();
	}
}
