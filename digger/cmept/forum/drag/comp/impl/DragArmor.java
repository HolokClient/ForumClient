package digger.cmept.forum.drag.comp.impl;

import digger.cmept.forum.forum;
import digger.cmept.forum.drag.comp.DragComp;
import digger.cmept.forum.module.impl.Render.Hud;

public class DragArmor extends DragComp
{
    public DragArmor()
    {
        super("Armor Status", 380, 357, 4, 1);
    }

    @Override
    public boolean allowDraw()
    {
        return forum.instance.featureManager.getFeature(Hud.class).isEnabled() && Hud.armorHUD.getCurrentValue();
    }
}
