package digger.cmept.forum.cmd.impl;


import com.mojang.realmsclient.gui.ChatFormatting;
import digger.cmept.forum.cmd.CommandAbstract;
import digger.cmept.forum.utils.other.ChatUtils;

public class HelpCommand
        extends CommandAbstract {
    public HelpCommand() {
        super("help", "help", ".help", "help");
    }

    @Override
    public void execute(String ... args) {
        if (args.length == 1) {
            if (args[0].equals("help")) {
                ChatUtils.addChatMessage((Object)((Object)ChatFormatting.RED) + "All Commands:");
                ChatUtils.addChatMessage((Object)((Object)ChatFormatting.WHITE) + ".bind");
                ChatUtils.addChatMessage((Object)((Object)ChatFormatting.WHITE) + ".macro");
                ChatUtils.addChatMessage((Object)((Object)ChatFormatting.WHITE) + ".gps");
                ChatUtils.addChatMessage((Object)((Object)ChatFormatting.WHITE) + ".parser");
                ChatUtils.addChatMessage((Object)((Object)ChatFormatting.WHITE) + ".vclip");
                ChatUtils.addChatMessage((Object)((Object)ChatFormatting.WHITE) + ".panic");
                ChatUtils.addChatMessage((Object)((Object)ChatFormatting.WHITE) + ".hclip");
                ChatUtils.addChatMessage((Object)((Object)ChatFormatting.WHITE) + ".eclip");
                ChatUtils.addChatMessage((Object)((Object)ChatFormatting.WHITE) + ".fakename");
                ChatUtils.addChatMessage((Object)((Object)ChatFormatting.WHITE) + ".friend");
                ChatUtils.addChatMessage((Object)((Object)ChatFormatting.WHITE) + ".config �����, � �� .cfg , �� ����� ������ ������ ������ ����");
                ChatUtils.addChatMessage((Object)((Object)ChatFormatting.WHITE) + ".tp");
            }
        } else {
            ChatUtils.addChatMessage(this.getUsage());
        }
    }
}