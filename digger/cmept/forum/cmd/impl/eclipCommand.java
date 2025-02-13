package digger.cmept.forum.cmd.impl;

import digger.cmept.forum.cmd.CommandAbstract;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;

public class eclipCommand
        extends CommandAbstract {
    Minecraft mc = Minecraft.getMinecraft();

    public eclipCommand() {
        super("eclip", "Eclip | Evclip | Ehclip", "�.Eclip | Ehclip | Evclip  + | - | <value> | bedrock  | up  | down", "Eclip", "Evclip", "Ehclip");
    }

    @Override
    public void execute(String ... args2) {
    }

    public static void startElytra() {
        Minecraft mc = Minecraft.getMinecraft();
        for (Slot slot : mc.player.inventoryContainer.inventorySlots) {
            if (slot.getStack().getItem() != Items.ELYTRA) continue;
            mc.playerController.windowClick(0, slot.slotNumber, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, mc.player);
            mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, slot.slotNumber, 0, ClickType.PICKUP, mc.player);
            mc.playerController.updateController();
        }
    }
}
