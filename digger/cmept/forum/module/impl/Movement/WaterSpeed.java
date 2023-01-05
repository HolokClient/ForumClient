package digger.cmept.forum.module.impl.Movement;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.EventUpdate;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.BooleanSetting;
import digger.cmept.forum.ui.settings.impl.NumberSetting;
import digger.cmept.forum.utils.Helper;
import digger.cmept.forum.utils.movement.MovementUtils;
import net.minecraft.init.MobEffects;

public class WaterSpeed extends Module {

    private final NumberSetting speed;
    private final BooleanSetting speedCheck;

    public WaterSpeed() {
        super("WaterSpeed", "Позволяет быстро бегать в воде" , ModuleCategory.Movement);
        speed = new NumberSetting("Speed Amount", 0.4f, 0.1F, 4, 0.01F, () -> true);
        speedCheck = new BooleanSetting("Potion Check", false, () -> true);
        addSettings(speedCheck, speed);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (!Helper.mc.player.isPotionActive(MobEffects.SPEED) && speedCheck.getCurrentValue()) {
            return;
        }
        if (Helper.mc.player.isInWater() || Helper.mc.player.isInLava()) {
            MovementUtils.setSpeed(speed.getCurrentValue());
        }
    }
}
