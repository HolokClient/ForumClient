package digger.cmept.forum.module.impl.Util;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.EventPreMotion;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.BooleanSetting;
import digger.cmept.forum.ui.settings.impl.NumberSetting;
import digger.cmept.forum.utils.Helper;
import digger.cmept.forum.utils.math.TimerHelper;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.*;

public class AirStealer extends Module {

    private final NumberSetting delay;
    public static BooleanSetting autoDisable;
    public TimerHelper timer = new TimerHelper();

    public AirStealer() {
        super("AirStealer", "��������� ������ ������ �������", ModuleCategory.Util);
        delay = new NumberSetting("Stealer Speed", 10, 0, 100, 1, () -> true);
        autoDisable = new BooleanSetting("AutoDisable", "������������� ��������� �������", true, () -> true);
        addSettings(autoDisable);
    }
    @EventTarget
    public void onUpdate(EventPreMotion event) {
        float delay = 0;

        if (Helper.mc.player.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest) Helper.mc.player.openContainer;
            for (int index = 0; index < container.inventorySlots.size(); ++index) {
                if (container.getLowerChestInventory().getStackInSlot(index).getItem() != Item.getItemById(0) && timer.hasReached((delay))) {
                    AirStealer.mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, Helper.mc.player);
                    timer.reset();
                    continue;
                }
                if (autoDisable.getCurrentValue()) {
                    Helper.mc.player.closeScreen();
                    this.onDisable();
                }
            }
        }
    }

    public boolean isWhiteItem(ItemStack itemStack) {
        return (itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemEnderPearl || itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemFood || itemStack.getItem() instanceof ItemPotion || itemStack.getItem() instanceof ItemBlock || itemStack.getItem() instanceof ItemArrow || itemStack.getItem() instanceof ItemCompass);
    }

    private boolean isEmpty(Container container) {
        for (int index = 0; index < container.inventorySlots.size(); index++) {
            if (isWhiteItem(container.getSlot(index).getStack()))
                return false;
        }
        return true;
    }

}
