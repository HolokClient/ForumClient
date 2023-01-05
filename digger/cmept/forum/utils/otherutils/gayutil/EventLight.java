package digger.cmept.forum.utils.otherutils.gayutil;

import digger.cmept.forum.event.events.Event;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;

public class EventLight extends EventCancellable implements Event {

    private final EnumSkyBlock enumSkyBlock;
    private final BlockPos pos;

    public EventLight(EnumSkyBlock enumSkyBlock, BlockPos pos) {
        this.enumSkyBlock = enumSkyBlock;
        this.pos = pos;
    }

    public EnumSkyBlock getEnumSkyBlock() {
        return enumSkyBlock;
    }

    public BlockPos getPos() {
        return pos;
    }
}