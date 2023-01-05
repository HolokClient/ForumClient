package digger.cmept.forum.module.impl.Util;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.EventUpdate;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.utils.inventory.InventoryUtil;

public class ElytraFix extends Module {
    public ElytraFix() {
        super("ElytraFix", "Свапает элитру назад", ModuleCategory.Util);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (mc.player.ticksExisted % 4 == 0) {
            InventoryUtil.swapElytraToChestplate();
        }
    }
}
