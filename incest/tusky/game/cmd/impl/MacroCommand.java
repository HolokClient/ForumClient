package incest.tusky.game.cmd.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import incest.tusky.game.tuskevich;
import incest.tusky.game.cmd.CommandAbstract;
import incest.tusky.game.files.impl.MacroConfig;
import incest.tusky.game.utils.other.ChatUtils;
import incest.tusky.game.cmd.macro.Macro;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

public class MacroCommand
            extends CommandAbstract {
        public MacroCommand() {
            super("macros", "macro", (Object)((Object)TextFormatting.GRAY) + ".macro" + (Object)((Object)ChatFormatting.WHITE) + " add " + "\u00a73<key> /home_home | \u00a77.macro" + (Object)((Object)ChatFormatting.WHITE) + " remove " + "\u00a73<key> |" + (Object)((Object)TextFormatting.GRAY) + " .macro" + (Object)((Object)ChatFormatting.WHITE) + " clear " + "\u00a73| \u00a77.macro" + (Object)((Object)ChatFormatting.WHITE) + " list", "\u00a77.macro" + (Object)((Object)ChatFormatting.WHITE) + " add " + "\u00a73<key> </home_home> | \u00a77.macro" + (Object)((Object)ChatFormatting.WHITE) + " remove " + "\u00a73<key> | \u00a77.macro" + (Object)((Object)ChatFormatting.WHITE) + " clear " + "| \u00a77.macro" + (Object)((Object)ChatFormatting.WHITE) + " list", "macro");
        }

        @Override
        public void execute(String ... arguments) {
            try {
                if (arguments.length > 1) {
                    if (arguments[0].equals("macro")) {
                        if (arguments[1].equals("add")) {
                            StringBuilder command = new StringBuilder();
                            for (int i = 3; i < arguments.length; ++i) {
                                command.append(arguments[i]).append(" ");
                            }
                            tuskevich.instance.macroManager.addMacro(new Macro(Keyboard.getKeyIndex(arguments[2].toUpperCase()), command.toString()));
                            tuskevich.instance.fileManager.getFile(MacroConfig.class).saveFile();
                            ChatUtils.addChatMessage((Object)((Object)ChatFormatting.GREEN) + "Added" + " macros for key" + (Object)((Object)ChatFormatting.RED) + " \"" + arguments[2].toUpperCase() + (Object)((Object)ChatFormatting.RED) + "\" " + (Object)((Object)ChatFormatting.WHITE) + "with value " + (Object)((Object)ChatFormatting.RED) + command);
                        }
                        if (arguments[1].equals("clear")) {
                            if (tuskevich.instance.macroManager.getMacros().isEmpty()) {
                                ChatUtils.addChatMessage((Object)((Object)ChatFormatting.RED) + "Your macros list is empty!");
                                return;
                            }
                            tuskevich.instance.macroManager.getMacros().clear();
                            ChatUtils.addChatMessage((Object)((Object)ChatFormatting.GREEN) + "Your macros list " + (Object)((Object)ChatFormatting.WHITE) + " successfully cleared!");
                        }
                        if (arguments[1].equals("remove")) {
                            tuskevich.instance.macroManager.deleteMacroByKey(Keyboard.getKeyIndex(arguments[2].toUpperCase()));
                            ChatUtils.addChatMessage((Object)((Object)ChatFormatting.GREEN) + "Macro " + (Object)((Object)ChatFormatting.WHITE) + "was deleted from key " + (Object)((Object)ChatFormatting.RED) + "\"" + arguments[2].toUpperCase() + "\"");
                        }
                        if (arguments[1].equals("list")) {
                            if (tuskevich.instance.macroManager.getMacros().isEmpty()) {
                                ChatUtils.addChatMessage((Object)((Object)ChatFormatting.RED) + "Your macros list is empty!");
                                return;
                            }
                            tuskevich.instance.macroManager.getMacros().forEach(macro -> ChatUtils.addChatMessage((Object)((Object)ChatFormatting.GREEN) + "Macros list: " + (Object)((Object)ChatFormatting.WHITE) + "Macros Name: " + (Object)((Object)ChatFormatting.RED) + macro.getValue() + (Object)((Object)ChatFormatting.WHITE) + ", Macro Bind: " + (Object)((Object)ChatFormatting.RED) + Keyboard.getKeyName(macro.getKey())));
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

