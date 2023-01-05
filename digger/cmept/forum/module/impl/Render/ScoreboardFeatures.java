package digger.cmept.forum.module.impl.Render;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.render.EventRenderScoreboard;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.BooleanSetting;
import digger.cmept.forum.ui.settings.impl.NumberSetting;
import net.minecraft.client.renderer.GlStateManager;

public class ScoreboardFeatures extends Module {

    public static BooleanSetting noScore;
    public NumberSetting x;
    public NumberSetting y;

    public ScoreboardFeatures() {
        super("Scoreboard", "Позволяет изменять скорбоард", ModuleCategory.Render);
        noScore = new BooleanSetting("No Scoreboard", true, () -> true);
        x = new NumberSetting("Scoreboard X", 0, -100, 100, 1, () -> !noScore.getCurrentValue());
        y = new NumberSetting("Scoreboard Y", 0, -100, 100, 1, () -> !noScore.getCurrentValue());
        addSettings(noScore);
    }

    @EventTarget
    public void onRenderScoreboard(EventRenderScoreboard event) {
        if (event.isPre()) {
            GlStateManager.translate(-x.getCurrentValue(), y.getCurrentValue(), 12);
        } else {
            GlStateManager.translate(x.getCurrentValue(), -y.getCurrentValue(), 12);
        }
    }
}
