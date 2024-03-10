package me.ugunaii.synchros.util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraftforge.fml.ModList;

public class ModHandler {

    public static void printInstalledMods() {
        Map<String, String> modVersions = ModList.get().getMods().stream()
                .collect(Collectors.toMap(mod -> mod.getModId(), mod -> mod.getVersion().toString()));

        System.out.println("Installed Mods:");
        for (Map.Entry<String, String> entry : modVersions.entrySet()) {
            String modId = entry.getKey();
            String version = entry.getValue();
            System.out.println(modId + " - " + version);
        }
    }

    public static String getInstalledMods() {
        Map<String, String> modVersions = ModList.get().getMods().stream()
                .collect(Collectors.toMap(mod -> mod.getModId(), mod -> mod.getVersion().toString()));

        StringBuilder concatenated = new StringBuilder();
        String delimiter = "";

        for (Map.Entry<String, String> entry : modVersions.entrySet()) {
            String modName = entry.getKey(); // Use modId as the name
            String version = entry.getValue();

            // Concatenate mod name and version with a delimiter
            concatenated.append(delimiter).append(modName).append("#").append(version);
            delimiter = "%%%"; // Set delimiter for the next iteration

        }

        return concatenated.toString();
    }

    public static String getProcessedString() {
        String raw = getInstalledMods();
        String[] list = raw.split("%%%");
        String processed = String.join(", ", list);
        processed = processed.replace("[", "")
                .replace("]", "")
                .replace("#", " v");

        return processed;
    }

}
