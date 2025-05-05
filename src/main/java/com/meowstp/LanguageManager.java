package com.meowstp;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LanguageManager {
    private Map<String, String> messages = new HashMap<>();
    private FileConfiguration config;

    public LanguageManager(FileConfiguration config) {
        this.config = config;
        loadLanguage();
    }

    public void loadLanguage() {
        // 有效的语言列表
        Set<String> validLanguages = new HashSet<>(Arrays.asList("zh_hans", "zh_hant", "en_us", "ja_jp"));

        // 读取配置中的语言设置，默认为zh_hans
        String language = config.getString("language", "zh_hans");

        // 如果读取的语言不在有效列表中，则设为默认值
        if (!validLanguages.contains(language.toLowerCase())) {
            language = "zh_hans";
        }
        messages.clear();

        if ("zh_hans".equalsIgnoreCase(language)) {
            // 中文消息
            messages.put("TranslationContributors", "当前语言: 简体中文 (贡献者: Zhang1233)");
            messages.put("CanNotFoundMeowLibs", "未找到 MeowLibs, 请安装前置依赖 MeowLibs!");            
            messages.put("startup", "MeowServerTP 已加载!");
            messages.put("shutdown", "MeowServerTP 已卸载!");
            messages.put("nowusingversion", "当前使用版本:");
            messages.put("checkingupdate", "正在检查更新...");
            messages.put("checkfailed", "检查更新失败，请检查你的网络状况!");
            messages.put("updateavailable", "发现新版本:");
            messages.put("updatemessage", "更新内容如下:");
            messages.put("updateurl", "新版本下载地址:");
            messages.put("oldversionmaycauseproblem", "旧版本可能会导致问题，请尽快更新!");
            messages.put("nowusinglatestversion", "您正在使用最新版本!");
            messages.put("reloaded", "配置文件已重载!");
            messages.put("nopermission", "你没有权限执行此命令!");
            messages.put("usage", "&e用法提示: &r/mstp <&6tp&r|&6send&r|&6server&r> [参数]");
            messages.put("usage.tp", "&e用法提示: &r/mstp tp <&6服务器ID&r> - 传送到指定服务器");
            messages.put("usage.send", "&e用法提示: &r/mstp send <&6玩家名&r|&6@all&r> <&6服务器ID&r> - 将玩家传送到指定服务器");
            messages.put("usage.server", "&e用法提示: &r/mstp server <&6add&r|&6remove&r|&6list&r> [参数]");
            messages.put("usage.server.add", "&e用法提示: &r/mstp server add <&6服务器ID&r> [&6显示名称&r] [&6权限节点&r] - 添加新服务器");
            messages.put("usage.server.remove", "&e用法提示: &r/mstp server remove <&6服务器ID&r> - 删除服务器");
            messages.put("usage.server.list", "&e用法提示: &r/mstp server list - 查看所有可用服务器");
            messages.put("notplayer", "只有玩家才能执行此命令!");
            messages.put("teleporting", "{prefix}正在传送至 %s");
            messages.put("teleportingOther", "{prefix}正在将 &e%s&r 传送至 &b%s&r");
            messages.put("teleportingAll", "{prefix}正在将 %d 名玩家传送至 %s");
            messages.put("playerNotFound", "{prefix}玩家 %s 不存在或不在线!");
            messages.put("playerOffline", "{prefix}玩家 %s 不在线!");
            messages.put("noPlayers", "{prefix}没有找到任何可传送的玩家!");
            messages.put("serverNotExist", "{prefix}服务器 %s 不存在!");
            messages.put("teleportingByOther", "{prefix}正在被传送至 %s");
            messages.put("serverAdded", "{prefix}成功添加服务器 {server}");
            messages.put("serverAddFailed", "{prefix}添加服务器失败!");
            messages.put("serverRemoved", "{prefix}成功删除服务器 {server}");
            messages.put("serverRemoveFailed", "{prefix}删除服务器失败!");
            messages.put("noServers", "{prefix}没有找到任何服务器!");
            messages.put("serverList", "{prefix}服务器列表:");
            messages.put("serverInfo", "  - %s (名称: %s, 权限: %s)");
            messages.put("databaseDriverError", "{prefix}无法加载JDBC驱动");
            messages.put("databaseConnectionError", "{prefix}无法连接到数据库");
            messages.put("databaseTableCreationError", "{prefix}创建数据库表失败");
            messages.put("databaseDisconnectionError", "{prefix}关闭数据库连接失败");
            messages.put("databaseGetServersError", "{prefix}获取服务器列表失败");
            messages.put("databaseAddServerError", "{prefix}添加服务器失败");
            messages.put("databaseUpdateServerError", "{prefix}更新服务器失败");
            messages.put("databaseDeleteServerError", "{prefix}删除服务器失败");
            messages.put("help", "&e========== &6MeowServerTP &e帮助信息 ==========\n" +
                "&e/mstp help &r- 显示此帮助信息\n" +
                "&e/mstp tp <服务器配置名> &r- 传送到指定服务器\n" +
                "&e/mstp send <玩家名/@all> <服务器配置名> &r- 将玩家传送到指定服务器\n" +
                "&e/mstp server list &r- 查看所有可用服务器\n" +
                "&e/mstp server add <服务器配置名> [服务器显示名] [权限节点] &r- 添加新服务器\n" +
                "&e/mstp server remove <服务器配置名> &r- 删除服务器\n" +
                "&e==========================================");
            messages.put("usage", "&e请输入 &6/mstp help &e查看帮助信息");
        } else if ("zh_hant".equalsIgnoreCase(language)) {
            // 繁體中文消息
            messages.put("TranslationContributors", "當前語言: 繁體中文 (貢獻者: Zhang1233 & TongYi-Lingma LLM)");
            messages.put("CanNotFoundMeowLibs", "未找到 MeowLibs, 請安裝前置依賴 MeowLibs!");
            messages.put("startup", "MeowServerTP 已載入!");
            messages.put("shutdown", "MeowServerTP 已卸載!");
            messages.put("nowusingversion", "當前使用版本:");
            messages.put("checkingupdate", "正在檢查更新...");
            messages.put("checkfailed", "檢查更新失敗，請檢查你的網絡狀態!");
            messages.put("updateavailable", "發現新版本:");
            messages.put("updatemessage", "更新內容如下:");
            messages.put("updateurl", "新版本下載地址:");
            messages.put("oldversionmaycauseproblem", "舊版本可能會導致問題，請尽快更新!");
            messages.put("nowusinglatestversion", "您正在使用最新版本!");
            messages.put("reloaded", "配置文件已重載!");
            messages.put("nopermission", "你沒有權限執行此命令!");
            messages.put("help", "&e========== &6MeowServerTP &e幫助信息 ==========\n" +
                "&e/mstp help &r- 顯示此幫助信息\n" +
                "&e/mstp tp <伺服器ID> &r- 傳送到指定伺服器\n" +
                "&e/mstp send <玩家名/@all> <伺服器ID> &r- 將玩家傳送到指定伺服器\n" +
                "&e/mstp server list &r- 查看所有可用伺服器\n" +
                "&e/mstp server add <伺服器ID> [顯示名稱] [權限節點] &r- 添加新伺服器\n" +
                "&e/mstp server remove <伺服器ID> &r- 刪除伺服器\n" +
                "&e==========================================");
            messages.put("usage", "&e請輸入 &6/mstp help &e查看幫助信息");
        } else if ("en_us".equalsIgnoreCase(language)) {
            // English messages
            messages.put("TranslationContributors", "Current Language: English (Contributors: Zhang1233)");
            messages.put("CanNotFoundMeowLibs", "MeowLibs not found, please install the dependency MeowLibs!");
            messages.put("startup", "MeowServerTP has been loaded!");
            messages.put("shutdown", "MeowServerTP has been disabled!");
            messages.put("nowusingversion", "Currently using version:");
            messages.put("checkingupdate", "Checking for updates...");
            messages.put("checkfailed", "Update check failed, please check your network!");
            messages.put("updateavailable", "A new version is available:");
            messages.put("updatemessage", "Update content:");
            messages.put("updateurl", "Download update at:");
            messages.put("oldversionmaycauseproblem", "Old versions may cause problems!");
            messages.put("nowusinglatestversion", "You are using the latest version!");
            messages.put("reloaded", "Configuration file has been reloaded!");
            messages.put("nopermission", "You do not have permission to execute this command!");
            messages.put("help", "&e========== &6MeowServerTP &eHelp Information ==========\n" +
                "&e/mstp help &r- Show this help information\n" +
                "&e/mstp tp <ServerID> &r- Teleport to specified server\n" +
                "&e/mstp send <PlayerName/@all> <ServerID> &r- Teleport player to specified server\n" +
                "&e/mstp server list &r- View all available servers\n" +
                "&e/mstp server add <ServerID> [DisplayName] [Permission] &r- Add new server\n" +
                "&e/mstp server remove <ServerID> &r- Remove server\n" +
                "&e==========================================");
            messages.put("usage", "&eType &6/mstp help &efor help information");
        } else if ("ja_jp".equalsIgnoreCase(language)) {
            // 日本语消息
            messages.put("TranslationContributors", "現在の言語: 日本語 (寄稿者: Zhang1233 & TongYi-Lingma LLM)");
            messages.put("CanNotFoundMeowLibs", "MeowLibsが見つかりません。プレフィックス依存をインストールしてください!");
            messages.put("startup", "MeowServerTPがロードされました!");
            messages.put("shutdown", "MeowServerTPが無効化されました!");
            messages.put("nowusingversion", "現在使用中のバージョン:");
            messages.put("checkingupdate", "更新を確認中...");
            messages.put("checkfailed", "更新チェックに失敗しました。ネットワークを確認してください!");
            messages.put("updateavailable", "新しいバージョンが利用できます:");
            messages.put("updatemessage", "アップデート内容:");
            messages.put("updateurl", "更新をダウンロードするURL:");
            messages.put("oldversionmaycauseproblem", "古いバージョンは問題を引き起こす可能性があります!");
            messages.put("nowusinglatestversion", "現在最新バージョンを使用しています!");
            messages.put("reloaded", "設定ファイルがリロードされました!");
            messages.put("nopermission", "このコマンドの実行に権限がありません!");
            messages.put("help", "&e========== &6MeowServerTP &eヘルプ情報 ==========\n" +
                "&e/mstp help &r- このヘルプ情報を表示\n" +
                "&e/mstp tp <サーバーID> &r- 指定サーバーにテレポート\n" +
                "&e/mstp send <プレイヤー名/@all> <サーバーID> &r- プレイヤーを指定サーバーにテレポート\n" +
                "&e/mstp server list &r- 利用可能なサーバー一覧を表示\n" +
                "&e/mstp server add <サーバーID> [表示名] [権限ノード] &r- 新サーバーを追加\n" +
                "&e/mstp server remove <サーバーID> &r- サーバーを削除\n" +
                "&e==========================================");
            messages.put("usage", "&eヘルプ情報を見るには &6/mstp help &eと入力してください");
        }
    }

    /**
     * 获取语言消息
     * @param key 消息键名
     * @return 对应的语言消息，如果不存在则返回键名
     */
    public String getMessage(String key) {
        return messages.getOrDefault(key, key);
    }
}
