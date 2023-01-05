package digger.cmept.forum.module.impl.Util;

import com.mojang.realmsclient.gui.ChatFormatting;
import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.input.EventMouse;
import digger.cmept.forum.friend.Friend;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.forum;
import digger.cmept.forum.utils.Helper;
import digger.cmept.forum.utils.other.ChatUtils;
import net.minecraft.entity.player.EntityPlayer;

public class MiddleClickFriend extends Module {


    public MiddleClickFriend() {
        super("MiddleClickFriend", "Добавить игрока в друзья(колесиком мыши)", ModuleCategory.Util);
    }

    @EventTarget
    public void onMouseEvent(EventMouse event) {
        if (event.getKey() == 2 && Helper.mc.pointedEntity instanceof EntityPlayer) {
            if (forum.instance.friendManager.getFriends().stream().anyMatch(friend -> friend.getName().equals(Helper.mc.pointedEntity.getName()))) {
                forum.instance.friendManager.getFriends().remove(forum.instance.friendManager.getFriend(Helper.mc.pointedEntity.getName()));
                ChatUtils.addChatMessage(ChatFormatting.RED + "Removed " + ChatFormatting.RESET + "'" + Helper.mc.pointedEntity.getName() + "'" + " as Friend!");
            } else {
                forum.instance.friendManager.addFriend(new Friend(Helper.mc.pointedEntity.getName()));
                ChatUtils.addChatMessage(ChatFormatting.GREEN + "Added " + ChatFormatting.RESET + "'" + Helper.mc.pointedEntity.getName() + "'" + " as Friend!");
            }
        }
    }
}