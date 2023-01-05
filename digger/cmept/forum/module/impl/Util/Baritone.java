package digger.cmept.forum.module.impl.Util;

import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.BooleanSetting;
import digger.cmept.forum.ui.settings.impl.NumberSetting;
import digger.cmept.forum.utils.other.ChatUtils;

public class Baritone extends Module {
    public static BooleanSetting randomizeRotations;

    public static NumberSetting randomizeValue;

    public static BooleanSetting autoDropTrash;

    public static BooleanSetting noBack;

    public static BooleanSetting inventoryFullLeave;

    public Baritone() {
        super("Baritone", "Исскуственный интелект", ModuleCategory.Util);
        randomizeRotations = new BooleanSetting("Randomize", true, () -> true);
        randomizeValue = new NumberSetting("Value", 0.5f, 0.5f, 2.0f, 0.1f, () -> randomizeRotations.getCurrentValue());
        autoDropTrash = new BooleanSetting("Auto Trash", "Автоматически выбрасывает хлам(булыга, камень, и т.д)", true, () -> true);
        inventoryFullLeave = new BooleanSetting("Inv Full Leave", "Когда инвентарь полный, выходит с сервера", false, () -> true);
        noBack = new BooleanSetting("No Back", "Не возвращается за выпавшим предметом", true, () -> true);
        this.addSettings(randomizeRotations, randomizeValue, autoDropTrash, noBack, inventoryFullLeave);
    }


    public void onEnable() {
        ChatUtils.addChatMessage("Пожалуйста, введите #help, для полного списка команд");
        ChatUtils.addChatMessage("Пожалуйста, введите #stop, для остановки поставленной задачи Baritone");
    }
}
