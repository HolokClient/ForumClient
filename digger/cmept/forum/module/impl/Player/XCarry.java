package digger.cmept.forum.module.impl.Player;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.packet.EventSendPacket;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import net.minecraft.network.play.client.CPacketCloseWindow;

public class XCarry extends Module {
    public XCarry() {
        super("XCarry", ModuleCategory.Player);
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        if (event.getPacket() instanceof CPacketCloseWindow) {
            event.setCancelled(true);
        }
    }
}