package com.meowstp;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {
    private final LanguageManager languageManager;

    public TeleportCommand(LanguageManager languageManager) {
        this.languageManager = languageManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 检查命令参数
        if (args.length < 2) {
            sender.sendMessage(getMessage("usage") + " " + "/mstp tp <server> | /mstp send <player> <server>");
            return true;
        }

        String subCommand = args[0].toLowerCase();
        if (subCommand.equals("tp")) {
            // 主动传送
            if (!(sender instanceof Player)) {
                // 非玩家，拦截
                sender.sendMessage(getMessage("notplayer"));
                return true;
            }
            Player player = (Player) sender;
            String server = args[1];
            // 检查服务器是否存在
            if (!MeowServerTP.getInstance().getConfig().contains("Server-list." + server)) {
                sender.sendMessage(getMessage("serverNotExist").replace("{server}", server));
                return true;
            }
            // 检查权限
            String permission = MeowServerTP.getInstance().getConfig().getString("Server-list." + server + ".Permission");
            if (permission != null && !permission.equalsIgnoreCase("false") && !player.hasPermission(permission)) {
                sender.sendMessage(getMessage("nopermission"));
                return true;
            }
            // 日志
            String serverNameConfig = MeowServerTP.getInstance().getConfig().getString("Server-list." + server + ".Name");
            String servername = serverNameConfig != null ? serverNameConfig : server;
            sender.sendMessage(String.format(getMessage("teleporting"), servername));
            // 执行传送
            executeTeleport(player, server, sender);
        } else if (subCommand.equals("send")) {
            // 检查权限
            if (!sender.hasPermission("meowservertp.command.send")) {
                // 没有传送他人的权限
                sender.sendMessage(getMessage("nopermission"));
                return true;
            }
            // 获取玩家
            Player targetPlayer = Bukkit.getPlayer(args[1]);
            if (targetPlayer == null) {
                sender.sendMessage(getMessage("playerOffline").replace("{player}", args[1]));
                return true;
            }
            // 获取并检查服务器参数
            String server = args[2];
            if (!MeowServerTP.getInstance().getConfig().contains("Server-list." + server)) {
                sender.sendMessage(String.format(getMessage("serverNotExist"), server));
                return true;
            }
            // 日志
            // 获取别名
            String serverNameConfig = MeowServerTP.getInstance().getConfig().getString("Server-list." + server + ".Name");
            String servername = serverNameConfig != null ? serverNameConfig : server;
            sender.sendMessage(Color.GREEN + String.format(getMessage("teleportingOther"), targetPlayer.getName(), servername));
            // 执行传送           
            executeTeleport(targetPlayer, server, sender);
        } else {
            sender.sendMessage(getMessage("usage") + " " + "/mstp tp <server> | /mstp send <player> <server>");
        }
        return true;
    }

    private void executeTeleport(Player player, String server, CommandSender sender) {
        TeleportUtils.teleport(player, server, languageManager);
    }

    private String getMessage(String key) {
        return languageManager.getMessage(key)
                .replace("{prefix}", MeowServerTP.getInstance().getConfig().getString("Prefix", ""))
                .replace("&", "§")
                .replace("/n", "\n");
    }
}