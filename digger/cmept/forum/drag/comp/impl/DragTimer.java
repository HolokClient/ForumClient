package digger.cmept.forum.drag.comp.impl;

import digger.cmept.forum.module.impl.Movement.Timer;
import digger.cmept.forum.forum;
import digger.cmept.forum.drag.comp.DragComp;

public class DragTimer extends DragComp {

    public DragTimer() {
        super("Timer", 160, 400, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return forum.instance.featureManager.getFeature(Timer.class).isEnabled();
    }
}
