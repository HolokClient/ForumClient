package digger.cmept.forum.module.impl.Movement;

import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.packet.EventReceivePacket;
import digger.cmept.forum.forum;
import digger.cmept.forum.ui.notif.NotifModern;
import digger.cmept.forum.ui.notif.NotifRender;
import digger.cmept.forum.ui.settings.Setting;
import digger.cmept.forum.ui.settings.impl.BooleanSetting;


public class AntiFlag extends Module {
    public static BooleanSetting disable = new BooleanSetting("Auto Disable", true, () -> true);

    public AntiFlag() {
        super("AntiFlag", "������������� ��������� ������ ��� ��� �������", ModuleCategory.Movement);
        addSettings(new Setting[]{(Setting) disable});
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        if (isEnabled() &&
                event.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook) {
            if (forum.instance.featureManager.getFeature(Speed.class).isEnabled()) {
                featureAlert("Speed");
                if (disable.getCurrentValue()) {
                    forum.instance.featureManager.getFeature(Speed.class).toggle();
                }
            } else if (forum.instance.featureManager.getFeature(Spider.class).isEnabled() && mc.player.isCollidedHorizontally) {
                featureAlert("Spider");
                if (disable.getCurrentValue())
                    forum.instance.featureManager.getFeature(Spider.class).toggle();
            } else if (forum.instance.featureManager.getFeature(elytrafly.class).isEnabled()) {
                featureAlert("ElytraLeaveSunrise");
                if (disable.getCurrentValue()) {
                    forum.instance.featureManager.getFeature(elytraleave.class).toggle();
                }
            } else if (forum.instance.featureManager.getFeature(elytrafly.class).isEnabled() && mc.player.isInLiquid()) {
                featureAlert("ElytraFlySunrise");
                if (disable.getCurrentValue()) {
                    forum.instance.featureManager.getFeature(elytrafly.class).toggle();
                }
            } else if (forum.instance.featureManager.getFeature(Timer.class).isEnabled()) {
                featureAlert("Timer");
                if (disable.getCurrentValue()) {
                    forum.instance.featureManager.getFeature(Timer.class).toggle();
                }
            }
        }
    }

    public void featureAlert(String feature) {
        NotifRender.queue("Anti Flag Debug", "Module " + feature + " was flagged" + (mc.player.isInWater() ? " on water" : "") + (mc.player.isInLava() ? " in lava" : "") + "!", 3, NotifModern.WARNING);
    }
}
