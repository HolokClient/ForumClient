package digger.cmept.forum.module.impl.Util;

import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.BooleanSetting;

public class NameProtect extends Module {
    public static BooleanSetting myName = new BooleanSetting("My Name", false, () -> true);
    public static BooleanSetting friends = new BooleanSetting("Friends Spoof", true, () -> true);
    public static BooleanSetting otherName = new BooleanSetting("Other Names", true, () -> true);
    public static BooleanSetting tabSpoof = new BooleanSetting("Tab Spoof", true, () -> true);
    public static BooleanSetting scoreboardSpoof = new BooleanSetting("Scoreboard Spoof", true, () -> true);

    public NameProtect() {
        super("NameProtect", "Скрывает ваш ник и др.", ModuleCategory.Util);
        addSettings(otherName, friends, tabSpoof, scoreboardSpoof);
    }
}
