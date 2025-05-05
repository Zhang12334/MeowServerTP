package com.meowstp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.configuration.ConfigurationSection;
import java.util.stream.Collectors;

/**
 * 服务器管理命令类
 * 处理服务器添加、删除、列表等管理命令
 */
public class ServerCommand implements CommandExecutor, TabCompleter {
    private final LanguageManager languageManager;  // 语言管理器
    private final DatabaseManager databaseManager;  // 数据库管理器
    private final MeowServerTP plugin;  // 插件主类实例

    /**
     * 构造函数
     * @param languageManager 语言管理器实例
     * @param databaseManager 数据库管理器实例
     * @param plugin 插件主类实例
     */
    public ServerCommand(LanguageManager languageManager, DatabaseManager databaseManager, MeowServerTP plugin) {
        this.languageManager = languageManager;
        this.databaseManager = databaseManager;
        this.plugin = plugin;
    }

    /**
     * 命令执行方法
     * @param sender 命令发送者
     * @param command 命令对象
     * @param label 命令标签
     * @param args 命令参数
     * @return 命令是否执行成功
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 重新加载配置
        MeowServerTP.getInstance().reloadConfig();
        
        // 检查命令参数
        if (args.length < 1) {
            sender.sendMessage(formatMessage(getMessage("usage")));
            return true;
        }

        String subCommand = args[0].toLowerCase();
        switch (subCommand) {
            case "add":
                handleAddCommand(sender, args);
                return true;
            case "remove":
                handleRemoveCommand(sender, args);
                return true;
            case "list":
                handleListCommand(sender);
                return true;
            default:
                sender.sendMessage(formatMessage(getMessage("usage.server")));
                return true;
        }
    }

    /**
     * 获取命令用法说明
     * @param sender 命令发送者
     * @return 用法说明文本
     */
    private String getUsage(CommandSender sender) {
        String baseUsage = getMessage("usage");
        if (sender.isOp()) {
            return formatMessage(baseUsage + " /mstp add <id> <name> [permission] | /mstp remove <id> | /mstp list");
        } else {
            return formatMessage(baseUsage + " /mstp list");
        }
    }

    /**
     * 处理添加服务器命令
     * @param sender 命令发送者
     * @param args 命令参数
     */
    private void handleAddCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("meowservertp.command.server.add")) {
            sender.sendMessage(formatMessage(getMessage("nopermission")));
            return;
        }

        if (args.length < 2) {
            sender.sendMessage(formatMessage(getMessage("usage.server.add")));
            return;
        }

        String serverId = args[1];
        String serverName = args.length > 2 ? args[2] : serverId;
        String permission = args.length > 3 ? args[3] : "meowservertp.server." + serverId;

        if (databaseManager.isEnabled()) {
            if (databaseManager.addServer(serverId, serverName, permission)) {
                sender.sendMessage(formatMessage(getMessage("serverAdded"), serverId));
            } else {
                sender.sendMessage(formatMessage(getMessage("serverAddFailed")));
            }
        } else {
            // 本地配置存储
            ConfigurationSection servers = plugin.getConfig().getConfigurationSection("Server-list");
            if (servers == null) {
                servers = plugin.getConfig().createSection("Server-list");
            }
            servers.set(serverId + ".Name", serverName);
            servers.set(serverId + ".Permission", permission);
            plugin.saveConfig();
            sender.sendMessage(formatMessage(getMessage("serverAdded"), serverId));
        }
    }

    /**
     * 处理删除服务器命令
     * @param sender 命令发送者
     * @param args 命令参数
     */
    private void handleRemoveCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("meowservertp.command.server.remove")) {
            sender.sendMessage(formatMessage(getMessage("nopermission")));
            return;
        }

        if (args.length < 2) {
            sender.sendMessage(formatMessage(getMessage("usage.server.remove")));
            return;
        }

        String serverId = args[1];

        if (databaseManager.isEnabled()) {
            if (databaseManager.removeServer(serverId)) {
                sender.sendMessage(formatMessage(getMessage("serverRemoved"), serverId));
            } else {
                sender.sendMessage(formatMessage(getMessage("serverRemoveFailed")));
            }
        } else {
            // 本地配置存储
            ConfigurationSection servers = plugin.getConfig().getConfigurationSection("Server-list");
            if (servers != null && servers.contains(serverId)) {
                servers.set(serverId, null);
                plugin.saveConfig();
                sender.sendMessage(formatMessage(getMessage("serverRemoved"), serverId));
            } else {
                sender.sendMessage(formatMessage(getMessage("serverNotExist"), serverId));
            }
        }
    }

    /**
     * 处理服务器列表命令
     * @param sender 命令发送者
     */
    private void handleListCommand(CommandSender sender) {
        // 重新加载配置
        plugin.reloadConfig();
        
        if (!sender.hasPermission("meowservertp.command.server.list")) {
            sender.sendMessage(formatMessage(getMessage("nopermission")));
            return;
        }

        List<ServerInfo> servers;
        if (plugin.getConfig().getBoolean("Database.enabled", false)) {
            servers = databaseManager.getServers();
        } else {
            // 本地配置存储
            servers = new ArrayList<>();
            ConfigurationSection serversSection = plugin.getConfig().getConfigurationSection("Server-list");
            if (serversSection != null) {
                for (String serverId : serversSection.getKeys(false)) {
                    String serverName = serversSection.getString(serverId + ".Name", serverId);
                    String permission = serversSection.getString(serverId + ".Permission", "meowservertp.server." + serverId);
                    servers.add(new ServerInfo(serverId, serverName, permission));
                }
            }
        }

        if (servers.isEmpty()) {
            sender.sendMessage(formatMessage(getMessage("noServers")));
            return;
        }

        sender.sendMessage(formatMessage(getMessage("serverList")));
        for (ServerInfo server : servers) {
            sender.sendMessage(formatMessage(getMessage("serverInfo"), server.getId(), server.getName(), server.getPermission()));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            List<String> completions = new ArrayList<>();
            
            if ("add".startsWith(input)) completions.add("add");
            if ("remove".startsWith(input)) completions.add("remove");
            if ("list".startsWith(input)) completions.add("list");
            
            return completions;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            String input = args[1].toLowerCase();
            List<String> completions = new ArrayList<>();
            
            if (plugin.getConfig().getBoolean("Database.enabled", false)) {
                completions.addAll(databaseManager.getServers().stream()
                    .map(ServerInfo::getId)
                    .filter(id -> id.toLowerCase().startsWith(input))
                    .collect(Collectors.toList()));
            } else {
                completions.addAll(plugin.getConfig().getConfigurationSection("Server-list").getKeys(false).stream()
                    .filter(key -> key.toLowerCase().startsWith(input))
                    .collect(Collectors.toList()));
            }
            
            return completions;
        }
        return new ArrayList<>();
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