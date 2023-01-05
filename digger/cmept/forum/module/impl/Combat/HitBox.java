package digger.cmept.forum.module.impl.Combat;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.EventUpdate;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.NumberSetting;

public class HitBox extends Module {

    public static NumberSetting hitboxSize = new NumberSetting("HitBox Size", "Размер хитбокса игрока", 0.25f, 0.1f, 2.f, 0.1f, () -> true);

    public HitBox() {
        super("HitBox", "Увеличивает хитбокс игрока.", ModuleCategory.Combat);
        addSettings(hitboxSize);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
    }
}
