package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.module.Module;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.packet.EventSendPacket;
import incest.tusky.game.event.events.impl.player.EventPreMotion;
import incest.tusky.game.event.events.impl.render.EventRender2D;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.render.RenderUtils;
import incest.tusky.game.utils.movement.MovementUtils;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class Timer extends Module {
    public static BooleanSetting smart;
    private NumberSetting timerAmount = new NumberSetting("Timer Amount", 2, 1, 10, 1);
    public float ticks = 0;
    public boolean active;
    public float animWidth;


    public Timer() {
        super("Timer", "Ускоряет вас", ModuleCategory.Movement);
        smart = new BooleanSetting("Smart", false);
        addSettings(timerAmount);
    }

    @EventTarget
    public void onSend(EventSendPacket eventSendPacket) {
        if (eventSendPacket.getPacket() instanceof CPacketPlayer.Position || eventSendPacket.getPacket() instanceof CPacketPlayer.PositionRotation) {
            if (ticks <= 25 && !active) {
                if (MovementUtils.isMoving())
                    ticks++;
            }
        }
    }

    @EventTarget
    public void onPreUpdate(EventPreMotion eventPreMotion) {
        if (smart.getCurrentValue()) {
            if (ticks <= 25 && !active) {
                if (MovementUtils.isMoving())
                    mc.timer.timerSpeed = timerAmount.getCurrentValue();
                else {
                    mc.timer.timerSpeed = 1;
                }
            }
            if (ticks >= 25) {
                active = true;
            }
            if (active) {
                mc.timer.timerSpeed = 1;
                if (!MovementUtils.isMoving())
                    ticks -= 1;
            }
            if (ticks <= 0) {
                active = false;
            }
        } else {

            mc.timer.timerSpeed = timerAmount.getCurrentValue();
        }
        ticks = MathHelper.clamp(ticks, 0, 100);
    }
    @EventTarget
    public void onRender(EventRender2D event2D) {
        if (smart.getCurrentValue()) {
            animWidth = (float) RenderUtils.interpolate((100 - (ticks * 4)), animWidth, 0.05f);

            mc.mntsb_16.drawCenteredString((int) Math.round(animWidth) + "%", (float) (sr.getScaledWidth() / 2), (float) (sr.getScaledHeight() * 1.87 - 10), -1);
            RenderUtils.drawRect2(sr.getScaledWidth() + 200, sr.getScaledHeight() * 1 , 102, 7, new Color(1, 1, 1, 50).getRGB());
            RenderUtils.drawRect2(sr.getScaledWidth() + 200, sr.getScaledHeight() * 1, animWidth, 5, new Color(155, 62, 224).getRGB());
            RenderUtils.drawBlurredShadow(sr.getScaledWidth() / 2 - 50, sr.getScaledHeight() * 1, animWidth, 5, 20, new Color(155, 62, 224));

        }
    }

    public void onDisable() {
        super.onDisable();
        active = true;
        ticks = 0;
        this.mc.timer.timerSpeed = 1.0f;
    }

}