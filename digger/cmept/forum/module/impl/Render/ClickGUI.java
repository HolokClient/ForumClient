package digger.cmept.forum.module.impl.Render;

import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.forum;
import digger.cmept.forum.ui.settings.impl.BooleanSetting;
import digger.cmept.forum.ui.settings.impl.ColorSetting;
import digger.cmept.forum.ui.settings.impl.NumberSetting;
import digger.cmept.forum.utils.Helper;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ClickGUI extends Module {
    public static BooleanSetting shadow = new BooleanSetting("Shadow", true, () -> true);

    public static BooleanSetting blur = new BooleanSetting("Blur", false, () -> true);
    public static ColorSetting color;
    public static ColorSetting color2;
    public static NumberSetting speed;

    public ClickGUI() {
        super("Click GUI", "���� ����", ModuleCategory.Render);
        setBind(Keyboard.KEY_RSHIFT);
        color = new ColorSetting("Gui Color", new Color(158, 13, 239, 255).getRGB(), () -> true);
        color2 = new ColorSetting("Gui Color 2", new Color(0, 0, 0).getRGB(), () -> true);
        addSettings(color,color2, blur , shadow, speed);

    }

    @Override
    public void onEnable() {
        Helper.mc.displayGuiScreen(forum.instance.clickGui);
        forum.instance.featureManager.getFeature(ClickGUI.class).setEnabled(false);
        super.onEnable();
    }
}