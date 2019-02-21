package net.whg.we.command;

public class VariableArgument implements CommandArgument
{
	private CommandVariable _var;

	public VariableArgument(CommandVariable var)
	{
		_var = var;
	}

	@Override
	public String getValue()
	{
		return _var.getValue();
	}

	@Override
	public String toString()
	{
		return "$" + _var.getName();
	}
}
