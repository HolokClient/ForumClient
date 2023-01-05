package digger.cmept.forum.module.impl.Combat;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.EventUpdate;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.NumberSetting;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class FastBow extends Module {

    private final NumberSetting bowDelay;

    public FastBow() {
        super("Bow Spam", "Быстрая стрельба из лука", ModuleCategory.Combat);
        this.bowDelay = new NumberSetting("Bow Delay", "Регулировка скорости стрельбы из лука", 1.0f, 1.0f, 10, 0.5f, () -> true);
        addSettings(bowDelay);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && mc.player.isBowing() && mc.player.getItemInUseMaxCount() >= bowDelay.getCurrentValue()) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.player.stopActiveHand();
        }
    }
}
