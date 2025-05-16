package com.meowstp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

/**
 * 主命令处理类
 * 负责处理所有 mstp 命令并分发到对应的处理器
 */
public class MainCommand implements CommandExecutor, TabCompleter {
    private final TeleportCommand teleportCommand;
    private final ServerCommand serverCommand;
    private final SendCommand sendCommand;
    private final LanguageManager languageManager;

    /**
     * 构造函数
     * @param languageManager 语言管理器
     * @param databaseManager 数据库管理器
     * @param plugin 插件主类实例
     */
    public MainCommand(LanguageManager languageManager, DatabaseManager databaseManager, MeowServerTP plugin) {
        this.languageManager = languageManager;
        this.teleportCommand = new TeleportCommand(languageManager);
        this.serverCommand = new ServerCommand(languageManager, databaseManager, plugin);
        this.sendCommand = new SendCommand(languageManager, databaseManager, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(formatMessage(getMessage("usage")));
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            // 检查是否允许普通玩家查看帮助信息
            boolean showHelpToNormalPlayers = MeowServerTP.getInstance().getConfig().getBoolean("show-help-to-normal-players", false);
            if (!showHelpToNormalPlayers && !sender.hasPermission("meowservertp.command.help")) {
                sender.sendMessage(formatMessage(getMessage("nopermission")));
                return true;
            }
            sender.sendMessage(formatMessage(getMessage("help")));
            return true;
        }

        String subCommand = args[0].toLowerCase();
        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, args.length - 1);

        switch (subCommand) {
            case "tp":
                return teleportCommand.onCommand(sender, command, label, subArgs);
            case "send":
                return sendCommand.onCommand(sender, command, label, subArgs);
            case "server":
                return serverCommand.onCommand(sender, command, label, subArgs);
            default:
                sender.sendMessage(formatMessage(getMessage("usage")));
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            // 第一个参数：子命令
            String input = args[0].toLowerCase();
            
            // 根据权限过滤可用的子命令
            if (sender.hasPermission("meowservertp.command.use")) {
                if ("tp".startsWith(input)) completions.add("tp");
                if ("send".startsWith(input) && sender.hasPermission("meowservertp.command.send")) completions.add("send");
                if ("server".startsWith(input) && sender.hasPermission("meowservertp.command.server.list")) completions.add("server");
            }
            
            // 按字母顺序排序
            completions.sort(String.CASE_INSENSITIVE_ORDER);
        } else if (args.length >= 2) {
            String subCommand = args[0].toLowerCase();
            String[] subArgs = new String[args.length - 1];
            System.arraycopy(args, 1, subArgs, 0, args.length - 1);

            // 根据子命令分发到对应的处理器
            switch (subCommand) {
                case "tp":
                    if (sender.hasPermission("meowservertp.command.use")) {
                        return teleportCommand.onTabComplete(sender, command, alias, subArgs);
                    }
                    break;
                case "send":
                    if (sender.hasPermission("meowservertp.command.send")) {
                        return sendCommand.onTabComplete(sender, command, alias, subArgs);
                    }
                    break;
                case "server":
                    if (sender.hasPermission("meowservertp.command.server.list")) {
                        return serverCommand.onTabComplete(sender, command, alias, subArgs);
                    }
                    break;
            }
        }
        
        return completions;
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