package digger.cmept.forum.module.impl.Render;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.EventUpdate;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.ListSetting;
import digger.cmept.forum.utils.Helper;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@SuppressWarnings("all")
public class FullBright extends Module {

    private final ListSetting brightMode = new ListSetting("FullBright Mode", "Gamma", () -> true, "Gamma", "Potion");
    ;

    public FullBright() {
        super("FullBright", ModuleCategory.Render);
        addSettings(brightMode);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (isEnabled()) {
            String mode = brightMode.getOptions();
            if (mode.equalsIgnoreCase("Gamma")) {
                Helper.mc.gameSettings.gammaSetting = 10f;
            }
            if (mode.equalsIgnoreCase("Potion")) {
                Helper.mc.player.addPotionEffect(new PotionEffect(Potion.getPotionById(16), 817, 1));
            } else {
                Helper.mc.player.removePotionEffect(Potion.getPotionById(16));
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Helper.mc.gameSettings.gammaSetting = 0.1f;
        Helper.mc.player.removePotionEffect(Potion.getPotionById(16));
    }
}
