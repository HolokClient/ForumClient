package digger.cmept.forum.module.impl.Movement;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.packet.EventReceivePacket;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.utils.math.TimerHelper;
import digger.cmept.forum.utils.movement.MovementUtils;

public class DamageSpeed extends Module {
    public static float ticks = 0;
    public TimerHelper timerHelper = new TimerHelper();
    private boolean damage;
    public DamageSpeed() {
        super("DamageSpeed", "Увеличивает вашу скорость", ModuleCategory.Movement);

    }

    @EventTarget
    public void onUpdate(EventReceivePacket event) {
        double radian = MovementUtils.getAllDirection();
        if (!mc.gameSettings.keyBindSneak.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown() && !mc.player.isCollidedHorizontally  && this.damage &&  MovementUtils.isMoving()) {
            if (!mc.gameSettings.keyBindSneak.isKeyDown() && mc.player.onGround) {
                mc.player.addVelocity(-Math.sin(radian) * 9.5 / 24.5, 0, Math.cos(radian) * 9.5 / 24.5);
            }
        }
        if (!mc.gameSettings.keyBindSneak.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown() && !mc.player.isCollidedHorizontally  && this.damage && MovementUtils.isMoving()) {

            mc.player.isCollidedHorizontally = true;
            ticks += 1;
            //   ChatUtils.addChatMessage(ChatFormatting.AQUA + "[debug] " + ChatFormatting.GREEN + "current tick is" + " " + ChatFormatting.RED + ticks); //debug
        }

        if (isEnabled() &&
                event.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook) {
            super.onDisable();

        }

        if (DamageSpeed.mc.player.hurtTime == 9) {
            this.damage = true;
        }
        if (ticks == 17) {
            super.onDisable();
            this.damage = false;
        }
    }

    @Override
    public void onDisable() {
        ticks = 0;
        mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }
}