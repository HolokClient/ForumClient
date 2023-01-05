package digger.cmept.forum.module.impl.Combat;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.EventPreMotion;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.utils.Helper;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AntiBot extends Module {

    public AntiBot() {
        super("AntiBot", "��������� ��������� ����������� ��������� � ����-����", ModuleCategory.Combat);
        addSettings();
    }
    public static List<Entity> isBotPlayer = new ArrayList<>();

    @EventTarget
    public void onEvent(EventPreMotion event) {
        if (event instanceof EventPreMotion) {
            for (Entity entity : Helper.mc.world.playerEntities) {
                if (!entity.getUniqueID().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + entity.getName()).getBytes(StandardCharsets.UTF_8))) && entity instanceof EntityOtherPlayerMP) {
                    isBotPlayer.add(entity);
                }
                if (!entity.getUniqueID().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + entity.getName()).getBytes(StandardCharsets.UTF_8))) && entity.isInvisible() && entity instanceof EntityOtherPlayerMP) {
                    Helper.mc.world.removeEntity(entity);
                }
            }
        }
    }
}