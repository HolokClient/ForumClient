package digger.cmept.forum.drag.comp.impl;

import digger.cmept.forum.drag.comp.DragComp;
import digger.cmept.forum.forum;
import digger.cmept.forum.module.impl.Render.Hud;

public class DragWaterMark extends DragComp {
    public DragWaterMark() {
        super("WaterMark", 0, 1, 4, 1);
    }

    @Override
    public boolean allowDraw() {
        return forum.instance.featureManager.getFeature(Hud.class).isEnabled() && Hud.waterMark.getCurrentValue();
    }
}
