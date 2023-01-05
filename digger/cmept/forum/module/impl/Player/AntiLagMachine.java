package digger.cmept.forum.module.impl.Player;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.utils.otherutils.gayutil.EventLight;
import net.minecraft.world.EnumSkyBlock;

public class AntiLagMachine extends Module {
    public AntiLagMachine() {
        super("AntiLagMachine", "", ModuleCategory.Player);
    }
    @EventTarget
    public void onWorldLight(EventLight event) {
        if (event.getEnumSkyBlock() == EnumSkyBlock.SKY) {
            event.cancel();
        }
    }

}