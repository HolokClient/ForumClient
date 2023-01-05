package digger.cmept.forum.module.impl.Util;

import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;

public class ChatHistory
        extends Module {
    public ChatHistory() {
        super("ChatHistory", "Показывает историю чата, даже если вы перезашли на сервер", ModuleCategory.Util);
    }
}
