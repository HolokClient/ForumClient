package digger.cmept.forum.module.impl.Render;


import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.NumberSetting;

public class ItemPhysics extends Module {
    public static NumberSetting physicsSpeed;

    public ItemPhysics() {
        super("ItemPhysics", ModuleCategory.Render);
                physicsSpeed = new NumberSetting("Physics Speed", 0.5F, 0.1F, 5.0F, 0.5F, () -> true);
        addSettings(physicsSpeed);
    }
}
