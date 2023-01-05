/*
 * Decompiled with CFR 0.150.
 */
package baritone.events.events.baritoneOnly;

import net.minecraft.util.math.BlockPos;
import digger.cmept.forum.event.events.Event;
import digger.cmept.forum.event.events.callables.EventCancellable;
public class EventBarBlockUse
extends EventCancellable
implements Event {
    public BlockPos position;

    public EventBarBlockUse(BlockPos pos) {
        this.position = pos;
    }
}

