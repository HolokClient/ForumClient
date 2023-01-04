package incest.tusky.game.ui.config;

import com.google.gson.JsonObject;
import incest.tusky.game.tuskevich;
import incest.tusky.game.module.Module;


import java.io.File;

public final class Config implements ConfigUpdater {

    private final String name;
    private final File file;

    public Config(String name) {
        this.name = name;
        this.file = new File(ConfigManager.configDirectory, name + ".json");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
            }
        }
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    @Override
    public JsonObject save() {
        JsonObject jsonObject = new JsonObject();
        JsonObject modulesObject = new JsonObject();
        JsonObject panelObject = new JsonObject();

        for (Module module : tuskevich.instance.featureManager.getAllFeatures()) {
            modulesObject.add(module.getLabel(), module.save());
        }

        jsonObject.add("Features", modulesObject);

        return jsonObject;
    }

    @Override
    public void load(JsonObject object) {
        if (object.has("Features")) {
            JsonObject modulesObject = object.getAsJsonObject("Features");
            for (Module module : tuskevich.instance.featureManager.getAllFeatures()) {
                module.setEnabled(false);
                module.load(modulesObject.getAsJsonObject(module.getLabel()));
            }
        }
    }
}