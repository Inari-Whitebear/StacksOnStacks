package com.tierzero.stacksonstacks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public class RegisterPileItemCommand implements ICommand {
	List aliases;

	public RegisterPileItemCommand() {
		aliases = new ArrayList();
		aliases.add("registerpileitem");
		aliases.add("rpi");
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "registerpileitem";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "registerpileitem item name> <meta> <piletype> <color|block> <blockmeta>";
	}

	@Override
	public List getCommandAliases() {

		return aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {

	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		// TODO OP ONLY

		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		// TODO Auto-generated method stub
		return false;
	}

}
