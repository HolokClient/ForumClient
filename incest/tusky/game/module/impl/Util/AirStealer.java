package incest.tusky.game.module.impl.Util;

import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventPreMotion;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.math.TimerHelper;
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

        if (mc.player.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest) mc.player.openContainer;
            for (int index = 0; index < container.inventorySlots.size(); ++index) {
                if (container.getLowerChestInventory().getStackInSlot(index).getItem() != Item.getItemById(0) && timer.hasReached((delay))) {
                    AirStealer.mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, mc.player);
                    timer.reset();
                    continue;
                }
                if (autoDisable.getCurrentValue()) {
                    mc.player.closeScreen();
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
