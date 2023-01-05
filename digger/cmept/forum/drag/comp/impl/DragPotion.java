package digger.cmept.forum.drag.comp.impl;

import digger.cmept.forum.forum;
import digger.cmept.forum.drag.comp.DragComp;
import digger.cmept.forum.module.impl.Render.Hud;

public class DragPotion extends DragComp {

    public DragPotion() {
        super("Potion Status", 50, 100, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return forum.instance.featureManager.getFeature(Hud.class).isEnabled() && Hud.potions.getCurrentValue();
    }
}
