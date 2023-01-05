package digger.cmept.forum.drag.comp.impl;

import digger.cmept.forum.forum;
import digger.cmept.forum.drag.comp.DragComp;
import digger.cmept.forum.module.impl.Render.ModuleList;

public class DragModuleList extends DragComp {

    public DragModuleList() {
        super("ModuleList", 350, 25, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return forum.instance.featureManager.getFeature(ModuleList.class).isEnabled();
    }
}