package digger.cmept.forum.module.impl.Player;

import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.NumberSetting;

public class ItemScroller extends Module {

    public static NumberSetting scrollerDelay;

    public ItemScroller() {
        super("ItemScroller", "Скорость скролла предметов", ModuleCategory.Player);

        scrollerDelay = new NumberSetting("Scroller Delay", 80, 0, 100, 1, () -> true);
        addSettings(scrollerDelay);

    }
}