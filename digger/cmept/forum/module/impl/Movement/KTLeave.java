package digger.cmept.forum.module.impl.Movement;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.EventUpdate;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.utils.other.ChatUtils;
import net.minecraft.network.play.client.CPacketPlayer;

public class KTLeave extends Module
{

    public KTLeave() {
        super("KTLeave", "", ModuleCategory.Movement);
    }


    @EventTarget
    public void onUpdate(final EventUpdate event) {
        float endX = 15900;
        float endZ = -1000;
        float endY = 70;
        if(mc.player.isSneaking() && mc.player.ticksExisted % 8 == 0) {
            ChatUtils.addChatMessage("  Мобилизирую на заданные мне координаты " + endX + " " + endY + " " + endZ);
            if (mc.player.posX != endX && mc.player.posZ != endZ) {
                mc.player.motionY = 0.05f;
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX + 0.5, endY, endZ - 0.5, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX, endY + 109, endZ, true));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX + 0.5, endY, endZ - 0.5, true));
            }
        }
    }
}
