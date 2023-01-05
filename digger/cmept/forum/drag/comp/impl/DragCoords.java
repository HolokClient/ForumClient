package digger.cmept.forum.drag.comp.impl;

import digger.cmept.forum.forum;
import digger.cmept.forum.drag.comp.DragComp;
import digger.cmept.forum.module.impl.Render.Hud;

public class DragCoords extends DragComp {

    public DragCoords() {
        super("Coordinates", 350, 25, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return forum.instance.featureManager.getFeature(Hud.class).isEnabled() && Hud.coords.getCurrentValue();
    }
}
