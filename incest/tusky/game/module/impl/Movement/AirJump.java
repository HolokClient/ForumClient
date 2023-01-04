package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventPreMotion;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.Helper;
import org.lwjgl.input.Keyboard;

public class AirJump extends Module {

    public AirJump() {
        super("AirJump", "Позволяет прыгать по воздуху", ModuleCategory.Movement);
    }

    @EventTarget
    public void onUpdate(EventPreMotion eventPreMotion) {
        if ((!Helper.mc.world.getCollisionBoxes(Helper.mc.player, Helper.mc.player.getEntityBoundingBox().offset(0, -1, 0).expand(0.5, 0, 0.5)).isEmpty() && Helper.mc.player.ticksExisted % 2 == 0)) {
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                Helper.mc.player.jumpTicks = 0;
                Helper.mc.player.fallDistance = 0;
                eventPreMotion.setOnGround(true);
                Helper.mc.player.onGround = true;
            }
        }
    }
}
