package digger.cmept.forum.module.impl.Combat;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.EventUpdate;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import net.minecraft.entity.Entity;

public class AntiArmorStand extends Module {
    public AntiArmorStand() {
        super("AntiArmorStand", "Автоматически удаляет все армор-стенды с мира", ModuleCategory.Combat);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.player == null || mc.world == null) {
            return;
        }
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity == null || !(entity instanceof net.minecraft.entity.item.EntityArmorStand))
                continue;
            mc.world.removeEntity(entity);
        }
    }
}
