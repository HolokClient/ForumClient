package digger.cmept.forum.module.impl.Combat;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.EventPreMotion;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.NumberSetting;
import digger.cmept.forum.utils.Helper;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;

public class AutoGapple extends Module {
    private boolean isActive;
    public static NumberSetting health;

    public AutoGapple() {
        super("AutoGApple", "Автоматически кушает гепл", ModuleCategory.Combat);
        health = new NumberSetting("Health Amount", 15, 1, 20, 1, () -> true);
        addSettings(health);
    }

    @EventTarget
    public void onUpdate(EventPreMotion eventUpdate) {
        if (Helper.mc.player == null || Helper.mc.world == null)
            return;
        if (isGoldenApple(Helper.mc.player.getHeldItemOffhand()) && Helper.mc.player.getHealth() <= health.getCurrentValue()) {
            isActive = true;
            Helper.mc.gameSettings.keyBindUseItem.pressed = true;
        } else if (isActive) {
            Helper.mc.gameSettings.keyBindUseItem.pressed = false;
            isActive = false;
        }
    }

    private boolean isGoldenApple(ItemStack itemStack) {
        return (itemStack != null && !itemStack.isEmpty() && itemStack.getItem() instanceof ItemAppleGold);
    }
}
