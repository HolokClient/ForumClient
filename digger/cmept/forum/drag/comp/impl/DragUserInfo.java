package digger.cmept.forum.drag.comp.impl;

import digger.cmept.forum.drag.comp.DragComp;
import digger.cmept.forum.forum;
import digger.cmept.forum.module.impl.Render.Hud;

public class DragUserInfo extends DragComp {

    public DragUserInfo() {
        super("User Info", 500, 25, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return forum.instance.featureManager.getFeature(Hud.class).isEnabled();
    }
}
