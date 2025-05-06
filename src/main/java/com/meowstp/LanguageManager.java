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
        Set<String> validLanguages = new HashSet<>(Arrays.asList("zh_hans", "zh_hant", "en_us", "ja_jp", "de_de"));

        // 读取配置中的语言设置，默认为zh_hans
        String language = config.getString("language", "zh_hans");

        // 如果读取的语言不在有效列表中，则设为默认值
        if (!validLanguages.contains(language.toLowerCase())) {
            language = "zh_hans";
        }
        messages.clear();

        if ("zh_hans".equalsIgnoreCase(language)) {
            // 简体中文消息
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
            messages.put("usage", "&e请输入 &6/mstp help &e查看帮助信息");
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
            messages.put("serverModified", "{prefix}成功修改服务器 {server}");
            messages.put("serverModifyFailed", "{prefix}修改服务器失败!");
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
                "&e/mstp server modify <服务器配置名> <新显示名> [新权限节点] &r- 修改服务器信息\n" +
                "&e==========================================");
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
            messages.put("usage", "&e請輸入 &6/mstp help &e查看幫助信息");
            messages.put("notplayer", "只有玩家才能執行此命令!");
            messages.put("teleporting", "{prefix}正在傳送至 %s");
            messages.put("teleportingOther", "{prefix}正在將 &e%s&r 傳送至 &b%s&r");
            messages.put("teleportingAll", "{prefix}正在將 %d 名玩家傳送至 %s");
            messages.put("playerNotFound", "{prefix}玩家 %s 不存在或不在線!");
            messages.put("playerOffline", "{prefix}玩家 %s 不在線!");
            messages.put("noPlayers", "{prefix}沒有找到任何可傳送的玩家!");
            messages.put("serverNotExist", "{prefix}伺服器 %s 不存在!");
            messages.put("teleportingByOther", "{prefix}正在被傳送至 %s");
            messages.put("serverAdded", "{prefix}成功添加伺服器 {server}");
            messages.put("serverAddFailed", "{prefix}添加伺服器失敗!");
            messages.put("serverRemoved", "{prefix}成功刪除伺服器 {server}");
            messages.put("serverRemoveFailed", "{prefix}刪除伺服器失敗!");
            messages.put("serverModified", "{prefix}成功修改伺服器 {server}");
            messages.put("serverModifyFailed", "{prefix}修改伺服器失敗!");
            messages.put("noServers", "{prefix}沒有找到任何伺服器!");
            messages.put("serverList", "{prefix}伺服器列表:");
            messages.put("serverInfo", "  - %s (名稱: %s, 權限: %s)");
            messages.put("databaseDriverError", "{prefix}無法加載JDBC驅動");
            messages.put("databaseConnectionError", "{prefix}無法連接到數據庫");
            messages.put("databaseTableCreationError", "{prefix}創建數據庫表失敗");
            messages.put("databaseDisconnectionError", "{prefix}關閉數據庫連接失敗");
            messages.put("databaseGetServersError", "{prefix}獲取伺服器列表失敗");
            messages.put("databaseAddServerError", "{prefix}添加伺服器失敗");
            messages.put("databaseUpdateServerError", "{prefix}更新伺服器失敗");
            messages.put("databaseDeleteServerError", "{prefix}刪除伺服器失敗");
            messages.put("help", "&e========== &6MeowServerTP &e幫助信息 ==========\n" +
                "&e/mstp help &r- 顯示此幫助信息\n" +
                "&e/mstp tp <伺服器配置名> &r- 傳送到指定伺服器\n" +
                "&e/mstp send <玩家名/@all> <伺服器配置名> &r- 將玩家傳送到指定伺服器\n" +
                "&e/mstp server list &r- 查看所有可用伺服器\n" +
                "&e/mstp server add <伺服器配置名> [伺服器顯示名] [權限節點] &r- 添加新伺服器\n" +
                "&e/mstp server remove <伺服器配置名> &r- 刪除伺服器\n" +
                "&e/mstp server modify <伺服器配置名> <新顯示名> [新權限節點] &r- 修改伺服器信息\n" +
                "&e==========================================");
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
            messages.put("usage", "&eType &6/mstp help &efor help information");
            messages.put("notplayer", "Only players can execute this command!");
            messages.put("teleporting", "{prefix}Teleporting to %s");
            messages.put("teleportingOther", "{prefix}Teleporting &e%s&r to &b%s&r");
            messages.put("teleportingAll", "{prefix}Teleporting %d players to %s");
            messages.put("playerNotFound", "{prefix}Player %s does not exist or is not online!");
            messages.put("playerOffline", "{prefix}Player %s is not online!");
            messages.put("noPlayers", "{prefix}No players found to teleport!");
            messages.put("serverNotExist", "{prefix}Server %s does not exist!");
            messages.put("teleportingByOther", "{prefix}Being teleported to %s");
            messages.put("serverAdded", "{prefix}Successfully added server {server}");
            messages.put("serverAddFailed", "{prefix}Failed to add server!");
            messages.put("serverRemoved", "{prefix}Successfully removed server {server}");
            messages.put("serverRemoveFailed", "{prefix}Failed to remove server!");
            messages.put("serverModified", "{prefix}Successfully modified server {server}");
            messages.put("serverModifyFailed", "{prefix}Failed to modify server!");
            messages.put("noServers", "{prefix}No servers found!");
            messages.put("serverList", "{prefix}Server list:");
            messages.put("serverInfo", "  - %s (Name: %s, Permission: %s)");
            messages.put("databaseDriverError", "{prefix}Failed to load JDBC driver");
            messages.put("databaseConnectionError", "{prefix}Failed to connect to database");
            messages.put("databaseTableCreationError", "{prefix}Failed to create database table");
            messages.put("databaseDisconnectionError", "{prefix}Failed to close database connection");
            messages.put("databaseGetServersError", "{prefix}Failed to get server list");
            messages.put("databaseAddServerError", "{prefix}Failed to add server to database");
            messages.put("databaseUpdateServerError", "{prefix}Failed to update server in database");
            messages.put("databaseDeleteServerError", "{prefix}Failed to delete server from database");
            messages.put("help", "&e========== &6MeowServerTP &eHelp Information ==========\n" +
                "&e/mstp help &r- Show this help information\n" +
                "&e/mstp tp <server-config-name> &r- Teleport to specified server\n" +
                "&e/mstp send <player-name/@all> <server-config-name> &r- Teleport player to specified server\n" +
                "&e/mstp server list &r- View all available servers\n" +
                "&e/mstp server add <server-config-name> [server-display-name] [permission-node] &r- Add new server\n" +
                "&e/mstp server remove <server-config-name> &r- Remove server\n" +
                "&e/mstp server modify <server-config-name> <new-display-name> [new-permission-node] &r- Modify server info\n" +
                "&e==========================================");
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
            messages.put("usage", "&eヘルプ情報を見るには &6/mstp help &eと入力してください");
            messages.put("notplayer", "このコマンドはプレイヤーのみが実行できます!");
            messages.put("teleporting", "{prefix}%s にテレポート中");
            messages.put("teleportingOther", "{prefix}&e%s&r を &b%s&r にテレポート中");
            messages.put("teleportingAll", "{prefix}%d 人のプレイヤーを %s にテレポート中");
            messages.put("playerNotFound", "{prefix}プレイヤー %s は存在しないかオンラインではありません!");
            messages.put("playerOffline", "{prefix}プレイヤー %s はオンラインではありません!");
            messages.put("noPlayers", "{prefix}テレポート可能なプレイヤーが見つかりません!");
            messages.put("serverNotExist", "{prefix}サーバー %s は存在しません!");
            messages.put("teleportingByOther", "{prefix}%s にテレポート中");
            messages.put("serverAdded", "{prefix}サーバー {server} を追加しました");
            messages.put("serverAddFailed", "{prefix}サーバーの追加に失敗しました!");
            messages.put("serverRemoved", "{prefix}サーバー {server} を削除しました");
            messages.put("serverRemoveFailed", "{prefix}サーバーの削除に失敗しました!");
            messages.put("serverModified", "{prefix}サーバー {server} を修正しました");
            messages.put("serverModifyFailed", "{prefix}サーバーの修正に失敗しました!");
            messages.put("noServers", "{prefix}サーバーが見つかりません!");
            messages.put("serverList", "{prefix}サーバー一覧:");
            messages.put("serverInfo", "  - %s (名前: %s, 権限: %s)");
            messages.put("databaseDriverError", "{prefix}JDBCドライバーの読み込みに失敗しました");
            messages.put("databaseConnectionError", "{prefix}データベースへの接続に失敗しました");
            messages.put("databaseTableCreationError", "{prefix}データベーステーブルの作成に失敗しました");
            messages.put("databaseDisconnectionError", "{prefix}データベース接続の切断に失敗しました");
            messages.put("databaseGetServersError", "{prefix}サーバー一覧の取得に失敗しました");
            messages.put("databaseAddServerError", "{prefix}データベースへのサーバー追加に失敗しました");
            messages.put("databaseUpdateServerError", "{prefix}データベース内のサーバー更新に失敗しました");
            messages.put("databaseDeleteServerError", "{prefix}データベースからのサーバー削除に失敗しました");
            messages.put("help", "&e========== &6MeowServerTP &eヘルプ情報 ==========\n" +
                "&e/mstp help &r- このヘルプ情報を表示\n" +
                "&e/mstp tp <サーバー設定名> &r- 指定サーバーにテレポート\n" +
                "&e/mstp send <プレイヤー名/@all> <サーバー設定名> &r- プレイヤーを指定サーバーにテレポート\n" +
                "&e/mstp server list &r- 利用可能なサーバー一覧を表示\n" +
                "&e/mstp server add <サーバー設定名> [サーバー表示名] [権限ノード] &r- 新サーバーを追加\n" +
                "&e/mstp server remove <サーバー設定名> &r- サーバーを削除\n" +
                "&e/mstp server modify <サーバー設定名> <新表示名> [新権限ノード] &r- サーバー情報を修正\n" +
                "&e==========================================");
        } else if ("de_de".equalsIgnoreCase(language)) {
            // 德语消息
            messages.put("TranslationContributors", "Aktuelle Sprache: Deutsch (Beitragende: Zhang1233 & TongYi-Lingma LLM)");
            messages.put("CanNotFoundMeowLibs", "MeowLibs nicht gefunden, bitte installiere die Abhängigkeit MeowLibs!");
            messages.put("startup", "MeowServerTP wurde geladen!");
            messages.put("shutdown", "MeowServerTP wurde deaktiviert!");
            messages.put("nowusingversion", "Aktuell verwendete Version:");
            messages.put("checkingupdate", "Suche nach Updates...");
            messages.put("checkfailed", "Update-Prüfung fehlgeschlagen, bitte überprüfe deine Netzwerkverbindung!");
            messages.put("updateavailable", "Eine neue Version ist verfügbar:");
            messages.put("updatemessage", "Update-Inhalt:");
            messages.put("updateurl", "Update herunterladen unter:");
            messages.put("oldversionmaycauseproblem", "Alte Versionen können Probleme verursachen!");
            messages.put("nowusinglatestversion", "Du verwendest die neueste Version!");
            messages.put("reloaded", "Konfigurationsdatei wurde neu geladen!");
            messages.put("nopermission", "Du hast keine Berechtigung, diesen Befehl auszuführen!");
            messages.put("usage", "&eGib &6/mstp help &eein, um Hilfeinformationen zu sehen");
            messages.put("notplayer", "Nur Spieler können diesen Befehl ausführen!");
            messages.put("teleporting", "{prefix}Teleportiere zu %s");
            messages.put("teleportingOther", "{prefix}Teleportiere &e%s&r zu &b%s&r");
            messages.put("teleportingAll", "{prefix}Teleportiere %d Spieler zu %s");
            messages.put("playerNotFound", "{prefix}Spieler %s existiert nicht oder ist nicht online!");
            messages.put("playerOffline", "{prefix}Spieler %s ist nicht online!");
            messages.put("noPlayers", "{prefix}Keine Spieler zum Teleportieren gefunden!");
            messages.put("serverNotExist", "{prefix}Server %s existiert nicht!");
            messages.put("teleportingByOther", "{prefix}Werde zu %s teleportiert");
            messages.put("serverAdded", "{prefix}Server {server} erfolgreich hinzugefügt");
            messages.put("serverAddFailed", "{prefix}Server konnte nicht hinzugefügt werden!");
            messages.put("serverRemoved", "{prefix}Server {server} erfolgreich entfernt");
            messages.put("serverRemoveFailed", "{prefix}Server konnte nicht entfernt werden!");
            messages.put("serverModified", "{prefix}Server {server} erfolgreich modifiziert");
            messages.put("serverModifyFailed", "{prefix}Server konnte nicht modifiziert werden!");
            messages.put("noServers", "{prefix}Keine Server gefunden!");
            messages.put("serverList", "{prefix}Serverliste:");
            messages.put("serverInfo", "  - %s (Name: %s, Berechtigung: %s)");
            messages.put("databaseDriverError", "{prefix}JDBC-Treiber konnte nicht geladen werden");
            messages.put("databaseConnectionError", "{prefix}Verbindung zur Datenbank konnte nicht hergestellt werden");
            messages.put("databaseTableCreationError", "{prefix}Datenbanktabelle konnte nicht erstellt werden");
            messages.put("databaseDisconnectionError", "{prefix}Datenbankverbindung konnte nicht geschlossen werden");
            messages.put("databaseGetServersError", "{prefix}Serverliste konnte nicht abgerufen werden");
            messages.put("databaseAddServerError", "{prefix}Server konnte nicht zur Datenbank hinzugefügt werden");
            messages.put("databaseUpdateServerError", "{prefix}Server konnte nicht in der Datenbank aktualisiert werden");
            messages.put("databaseDeleteServerError", "{prefix}Server konnte nicht aus der Datenbank gelöscht werden");
            messages.put("help", "&e========== &6MeowServerTP &eHilfeinformationen ==========\n" +
                "&e/mstp help &r- Zeige diese Hilfeinformationen\n" +
                "&e/mstp tp <Server-Konfigurationsname> &r- Teleportiere zum angegebenen Server\n" +
                "&e/mstp send <Spielername/@all> <Server-Konfigurationsname> &r- Teleportiere Spieler zum angegebenen Server\n" +
                "&e/mstp server list &r- Zeige alle verfügbaren Server\n" +
                "&e/mstp server add <Server-Konfigurationsname> [Server-Anzeigename] [Berechtigungsknoten] &r- Füge neuen Server hinzu\n" +
                "&e/mstp server remove <Server-Konfigurationsname> &r- Entferne Server\n" +
                "&e/mstp server modify <Server-Konfigurationsname> <neuer-Anzeigename> [neuer-Berechtigungsknoten] &r- Modifiziere Server-Informationen\n" +
                "&e==========================================");
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
