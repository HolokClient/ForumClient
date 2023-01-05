package digger.cmept.forum.drag.comp.impl;

import digger.cmept.forum.module.impl.Render.TargetHUD;
import digger.cmept.forum.forum;
import digger.cmept.forum.drag.comp.DragComp;

public class DragTargetHUD extends DragComp {

    public DragTargetHUD() {
        super("TargetHUD", 350, 25, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return forum.instance.featureManager.getFeature(TargetHUD.class).isEnabled();
    }
}
