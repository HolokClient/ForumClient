package digger.cmept.forum.module.impl.Combat;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.OnEatFood;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.NumberSetting;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAppleGold;

public class AppleGoldenTimer extends Module {
    public static boolean cooldown;
    private boolean isEated;
    private NumberSetting ticks = new NumberSetting("Cooldown", 3, 1, 6, 0.5f, NumberSetting.NumberType.SEC);

    public AppleGoldenTimer() {
        super("CDApple", "CoolDown на поедание яблока.", ModuleCategory.Combat);
        addSettings(ticks);
    }

    @EventTarget
    public void onUpdate(OnEatFood eventUpdate) {
        if (!(eventUpdate.getItem().getItem() instanceof ItemAppleGold)) return;
        mc.player.getCooldownTracker().setCooldown(Items.GOLDEN_APPLE, ticks.getCurrentValueInt() * 20);
    }
}