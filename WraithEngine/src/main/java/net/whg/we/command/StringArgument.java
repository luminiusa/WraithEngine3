package net.whg.we.command;

public class StringArgument implements CommandArgument
{
	private String _value;

	public StringArgument(String value)
	{
		_value = value;
	}

	@Override
	public String getValue()
	{
		return _value;
	}

	@Override
	public String toString()
	{
		return _value;
	}
}
