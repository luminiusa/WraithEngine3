package net.whg.we.command.common;

import net.whg.we.command.Command;
import net.whg.we.command.CommandHandler;
import net.whg.we.ui.terminal.Console;

public class ClearCommand implements CommandHandler
{
	private final String[] ALIAS = {};

	@Override
	public String getCommandName()
	{
		return "clear";
	}

	@Override
	public String[] getCommandAliases()
	{
		return ALIAS;
	}

	@Override
	public String executeCommand(Command command)
	{
		Console console = command.getCommandSender().getConsole();

		if (command.getArgs().length > 0)
		{
			console.println("Unknown number of parameters! Please use as:\n"
					+ "help [command name]\n" + "Where [] arguments are optional.");
			return "";
		}

		console.clear();
		return "";
	}

	@Override
	public String getDescription()
	{
		return "Prints the input arguments, seperated by spaces.";
	}

	@Override
	public String getHelpText()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("clear\n");
		sb.append("  Clears the console.\n");

		return sb.toString();
	}
}
