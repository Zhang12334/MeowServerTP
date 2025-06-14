package com.meowstp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.bukkit.configuration.ConfigurationSection;

public class TeleportCommand implements CommandExecutor, TabCompleter {
    private final LanguageManager languageManager;
    private final DatabaseManager databaseManager;

    public TeleportCommand(LanguageManager languageManager) {
        this.languageManager = languageManager;
        this.databaseManager = MeowServerTP.getInstance().getDatabaseManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 重新加载配置
        MeowServerTP.getInstance().reloadConfig();
        
        // 检查命令参数
        if (args.length < 1) {
            sender.sendMessage(formatMessage(getMessage("usage")));
            return true;
        }

        // 检查是否为玩家
        if (!(sender instanceof Player)) {
            sender.sendMessage(formatMessage(getMessage("notplayer")));
            return true;
        }

        Player player = (Player) sender;
        String server = args[0];
        
        // 检查服务器是否存在
        if (!isServerExists(server)) {
            sender.sendMessage(formatMessage(getMessage("serverNotExist"), server));
            return true;
        }
        
        // 检查权限
        String permission = getServerPermission(server);
        if (permission != null && !permission.equalsIgnoreCase("false") && !player.hasPermission(permission)) {
            sender.sendMessage(formatMessage(getMessage("nopermission")));
            return true;
        }
        
        // 执行传送
        String serverName = getServerName(server);
        sender.sendMessage(formatMessage(getMessage("teleporting"), serverName));
        executeTeleport(player, server, sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            
            // 获取所有可用的服务器列表
            List<String> availableServers = getAvailableServers();
            
            // 根据权限过滤服务器
            for (String server : availableServers) {
                String permission = getServerPermission(server);
                if (permission == null || permission.equalsIgnoreCase("false") || sender.hasPermission(permission)) {
                    if (server.toLowerCase().startsWith(input)) {
                        completions.add(server);
                    }
                }
            }
            
            // 按字母顺序排序
            completions.sort(String.CASE_INSENSITIVE_ORDER);
        }
        
        return completions;
    }

    private List<String> getAvailableServers() {
        List<String> servers = new ArrayList<>();
        if (MeowServerTP.getInstance().getConfig().getBoolean("Database.priority", true) && 
            MeowServerTP.getInstance().getConfig().getBoolean("Database.enabled", false)) {
            servers = databaseManager.getServers().stream()
                .map(ServerInfo::getId)
                .collect(Collectors.toList());
        } else {
            ConfigurationSection serversSection = MeowServerTP.getInstance().getConfig().getConfigurationSection("Server-list");
            if (serversSection != null) {
                servers.addAll(serversSection.getKeys(false));
            }
        }
        return servers;
    }

    private boolean isServerExists(String server) {
        if (MeowServerTP.getInstance().getConfig().getBoolean("Database.enabled", false)) {
            return databaseManager.getServers().stream()
                .anyMatch(serverInfo -> serverInfo.getId().equals(server));
        }
        return MeowServerTP.getInstance().getConfig().contains("Server-list." + server);
    }

    private String getServerPermission(String server) {
        if (MeowServerTP.getInstance().getConfig().getBoolean("Database.enabled", false)) {
            return databaseManager.getServers().stream()
                .filter(serverInfo -> serverInfo.getId().equals(server))
                .map(ServerInfo::getPermission)
                .findFirst()
                .orElse(null);
        }
        return MeowServerTP.getInstance().getConfig().getString("Server-list." + server + ".Permission");
    }

    private String getServerName(String server) {
        if (MeowServerTP.getInstance().getConfig().getBoolean("Database.enabled", false)) {
            return databaseManager.getServers().stream()
                .filter(serverInfo -> serverInfo.getId().equals(server))
                .map(ServerInfo::getName)
                .findFirst()
                .orElse(server);
        }
        String serverNameConfig = MeowServerTP.getInstance().getConfig().getString("Server-list." + server + ".Name");
        return serverNameConfig != null ? serverNameConfig : server;
    }

    private void executeTeleport(Player player, String server, CommandSender sender) {
        TeleportUtils.teleport(player, server, languageManager);
    }

    private String getMessage(String key) {
        return languageManager.getMessage(key);
    }

    private String formatMessage(String message, Object... args) {
        // 替换前缀
        if (message.contains("{prefix}")) {
            message = message.replace("{prefix}", MeowServerTP.getInstance().getConfig().getString("Prefix", ""));
        }
        // 替换服务器名称
        if (message.contains("{server}") && args != null && args.length > 0) {
            message = message.replace("{server}", args[0].toString());
        }
        // 使用 String.format 替换其他占位符
        if (args != null && args.length > 0) {
            message = String.format(message, args);
        }
        // 替换颜色代码
        message = message.replace("&", "§");
        return message;
    }
}