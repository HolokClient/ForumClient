package incest.tusky.game.module.impl.Render;

import incest.tusky.game.drag.comp.impl.DragModuleList;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.render.EventRender2D;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.tuskevich;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.ColorSetting;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.math.AnimationHelper;
import incest.tusky.game.utils.render.ClientHelper;
import incest.tusky.game.utils.render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class ModuleList extends Module {
    public float scale = 2;

    public static ListSetting colorList = new ListSetting("ArrayList Color", "Custom", () -> true, "Custom");
    public static ColorSetting oneColor = new ColorSetting("One Color", new Color(0x00FDF5).getRGB(), () -> colorList.currentMode.equals("Custom") || colorList.currentMode.equals("Fade"));
    public static ColorSetting twoColor = new ColorSetting("Two Color", new Color(0xFFFFFF).getRGB(), () -> colorList.currentMode.equals("Custom"));
    public BooleanSetting onlyBinds = new BooleanSetting("Only Binds", false, () -> true);
    public BooleanSetting noVisualModules = new BooleanSetting("No Visual", true, () -> true);
    public ModuleList() {
        super("ModuleList", "Показывает список включанных модулей на экране", ModuleCategory.Render);
        addSettings(colorList,oneColor,twoColor, noVisualModules, onlyBinds);
    }


    @EventTarget
    public void Event2D(EventRender2D event) {
        if (!isEnabled()) return;

        DragModuleList di = (DragModuleList) tuskevich.instance.draggableHUD.getDraggableComponentByClass(DragModuleList.class);
        di.setWidth(145);
        di.setHeight(100);
        int stringWidth;
        List<Module> activeModules = tuskevich.instance.featureManager.getAllFeatures();
        activeModules.sort(Comparator.comparingDouble(s -> -Helper.mc.mntsb_15.getStringWidth(s.getLabel().toLowerCase())));
        float displayWidth = di.getX();
        float y = di.getY();
        ScaledResolution rs = new ScaledResolution(this.mc);
        int width = tuskevich.scale.calc(rs.getScaledWidth());
        int height = tuskevich.scale.calc(rs.getScaledHeight());
        boolean reverse = displayWidth > (float)(width / 2);
        boolean reverseY = y > (float)(height / 2);
        int yTotal = 0;
        int offset = 1;

        for (int i = 0; i < tuskevich.instance.featureManager.getAllFeatures().size(); ++i) {
            yTotal += Helper.mc.mntsb_15.getFontHeight() + 3;
        }
        if(reverse){
            for (Module module : activeModules) {
                module.animYto = AnimationHelper.Move(module.animYto, (float) (module.isEnabled() ? 1 : 0), (float) (6.5f * tuskevich.deltaTime()), (float) (6.5f * tuskevich.deltaTime()), (float) tuskevich.deltaTime());
                if (module.animYto > 0.01f) {
                    if (module.getSuffix().equals("ClickGui") || noVisualModules.getCurrentValue() && module.getCategory() == ModuleCategory.Render || onlyBinds.getCurrentValue() && module.getBind() == 0)
                        continue;
                    stringWidth = this.mc.mntsb_15.getStringWidth(module.getLabel().toLowerCase()) + 3;
                    RenderUtils.drawRect4(displayWidth + 50 - Helper.mc.mntsb_15.getStringWidth(module.getLabel().toLowerCase()) - 5, y, displayWidth + 50, y + (float)offset + 8.2f,RenderUtils.injectAlpha(ClientHelper.getClientColor(y, yTotal, 5), 255).getRGB());
                    Helper.mc.mntsb_15.drawString(module.getLabel().toLowerCase(), displayWidth + 50.5f - Helper.mc.mntsb_15.getStringWidth(module.getLabel().toLowerCase()) - 4f, y + Helper.mc.mntsb_14.getFontHeight() + (float)offset - 4, -1);
                    RenderUtils.drawRect4(displayWidth + 49, y, displayWidth +51.5f, y + 8.2f + (float)offset, Color.WHITE.getRGB());

                    y += 8 * module.animYto;
                }

            }
        }


        if(!reverse){
            for (Module module : activeModules) {
                module.animYto = AnimationHelper.Move(module.animYto, (float) (module.isEnabled() ? 1 : 0), (float) (6.5f * tuskevich.deltaTime()), (float) (6.5f * tuskevich.deltaTime()), (float) tuskevich.deltaTime());


                if (module.animYto > 0.01f) {
                    if (module.getSuffix().equals("ClickGui") || noVisualModules.getCurrentValue() && module.getCategory() == ModuleCategory.Render || onlyBinds.getCurrentValue() && module.getBind() == 0)
                        continue;
                    stringWidth = this.mc.mntsb_15.getStringWidth(module.getLabel().toLowerCase()) + 3;
                    GlStateManager.pushMatrix();
                    RenderUtils.drawBlurredShadow(displayWidth -2, y + (float)offset - 3.5f + 2, stringWidth + 5f, 10, 5, RenderUtils.injectAlpha(ClientHelper.getClientColor(y, yTotal, 10), 150));
                    GL11.glTranslated(1, y, 1);
                    GL11.glTranslated(-1, -y, 1);
                    RenderUtils.drawRect(displayWidth, y - 0.5f -2 + offset + 2, displayWidth + (float)stringWidth + 3.5f, y + (float)offset + 8.0f, RenderUtils.injectAlpha(ClientHelper.getClientColor(y, yTotal, 10), (int) (animYto * 255)).getRGB());
                    this.mc.mntsb_15.drawString(module.getLabel().toLowerCase(), displayWidth + 3.5f, y + (float)offset + 2, Color.WHITE.getRGB());
                    RenderUtils.drawRect(displayWidth - 1.5f, y - 0.5f -2 + offset + 2, displayWidth + 1 , y + (float)offset +8f,  Color.WHITE.getRGB());
                    GlStateManager.popMatrix();

                }

                y += 8 * module.animYto * offset;

            }
        }
    }
}