package incest.tusky.game.ui.altmanager.alt;

import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class AltManager extends GuiScreen {

    public static Alt lastAlt;
    public static ArrayList<Alt> registry = new ArrayList<>();

    public ArrayList<Alt> getRegistry() {
        return registry;
    }

    public void setLastAlt(Alt alt) {
        lastAlt = alt;
    }
}
