package digger.cmept.forum.module.impl.Combat;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.packet.EventReceivePacket;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.BooleanSetting;
import digger.cmept.forum.ui.settings.impl.ListSetting;
import digger.cmept.forum.utils.Helper;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity extends Module {
    public static BooleanSetting cancelOtherDamage;
    public static ListSetting velocityMode;


    public Velocity() {
        super("Velocity", "Убирает отталкивание при ударе", ModuleCategory.Combat);
        velocityMode = new ListSetting("Velocity Mode", "Packet", () -> true, "Packet", "Matrix");
        cancelOtherDamage = new BooleanSetting("Pon", true, () -> true);
        addSettings(velocityMode);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        String mode = velocityMode.getOptions();
            if (cancelOtherDamage.getCurrentValue() && Helper.mc.player.hurtTime > 0 && event.getPacket()
                    instanceof SPacketEntityVelocity && !Helper.mc.player.isPotionActive(MobEffects.FIRE_RESISTANCE) &&
                    (Helper.mc.player.isPotionActive(MobEffects.POISON) || Helper.mc.player.isPotionActive(MobEffects.WITHER) || Helper.mc.player.isBurning())) {
                event.setCancelled(true);
        }
        if (mode.equalsIgnoreCase("Packet")) {
            if ((event.getPacket() instanceof SPacketEntityVelocity || event.getPacket() instanceof SPacketExplosion) &&
                    ((SPacketEntityVelocity)event.getPacket()).getEntityID() == Helper.mc.player.getEntityId()) {
                event.setCancelled(true);
            }
        } else if (mode.equals("Matrix")) {
            if (Helper.mc.player.hurtTime > 8) {
                Helper.mc.player.onGround = true;
            }
        }
    }
}