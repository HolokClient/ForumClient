/*
 * Decompiled with CFR 0.150.
 */
package baritone.events.events.baritoneOnly;

import net.minecraft.network.play.server.SPacketChunkData;
import digger.cmept.forum.event.events.Event;
import digger.cmept.forum.event.events.callables.EventCancellable;
public class EventBarChunkPre
extends EventCancellable
implements Event {
    public SPacketChunkData packet;

    public EventBarChunkPre(SPacketChunkData packetIn) {
        this.packet = packetIn;
    }
}

