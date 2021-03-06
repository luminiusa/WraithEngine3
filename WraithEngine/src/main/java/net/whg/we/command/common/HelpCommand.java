package net.whg.we.command.common;

import net.whg.we.command.Command;
import net.whg.we.command.CommandArgument;
import net.whg.we.command.CommandHandler;
import net.whg.we.command.CommandList;
import net.whg.we.command.CommandSender;

public class HelpCommand implements CommandHandler
{
	private final String[] ALIAS =
	{
			"?"
	};

	private CommandList _commandList;

	public HelpCommand(CommandList commandList)
	{
		_commandList = commandList;
	}

	@Override
	public String getCommandName()
	{
		return "help";
	}

	@Override
	public String[] getCommandAliases()
	{
		return ALIAS;
	}

	@Override
	public String executeCommand(Command command)
	{
		CommandArgument[] args = command.getArgs();
		CommandSender sender = command.getCommandSender();

		if (args.length == 0)
		{
			for (int i = 0; i < _commandList.getCommandCount(); i++)
			{
				CommandHandler handler = _commandList.getCommand(i);

				sender.sendMessage(handler.getCommandName() + "\n    " + handler.getDescription());
			}

			return "";
		}

		if (args.length == 1)
		{
			CommandHandler handler = _commandList.getCommand(command.getName());

			if (handler == null)
			{
				sender.sendMessage("Unable to find command '" + command.getName() + "'");
				return "";
			}

			sender.sendMessage(command.getName() + ":\n" + handler.getHelpText());
			return "";
		}

		sender.sendMessage("Unknown number of parameters! Please use as:\n"
				+ "help [command name]\n" + "Where [] arguments are optional.");
		return "";
	}

	@Override
	public String getDescription()
	{
		return "Gets the help text for a command, or a list of available commands.";
	}

	@Override
	public String getHelpText()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("help\n");
		sb.append("  Lists all available commands.\n");
		sb.append("help <command name>\n");
		sb.append("  Lists the help text for a given command.");

		return sb.toString();
	}
}
