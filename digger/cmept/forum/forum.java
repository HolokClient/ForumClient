package digger.cmept.forum;


import digger.cmept.forum.ui.celestun4ik.guiscreencomponent;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleManager;
import digger.cmept.forum.utils.otherutils.gayutil.ScaleUtils;
import digger.cmept.forum.cmd.macro.MacroManager;
import digger.cmept.forum.files.FileManager;
import digger.cmept.forum.files.impl.FriendConfig;
import digger.cmept.forum.files.impl.HudConfig;
import digger.cmept.forum.files.impl.MacroConfig;
import digger.cmept.forum.cmd.CommandManager;
import digger.cmept.forum.drag.DragModern;
import digger.cmept.forum.event.EventManager;
import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.input.EventInputKey;
import digger.cmept.forum.friend.FriendManager;
import digger.cmept.forum.utils.math.ShaderShell;
//import viamcp.ViaMCP;
import digger.cmept.forum.ui.config.ConfigManager;
import digger.cmept.forum.utils.math.RotationHelper;
import digger.cmept.forum.utils.math.TPSUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.IOException;

public class forum {
    public Long time;
    public static ScaleUtils scale = new ScaleUtils(2);
    public ModuleManager featureManager;
    public FileManager fileManager;
    public static long playTimeStart = 0;
    public DragModern draggableHUD;

    public MacroManager macroManager;
    public ConfigManager configManager;
    public EventManager eventManager;
    public CommandManager commandManager;

    public FriendManager friendManager;
    public guiscreencomponent clickGui;
    public static forum instance = new forum();
    public RotationHelper.Rotation rotation;

    public static double deltaTime() {
        return Minecraft.getDebugFPS() > 0 ? (1.0000 / Minecraft.getDebugFPS()) : 1;
    }

    public String name = "Forum", type = "Recode", version = "0.1";

    public void init() {
        Display.setTitle(name + " " + type + "  - https://vk.com/mincedclient");
        ShaderShell.init();
        time = System.currentTimeMillis();
        (fileManager = new FileManager()).loadFiles();
        friendManager = new FriendManager();
        featureManager = new ModuleManager();
        macroManager = new MacroManager();
        eventManager = new EventManager();
        configManager = new ConfigManager();
        draggableHUD = new DragModern();
        commandManager = new CommandManager();
        clickGui = new guiscreencomponent();
        rotation = new RotationHelper.Rotation();


        for (RenderPlayer render : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
        }
        TPSUtils tpsUtils = new TPSUtils();
        try {
            //  ViaMCP.getInstance().start();
           // ViaMCP.getInstance().initAsyncSlider();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.fileManager.getFile(FriendConfig.class).loadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.fileManager.getFile(MacroConfig.class).loadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.fileManager.getFile(HudConfig.class).loadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        EventManager.register(this);
        EventManager.register(this.rotation);

    }

    public final Color getClientColor() {
        return new Color(236, 133, 209);
    }

    public final Color getAlternateClientColor() {
        return new Color(28, 167, 222);
    }

    public void stop() {
        forum.instance.configManager.saveConfig("default");
        (fileManager = new FileManager()).saveFiles();
        EventManager.unregister(this);
    }

    @EventTarget
    public void onInputKey(EventInputKey event) {
        featureManager.getAllFeatures().stream().filter(module -> module.getBind() == event.getKey()).forEach(Module::toggle);
        macroManager.getMacros().stream().filter(macros -> macros.getKey() == event.getKey()).forEach(macros -> Minecraft.getMinecraft().player.sendChatMessage(macros.getValue()));
    }
}
