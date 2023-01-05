package digger.cmept.forum.module.impl.Render;

import com.mojang.realmsclient.gui.ChatFormatting;
import digger.cmept.forum.drag.comp.impl.*;
import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.render.EventRender2D;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.forum;
import digger.cmept.forum.ui.settings.impl.BooleanSetting;
import digger.cmept.forum.ui.settings.impl.ListSetting;
import digger.cmept.forum.utils.Helper;
import digger.cmept.forum.utils.render.ClientHelper;
import digger.cmept.forum.utils.render.GLUtils;
import digger.cmept.forum.utils.render.RenderUtils;
import digger.cmept.forum.utils.render.RoundedUtil;
import incest.tusky.game.drag.comp.impl.*;
import digger.cmept.forum.module.impl.Util.NameProtect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import optifine.CustomColors;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Hud extends Module {
    public static BooleanSetting waterMark = new BooleanSetting("WaterMark", true, () -> true);
    public static ListSetting waterMarkMode = new ListSetting("WaterMark Mode", "Minced", () -> waterMark.getCurrentValue(), "Minced", "Celestial");
    public static BooleanSetting coords = new BooleanSetting("Coordinates", true, () -> true);
    public static BooleanSetting sessionInfo = new BooleanSetting("Session Info", false, () -> true);
    public static BooleanSetting armorHUD = new BooleanSetting("Armor Status", true, () -> true);
    public static BooleanSetting potions = new BooleanSetting("Potion Info", true, () -> true);
    public Color color;
    private double cooldownBarWidth;
    private double hurttimeBarWidth;
    public float scale = 2;

    public Hud() {
        super("Hud", "Визуальные компоненты клиента", ModuleCategory.Render);
        addSettings(waterMark, waterMarkMode, potions, armorHUD, coords);
    }

    private Color gradientColor1 = Color.WHITE, gradientColor2 = Color.WHITE;

    @EventTarget
    public void onRender(EventRender2D eventRender2D) {
        if (waterMark.getCurrentValue()) {
            switch (waterMarkMode.currentMode) {
                case "Minced": {
                    DragWaterMark pon = (DragWaterMark) forum.instance.draggableHUD.getDraggableComponentByClass(DragWaterMark.class);
                    RoundedUtil.drawRound(10, 8, 90, 15, 4,new Color(241, 241, 241, 255));
                    RenderUtils.drawBlurredShadow(1,5,35,25,35, new Color(255, 0, 0, 81));
                    RoundedUtil.drawRound(1, 7, 25, 25, 12, new Color(255,255,255,255));
                    RenderUtils.drawImage(new ResourceLocation("celestial/images/notification/" + "logo" + ".png"), 3,  8 , 19 + 3, 19 + 3,  ClientHelper.getClientColor(getY(), getY3(), 20));
                    mc.mntsb_18.drawStringWithShadow("MINCED" + " FREE", 27, 12,  ClientHelper.getClientColor(getY(), getY3(), 20).getRGB());
                    GLUtils.INSTANCE.rescaleMC();
                    break;
                }
                case "Celestial": {
                    DragWaterMark dwm = (DragWaterMark) forum.instance.draggableHUD.getDraggableComponentByClass(DragWaterMark.class);

                    String server;
                    if (this.mc.isSingleplayer()) {
                        server = "Одиночный режим";
                    } else {
                        assert (this.mc.getCurrentServerData() != null);
                        server = this.mc.getCurrentServerData().serverIP.toLowerCase();
                    }
                    dwm.setWidth(100);
                    dwm.setHeight(150);
                    GLUtils.INSTANCE.rescale(scale);
                    int x = 21;
                    int y = 14;
                    int yOffset = 16;
                    RoundedUtil.drawGradientRound(10, 10, 80, 22, 4, ClientHelper.getClientColor(getY(), getY3(), 20), ClientHelper.getClientColor(getY(), getY3(), 20), new Color(255, 255, 255),  new Color(255, 255, 255));
                    RenderUtils.drawBlurredShadow(1,5,20,20,25, new Color(255, 255, 255, 90));
                    RoundedUtil.drawGradientRound(1, 7, 30, 30, 15, ClientHelper.getClientColor(getY(), getY3(), 20), ClientHelper.getClientColor(getY(), getY3(), 20), new Color(255, 255, 255),  new Color(255, 255, 255));
                    RenderUtils.drawImage(new ResourceLocation("celestial/images/notification/" + "logo" + ".png"), 5,  10 , 19 + 3, 19 + 3, new Color(255,255,255,255));

                    mc.mntsb_18.drawStringWithShadow(ChatFormatting.WHITE  +"Minced", 35, 14, -1);
                    mc.mntsb_15.drawStringWithShadow(ChatFormatting.WHITE + "UID: " + ChatFormatting.GRAY+ "tusky", 35, 24, -1);
                    GLUtils.INSTANCE.rescaleMC();
                    break;
                }
            }

        }
        if (armorHUD.getCurrentValue()) {
            DragArmor das = (DragArmor) forum.instance.draggableHUD.getDraggableComponentByClass(DragArmor.class);
            das.setWidth(100);
            das.setHeight(30);
            GlStateManager.pushMatrix();
            GlStateManager.enableTexture2D();
            int i = das.getX();
            int y = das.getY();
            int count = 0;
            for (ItemStack is : Helper.mc.player.inventory.armorInventory) {
                ++count;
                int x = i - 90 + (9 - count) * 20 + 2;
                GlStateManager.enableDepth();
                Helper.mc.getRenderItem().zLevel = 200.0f;
                Helper.mc.getRenderItem().renderItemAndEffectIntoGUI(is, x, y);
                Helper.mc.getRenderItem().renderItemOverlayIntoGUI(Helper.mc.tenacity, is, x, y, "");
                Helper.mc.getRenderItem().zLevel = 0.0f;
                GlStateManager.enableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
            }
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
            GlStateManager.popMatrix();
        }
        if (coords.getCurrentValue()) {
            DragCoords dci = (DragCoords) forum.instance.draggableHUD.getDraggableComponentByClass(DragCoords.class);
            dci.setWidth(90);
            dci.setHeight(25);

            String xCoord = "" + Math.round(Helper.mc.player.posX);
            String yCoord = "" + Math.round(Helper.mc.player.posY);
            String zCoord = "" + Math.round(Helper.mc.player.posZ);
            String fps = "" + Math.round(Minecraft.getDebugFPS());
            String bps = "" + calculateBPS();
            String ping = "" + Objects.requireNonNull(mc.getConnection()).getPlayerInfo(mc.player.getUniqueID()).getResponseTime();
            mc.mntsb_16.drawStringWithShadow("BPS: ", getX(), getY()  + 3, -1);
            mc.mntsb_16.drawStringWithShadow(ChatFormatting.GRAY + bps, getX() + 22, getY()  + 3, -1);
            mc.mntsb_16.drawStringWithShadow("FPS: ", getX(), getY()  - 5, -1);
            mc.mntsb_16.drawStringWithShadow(ChatFormatting.GRAY + fps, getX() + 22, getY() -5, -1);
            mc.mntsb_16.drawStringWithShadow("XYZ: ", getX(), getY()  + 11, -1);
            mc.mntsb_16.drawStringWithShadow(ChatFormatting.GRAY + xCoord, getX() + 22, getY() + 11, -1);
            mc.mntsb_16.drawStringWithShadow(ChatFormatting.GRAY + yCoord, getX() + 47 + Helper.mc.mntsb_15.getStringWidth(xCoord) - 17, getY()+ 11, -1);
            mc.mntsb_16.drawStringWithShadow(ChatFormatting.GRAY + zCoord, getX() + 75 + Helper.mc.mntsb_15.getStringWidth(xCoord) - 23 + Helper.mc.mntsb_15.getStringWidth(yCoord) - 17, getY() + 11, -1);
        }
        if (potions.getCurrentValue()) {
            DragPotion dph = (DragPotion) forum.instance.draggableHUD.getDraggableComponentByClass(DragPotion.class);
            dph.setWidth(100);
            dph.setHeight(150);
            GLUtils.INSTANCE.rescale(scale);
            int x = 21;
            int y = 14;
            int yOffset = 16;
            Collection<PotionEffect> collection = Helper.mc.player.getActivePotionEffects();
            {
                GlStateManager.color(1F, 1F, 1F, 1F);
                GlStateManager.disableLighting();
                int listOffset = 23;
                if (collection.size() > 5) {
                    listOffset = 132 / (collection.size() - 1);
                }
                List<PotionEffect> potions = new ArrayList<>(Helper.mc.player.getActivePotionEffects());
                potions.sort(Comparator.comparingDouble(effect -> -Helper.mc.mntsb_14.getStringWidth((Objects.requireNonNull(Potion.getPotionById(CustomColors.getPotionId(effect.getEffectName()))).getName()))));

                for (PotionEffect potion : potions) {

                    Potion effect = Potion.getPotionById(CustomColors.getPotionId(potion.getEffectName()));

                    String potionPower = I18n.format(effect.getName());
                    if (potion.getAmplifier() == 1) {
                        potionPower = potionPower + " " + I18n.format("enchantment.level.2");
                    } else if (potion.getAmplifier() == 2) {
                        potionPower = potionPower + " " + I18n.format("enchantment.level.3");
                    } else if (potion.getAmplifier() == 3) {
                        potionPower = potionPower + " " + I18n.format("enchantment.level.4");
                    }

                    if (effect.hasStatusIcon()) {
                        GlStateManager.color(1F, 1F, 1F, 1F);
                        Helper.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                        int statusIconIndex = effect.getStatusIconIndex();
                        new Gui().drawTexturedModalRect((float) ((dph.getX() + x) - 20), (dph.getY() + yOffset) - y - 4, statusIconIndex % 8 * 18, 198 + statusIconIndex / 8 * 18, 18, 18);
                    }
                    int potionColor = -1;
                    if ((potion.getDuration() < 200)) {
                        potionColor = new Color(215, 59, 59).getRGB();
                    } else if (potion.getDuration() < 400) {
                        potionColor = new Color(231, 143, 32).getRGB();
                    } else if (potion.getDuration() > 400) {
                        potionColor = new Color(172, 171, 171).getRGB();
                    }
                    Helper.mc.mntsb_15.drawStringWithShadow(ChatFormatting.WHITE + potionPower, dph.getX() + x, (dph.getY() + yOffset) - y, -1);
                    Helper.mc.mntsb_15.drawStringWithShadow(Potion.getDurationString(potion), dph.getX() + x, (dph.getY() + yOffset + 10) - y, potionColor);
                    yOffset += listOffset;
                }
                GLUtils.INSTANCE.rescaleMC();
            }
        }if (sessionInfo.getCurrentValue()) {
            DragSessionInfo dsi = (DragSessionInfo) forum.instance.draggableHUD.getDraggableComponentByClass(DragSessionInfo.class);
            dsi.setWidth(155);
            dsi.setHeight(56);
            GLUtils.INSTANCE.rescale(scale);
            String server = Helper.mc.isSingleplayer() ? "Херобрин в сети! ТИШ" : Helper.mc.getCurrentServerData() != null ? Helper.mc.getCurrentServerData().serverIP.toLowerCase() : "null";
            String name = forum.instance.featureManager.getFeature(NameProtect.class).isEnabled() && NameProtect.myName.getCurrentValue() ? "" + "Protected" : " " + Helper.mc.player.getName();
            String time;
            if (Minecraft.getMinecraft().isSingleplayer()) {
                time =(new SimpleDateFormat("HH:mm")).format(Calendar.getInstance().getTime());
            } else {
                 time = (new SimpleDateFormat("HH:mm")).format(Calendar.getInstance().getTime());
            }
            RenderUtils.drawBlurredShadow(dsi.getX(),dsi.getY(), 130,56,15, new Color(255,255,255));
            RoundedUtil.drawRoundOutline(dsi.getX(),dsi.getY(), 130, 56, 10,3,new Color(0,0,0, 0), new Color(255, 255, 255));
            RoundedUtil.drawGradientRound(dsi.getX(), dsi.getY(), 130,56,10, ClientHelper.getClientColor(getY(), getY3(), 20), new Color(208, 208, 208), new Color(190, 190, 190), ClientHelper.getClientColor(getY(), getY3(), 20));
            Helper.mc.neverlose900_20.drawStringWithShadow("    Information Session", dsi.getX()+ 5, dsi.getY() + 6, -1);
            Helper.mc.tenacity.drawStringWithShadow("nick: "+ChatFormatting.RED +  name, dsi.getX() + 5, dsi.getY() + 18, -1);
            Helper.mc.tenacity.drawStringWithShadow("server ip: " +ChatFormatting.RED +  server, dsi.getX() + 5, dsi.getY() + 27, -1);
            Helper.mc.tenacity.drawStringWithShadow("server online: " +ChatFormatting.RED + mc.getConnection().getPlayerInfoMap().size(), dsi.getX() + 5, dsi.getY() + 37.5f, -1);
            Helper.mc.tenacity.drawStringWithShadow("time " + "on pc: "+ ChatFormatting.RED + time, dsi.getX() + 5, dsi.getY() + 46, -1);

            GLUtils.INSTANCE.rescaleMC();
        }
    }

    private double calculateBPS() {
        double bps = (Math.hypot(Helper.mc.player.posX - Helper.mc.player.prevPosX, Helper.mc.player.posZ - Helper.mc.player.prevPosZ) * Helper.mc.timer.timerSpeed) * 20;
        return Math.round(bps * 100.0) / 100.0;
    }

    public float getX2() {
        DragUserInfo dci = (DragUserInfo) forum.instance.draggableHUD.getDraggableComponentByClass(DragUserInfo.class);

        return dci.getX();
    }

    public float getY2() {
        DragUserInfo dci = (DragUserInfo) forum.instance.draggableHUD.getDraggableComponentByClass(DragUserInfo.class);

        return dci.getY() + 10;
    }

    public float getY3() {
        DragWaterMark dwm = (DragWaterMark) forum.instance.draggableHUD.getDraggableComponentByClass(DragWaterMark.class);

        return dwm.getY() - 5;
    }

    public float getX3() {
        DragWaterMark dwm = (DragWaterMark) forum.instance.draggableHUD.getDraggableComponentByClass(DragWaterMark.class);

        return dwm.getX() - 5;
    }

    public float getX() {
        DragCoords dci = (DragCoords) forum.instance.draggableHUD.getDraggableComponentByClass(DragCoords.class);

        return dci.getX();
    }

    public float getY() {
        DragCoords dci = (DragCoords) forum.instance.draggableHUD.getDraggableComponentByClass(DragCoords.class);

        return dci.getY() + 15;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }
}
