package net.minecraft.client.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import incest.tusky.game.tuskevich;
import incest.tusky.game.ui.FileHelper;
import incest.tusky.game.ui.altmanager.GuiAltManager;
import incest.tusky.game.ui.button.GuiMainMenuButton;
import incest.tusky.game.utils.math.animations.Animation;
import incest.tusky.game.utils.math.animations.impl.DecelerateAnimation;
import incest.tusky.game.utils.render.RenderUtils;
import incest.tusky.game.utils.render.RoundedUtil;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;



public class GuiMainMenu extends GuiScreen {
    private int width;
    public float scale = 2.0F;
    private int height;
    private Animation initAnimation;

    public GuiMainMenu() {
    }

    public void initGui() {
        ScaledResolution sr = new ScaledResolution(this.mc);
        this.width = sr.getScaledWidth();
        this.height = sr.getScaledHeight();
        this.initAnimation = new DecelerateAnimation(300, 1.0);
        this.buttonList.add(new GuiMainMenuButton(0, this.width / 2 - 80, this.height / 2 - 10, 180, 10, "Одиночная игра"));
        this.buttonList.add(new GuiMainMenuButton(1, this.width / 2 - 80, this.height / 2 + 18, 180, 10, "Список серверов"));
        this.buttonList.add(new GuiMainMenuButton(2, this.width / 2 - 80, this.height / 2 + 47, 180, 10, "Альт менеджер"));
        this.buttonList.add(new GuiMainMenuButton(3, this.width / 2 - 35, this.height / 2 + 75, 25, 13, ""));
        this.buttonList.add(new GuiMainMenuButton(4, this.width / 2 + 1, this.height / 2 + 75, 20, 10, ""));
        this.buttonList.add(new GuiMainMenuButton(5, this.width / 2 + 35, this.height / 2 + 75, 20, 10, ""));

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        new ScaledResolution(this.mc);
        new ScaledResolution(this.mc);
        RenderUtils.drawImage(new ResourceLocation("minced/bestfon.png"),0.01f, 0.01f, this.width, this.height, new Color(255,255,255,255));
        RoundedUtil.drawRoundOutline((float)(this.width / 2 - 88), (float)(this.height / 2 - 55), 200.0F, 180.0F, 10,0.1f,  new Color(0, 0, 0, 171),new Color(255,255,255));
        RoundedUtil.drawRoundOutline(this.width / 2 - 80, this.height / 2 - 10, 180, 20, 5,0.1f,  new Color(58, 58, 58, 124), new Color(255,255,255));
        RoundedUtil.drawRoundOutline(this.width / 2 - 80, this.height / 2 + 18, 180, 20, 5, 0.1f, new Color(58, 58, 58, 124),new Color(255,255,255));
        RoundedUtil.drawRoundOutline(this.width / 2 - 80, this.height / 2 + 47, 180, 20, 5, 0.1f,   new Color(58, 58, 58, 124), new Color(255,255,255));
        RoundedUtil.drawRoundOutline(this.width / 2 - 35, this.height / 2 + 75, 25, 25, 5, 0.1f,  new Color(58, 58, 58, 124), new Color(255,255,255));
        RenderUtils.drawImage(new ResourceLocation("minced/folder.png"),this.width / 2 - 31, this.height / 2 + 77, 18, 18, new Color(255,255,255,255));
        RoundedUtil.drawRoundOutline(this.width / 2 + 1, this.height / 2 + 75, 25, 25, 5, 0.1f, new Color(58, 58, 58, 124),new Color(255,255,255));
        RenderUtils.drawImage(new ResourceLocation("minced/close.png"), this.width / 2 + 3,this.height / 2 + 77, 20, 20, new Color(255,255,255,255));
        RoundedUtil.drawRoundOutline(this.width / 2 + 35, this.height / 2 + 75, 25, 25, 5, 0.1f,  new Color(58, 58, 58, 124),new Color(255,255,255));
        RenderUtils.drawImage(new ResourceLocation("minced/vk.png"), this.width / 2 + 37, this.height / 2 + 77, 20, 20, new Color(255,255,255,255));

        this.mc.tenacity20.drawStringWithShadow(ChatFormatting.WHITE + "MINCED", (double)(this.width / 2 - 27), (double)(this.height / 2 - 45), -1);
        this.mc.tenacity20.drawStringWithShadow(ChatFormatting.WHITE + "", (double)(this.width / 2 - 45), (double)(this.height / 2 - 36), -1);
        new ScaledResolution(this.mc);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                this.mc.displayGuiScreen(new GuiWorldSelection(this));
                break;
            case 1:
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 2:
                this.mc.displayGuiScreen(new GuiAltManager());
                break;
            case 3:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case 4:
                System.exit(0);
                tuskevich.instance.configManager.saveConfig("default");
                tuskevich.instance.fileManager.saveFiles();
            case 5:
                FileHelper.showURL("https://vk.com/mincedclient");
             break;
        }

        super.actionPerformed(button);
    }
}

