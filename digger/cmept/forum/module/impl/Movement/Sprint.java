package digger.cmept.forum.module.impl.Movement;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.EventUpdate;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.utils.Helper;
import digger.cmept.forum.utils.movement.MovementUtils;

public class Sprint extends Module {
    public Sprint() {
        super("AutoSprint", ModuleCategory.Movement);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (Helper.mc.player.getFoodStats().getFoodLevel() / 2 > 3) {
            Helper.mc.player.setSprinting(MovementUtils.isMoving());
        }
    }

    @Override
    public void onDisable() {
        Helper.mc.player.setSprinting(false);
        super.onDisable();
    }
}
