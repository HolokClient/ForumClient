package incest.tusky.game.utils.otherutils.pon;

import incest.tusky.game.event.events.Event;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.math.BlockPos;

public class EventRenderChunk
        implements Event {
    public net.minecraft.util.math.BlockPos BlockPos;
    public net.minecraft.client.renderer.chunk.RenderChunk RenderChunk;

    public EventRenderChunk(RenderChunk renderChunk, BlockPos blockPos) {
        this.BlockPos = blockPos;
        this.RenderChunk = renderChunk;
    }
}

