package digger.cmept.forum.module.impl.Player;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.EventUpdate;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.utils.Helper;

public class NoJumpDelay extends Module {
    public NoJumpDelay() {
        super("NoJumpDelay", "Нету задержки на прыжок", ModuleCategory.Player);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        Helper.mc.player.setJumpTicks(0);
    }
}
