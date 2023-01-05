package digger.cmept.forum.module.impl.Player;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.packet.EventReceivePacket;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.utils.Helper;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class NoServerRotations extends Module {

    public NoServerRotations() {
        super("NoRotate", "Отключает ротации сервера", ModuleCategory.Player);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();
            packet.yaw = Helper.mc.player.rotationYaw;
            packet.pitch = Helper.mc.player.rotationPitch;
        }
    }
}