package digger.cmept.forum.cmd.impl;


import com.mojang.realmsclient.gui.ChatFormatting;
import digger.cmept.forum.cmd.CommandAbstract;
import digger.cmept.forum.ui.notif.NotifModern;
import digger.cmept.forum.ui.notif.NotifRender;
import digger.cmept.forum.utils.other.ChatUtils;

public class FakeNameCommand
        extends CommandAbstract {
    public static String oldName;
    public static String currentName;
    public static boolean canChange;

    public FakeNameCommand() {
        super("fakename", "fakename", "\u00a76.fakename" + (Object)((Object)ChatFormatting.WHITE) + " set \u00a73<name> |" + (Object)((Object)ChatFormatting.WHITE) + " reset", "\u00a76.fakename" + (Object)((Object)ChatFormatting.WHITE) + " set \u00a73<name> |" + (Object)((Object)ChatFormatting.WHITE) + " reset", "fakename");
    }

    @Override
    public void execute(String ... arguments) {
        try {
            if (arguments.length >= 2) {
                oldName = FakeNameCommand.mc.player.getName();
                if (arguments[0].equalsIgnoreCase("fakename")) {
                    if (arguments[1].equalsIgnoreCase("set")) {
                        currentName = arguments[2];
                        canChange = true;
                        ChatUtils.addChatMessage((Object)((Object)ChatFormatting.GREEN) + "Successfully" + (Object)((Object)ChatFormatting.WHITE) + " changed your name to " + (Object)((Object)ChatFormatting.RED) + arguments[2]);
                        NotifRender.queue("FakeName Manager", (Object)((Object)ChatFormatting.GREEN) + "Successfully" + (Object)((Object)ChatFormatting.WHITE) + " changed your name to " + (Object)((Object)ChatFormatting.RED) + arguments[2], 4, NotifModern.SUCCESS);
                    }
                    if (arguments[1].equalsIgnoreCase("reset")) {
                        currentName = oldName;
                        canChange = false;
                        ChatUtils.addChatMessage((Object)((Object)ChatFormatting.GREEN) + "Successfully" + (Object)((Object)ChatFormatting.WHITE) + " reset your name!");
                        NotifRender.queue("FakeName Manager", (Object)((Object)ChatFormatting.GREEN) + "Successfully" + (Object)((Object)ChatFormatting.WHITE) + " reset your name!", 4, NotifModern.SUCCESS);
                    }
                }
            } else {
                ChatUtils.addChatMessage(this.getUsage());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}


