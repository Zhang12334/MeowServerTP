package com.meowstp;

import org.bukkit.entity.Player;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TeleportUtils {
    public static void teleport(Player player, String server, LanguageManager languageManager) {
        if (player == null) return;
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream out = new DataOutputStream(baos)) {
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(MeowServerTP.getInstance(), "BungeeCord", baos.toByteArray());
        } catch (IOException e) {
            MeowServerTP.getInstance().getLogger().severe(String.format(
                languageManager.getMessage("teleportFailed"), 
                player.getName(), server, e.getMessage()
            ));
        }
    }
}