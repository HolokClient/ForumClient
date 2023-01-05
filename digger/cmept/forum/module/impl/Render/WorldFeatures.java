package digger.cmept.forum.module.impl.Render;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.packet.EventReceivePacket;
import digger.cmept.forum.event.events.impl.player.EventUpdate;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.BooleanSetting;
import digger.cmept.forum.ui.settings.impl.ColorSetting;
import digger.cmept.forum.ui.settings.impl.ListSetting;
import digger.cmept.forum.ui.settings.impl.NumberSetting;
import digger.cmept.forum.utils.Helper;
import net.minecraft.network.play.server.SPacketTimeUpdate;

import java.awt.*;

public class WorldFeatures extends Module {
    private long spinTime = 0;

    public static BooleanSetting snow = new BooleanSetting("Snow", true, () -> true);
    public static ColorSetting weatherColor = new ColorSetting("Weather", new Color(0xFFFFFF).getRGB(), () -> snow.getCurrentValue());
    public static BooleanSetting worldColor = new BooleanSetting("World Color", false, () -> true);
    public static ColorSetting worldColors = new ColorSetting("Color World", new Color(0xFFFFFF).getRGB(), () -> worldColor.getCurrentValue());
    public BooleanSetting ambience = new BooleanSetting("Ambience", false, () -> true);
    public ListSetting ambienceMode = new ListSetting("Ambience Mode", "Day", () -> ambience.getCurrentValue(), "Day", "Night", "Morning", "Sunset", "Spin");
    public NumberSetting ambienceSpeed = new NumberSetting("Ambience Speed", 20.f, 0.1f, 1000.f, 1, () -> ambienceMode.currentMode.equals("Spin"));

    public WorldFeatures() {
        super("WorldFeatures", ModuleCategory.Render);
        addSettings(snow, weatherColor, worldColor, worldColors, ambience, ambienceMode, ambienceSpeed);
    }

    @EventTarget
    public void onPacket(EventReceivePacket event) {
        if (ambience.getCurrentValue()) {
            if (event.getPacket() instanceof SPacketTimeUpdate) {
                event.setCancelled(true);
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        String mode = ambienceMode.getOptions();
        if (ambience.getCurrentValue()) {
            if (mode.equalsIgnoreCase("Spin")) {
                Helper.mc.world.setWorldTime(spinTime);
                this.spinTime = (long) (spinTime + ambienceSpeed.getCurrentValue());
            } else if (mode.equalsIgnoreCase("Day")) {
                Helper.mc.world.setWorldTime(5000);
            } else if (mode.equalsIgnoreCase("Night")) {
                Helper.mc.world.setWorldTime(17000);
            } else if (mode.equalsIgnoreCase("Morning")) {
                Helper.mc.world.setWorldTime(0);
            } else if (mode.equalsIgnoreCase("Sunset")) {
                Helper.mc.world.setWorldTime(13000);
            }
        }
    }
}
