package com.meowstp;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.bukkit.configuration.ConfigurationSection;

/**
 * 传送他人命令处理类
 */
public class SendCommand implements CommandExecutor, TabCompleter {
    private final LanguageManager languageManager;
    private final DatabaseManager databaseManager;
    private final MeowServerTP plugin;

    public SendCommand(LanguageManager languageManager, DatabaseManager databaseManager, MeowServerTP plugin) {
        this.languageManager = languageManager;
        this.databaseManager = databaseManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 重新加载配置
        MeowServerTP.getInstance().reloadConfig();
        
        // 检查命令参数
        if (args.length < 2) {
            sender.sendMessage(formatMessage(getMessage("usage")));
            return true;
        }

        String target = args[0];
        String server = args[1];

        // 检查服务器是否存在
        if (!isServerExists(server)) {
            sender.sendMessage(formatMessage(getMessage("serverNotExist"), server));
            return true;
        }

        // 检查权限
        String permission = getServerPermission(server);
        if (permission != null && !permission.equalsIgnoreCase("false") && !sender.hasPermission(permission)) {
            sender.sendMessage(formatMessage(getMessage("nopermission")));
            return true;
        }

        // 处理 @all 参数
        if (target.equalsIgnoreCase("@all")) {
            if (!sender.hasPermission("meowservertp.command.send.all")) {
                sender.sendMessage(formatMessage(getMessage("nopermission")));
                return true;
            }

            // 获取所有在线玩家（不包括命令执行者）
            List<Player> players = Bukkit.getOnlinePlayers().stream()
                .filter(p -> !p.equals(sender))
                .collect(Collectors.toList());

            if (players.isEmpty()) {
                sender.sendMessage(formatMessage(getMessage("noPlayers")));
                return true;
            }

            // 执行传送
            String serverName = getServerName(server);
            for (Player player : players) {
                executeTeleport(player, server, serverName, sender);
            }
            sender.sendMessage(formatMessage(getMessage("teleportingAll"), players.size(), serverName));
            return true;
        }

        // 处理单个玩家传送
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            // 检查玩家是否存在但不在线
            if (Bukkit.getOfflinePlayer(target).hasPlayedBefore()) {
                sender.sendMessage(formatMessage(getMessage("playerOffline"), target));
            } else {
                sender.sendMessage(formatMessage(getMessage("playerNotFound"), target));
            }
            return true;
        }

        // 执行传送
        String serverName = getServerName(server);
        sender.sendMessage(formatMessage(getMessage("teleportingOther"), player.getName(), serverName));
        executeTeleport(player, server, serverName, sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            // 第一个参数：玩家名或@all
            String input = args[0].toLowerCase();
            
            // 添加@all选项（如果有权限）
            if (sender.hasPermission("meowservertp.command.send.all") && "@all".startsWith(input)) {
                completions.add("@all");
            }
            
            // 添加在线玩家列表
            completions.addAll(Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(name -> name.toLowerCase().startsWith(input))
                .collect(Collectors.toList()));
            
            // 按字母顺序排序
            completions.sort(String.CASE_INSENSITIVE_ORDER);
        } else if (args.length == 2) {
            // 第二个参数：服务器名
            String input = args[1].toLowerCase();
            
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
        if (plugin.getConfig().getBoolean("Database.priority", true) && 
            plugin.getConfig().getBoolean("Database.enabled", false)) {
            servers = databaseManager.getServers().stream()
                .map(ServerInfo::getId)
                .collect(Collectors.toList());
        } else {
            ConfigurationSection serversSection = plugin.getConfig().getConfigurationSection("Server-list");
            if (serversSection != null) {
                servers.addAll(serversSection.getKeys(false));
            }
        }
        return servers;
    }

    private boolean isServerExists(String server) {
        if (plugin.getConfig().getBoolean("Database.enabled", false)) {
            return databaseManager.getServers().stream()
                .anyMatch(serverInfo -> serverInfo.getId().equals(server));
        }
        return plugin.getConfig().contains("Server-list." + server);
    }

    private String getServerPermission(String server) {
        if (plugin.getConfig().getBoolean("Database.enabled", false)) {
            return databaseManager.getServers().stream()
                .filter(serverInfo -> serverInfo.getId().equals(server))
                .map(ServerInfo::getPermission)
                .findFirst()
                .orElse(null);
        }
        return plugin.getConfig().getString("Server-list." + server + ".Permission");
    }

    private String getServerName(String server) {
        if (plugin.getConfig().getBoolean("Database.enabled", false)) {
            return databaseManager.getServers().stream()
                .filter(serverInfo -> serverInfo.getId().equals(server))
                .map(ServerInfo::getName)
                .findFirst()
                .orElse(server);
        }
        String serverNameConfig = plugin.getConfig().getString("Server-list." + server + ".Name");
        return serverNameConfig != null ? serverNameConfig : server;
    }

    private void executeTeleport(Player player, String server, String serverName, CommandSender sender) {
        player.sendMessage(formatMessage(getMessage("teleporting"), serverName));
        TeleportUtils.teleport(player, server, languageManager);
    }

    private String getMessage(String key) {
        return languageManager.getMessage(key);
    }

    private String formatMessage(String message, Object... args) {
        // 替换前缀
        if (message.contains("{prefix}")) {
            message = message.replace("{prefix}", plugin.getConfig().getString("Prefix", ""));
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