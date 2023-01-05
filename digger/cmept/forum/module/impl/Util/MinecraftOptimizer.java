package digger.cmept.forum.module.impl.Util;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.EventUpdate;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.BooleanSetting;
import digger.cmept.forum.utils.otherutils.gayutil.EventLight;
import net.minecraft.world.EnumSkyBlock;

public class MinecraftOptimizer extends Module {
    public static BooleanSetting entity;
    public MinecraftOptimizer() {
        super("MinecraftOptimizer", "", ModuleCategory.Util);
        entity = new BooleanSetting("del entity", false, () -> true);
        addSettings(entity);
    }
    public BooleanSetting light = new BooleanSetting("Light", true);
    public BooleanSetting entities = new BooleanSetting("Entities", true);

    @EventTarget
    public void onWorldLight(EventLight event) {
        if (light.getCurrentValue()) {
            if (event.getEnumSkyBlock() == EnumSkyBlock.SKY) {
                event.cancel();
            }
            if (event.getEnumSkyBlock() == EnumSkyBlock.BLOCK) {
                event.cancel();
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {

    }

}
