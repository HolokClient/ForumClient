package digger.cmept.forum.module.impl.Player;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.packet.EventReceivePacket;
import digger.cmept.forum.friend.Friend;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.forum;
import digger.cmept.forum.ui.settings.impl.BooleanSetting;
import digger.cmept.forum.ui.settings.impl.NumberSetting;
import digger.cmept.forum.utils.Helper;
import digger.cmept.forum.utils.math.TimerHelper;
import net.minecraft.network.play.server.SPacketChat;

@SuppressWarnings("all")

public class AutoTPAccept
        extends Module {
    private final BooleanSetting friendsOnly;
    private final NumberSetting delay;
    private final TimerHelper timerHelper = new TimerHelper();

    public AutoTPAccept() {
        super("AutoTPAccept", "Автоматически принимает телепорт от друзей", ModuleCategory.Player);
        friendsOnly = new BooleanSetting("Friends Only", false, () -> true);
        delay = new NumberSetting("Delay", 300.0f, 0.0f, 1000.0f, 100.0f, () -> true);
        addSettings(this.friendsOnly, delay);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket e) {
        SPacketChat message = (SPacketChat) e.getPacket();
        if (message.getChatComponent().getFormattedText().contains("телепортироваться")) {
            if (this.friendsOnly.getCurrentValue()) {
                for (Friend friend : forum.instance.friendManager.getFriends()) {
                    if (!message.getChatComponent().getFormattedText().contains(friend.getName()) || !this.timerHelper.hasReached(this.delay.getCurrentValue()))
                        continue;
                    Helper.mc.player.sendChatMessage("/tpaccept");
                    timerHelper.reset();
                }
            } else if (this.timerHelper.hasReached(this.delay.getCurrentValue())) {
                Helper.mc.player.sendChatMessage("/tpaccept");
                timerHelper.reset();
            }
        }
    }
}
