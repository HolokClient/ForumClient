package digger.cmept.forum.module.impl.Player;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.EventUpdate;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.notif.NotifModern;
import digger.cmept.forum.ui.notif.NotifRender;
import digger.cmept.forum.utils.Helper;
import digger.cmept.forum.utils.other.ChatUtils;
import net.minecraft.client.gui.GuiGameOver;

public class DeathCoordinates
        extends Module {
    public DeathCoordinates() {
        super("DeathCoords", "Показывает координаты вашей смерти", ModuleCategory.Player);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (Helper.mc.player.getHealth() < 1.0f && Helper.mc.currentScreen instanceof GuiGameOver) {
            int x = Helper.mc.player.getPosition().getX();
            int y = Helper.mc.player.getPosition().getY();
            int z = Helper.mc.player.getPosition().getZ();
            if (Helper.mc.player.deathTime < 1) {
                NotifRender.queue("DeathCoords", "X: " + x + " Y: " + y + " Z: " + z, 10, NotifModern.INFO);
                ChatUtils.addChatMessage("DeathCoords: X: " + x + " Y: " + y + " Z: " + z);
            }
        }
    }
}
