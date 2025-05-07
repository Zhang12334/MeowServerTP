package com.meowstp;

import org.bukkit.configuration.file.FileConfiguration;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库管理器类
 * 负责处理与MySQL数据库的连接和操作
 */
public class DatabaseManager {
    private Connection connection;  // 数据库连接对象
    private final MeowServerTP plugin;  // 插件主类实例
    private final FileConfiguration config;  // 配置文件
    private final LanguageManager languageManager;  // 语言管理器

    /**
     * 构造函数
     * @param plugin 插件主类实例
     */
    public DatabaseManager(MeowServerTP plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.languageManager = plugin.getLanguageManager();
    }
    private String formatMessage(String message) {
        // 替换前缀
        if (message.contains("{prefix}")) {
            message = message.replace("{prefix}", plugin.getConfig().getString("Prefix", ""));
        }
        return message;
    }
    /**
     * 连接到数据库
     * 从配置文件中读取连接信息并建立连接
     */
    public void connect() {
        if (!config.getBoolean("Database.enabled", false)) {
            return;
        }

        try {
            // 从配置文件中读取数据库连接信息
            String host = config.getString("Database.host", "localhost");
            int port = config.getInt("Database.port", 3306);
            String database = config.getString("Database.database", "meowservertp");
            String username = config.getString("Database.username", "root");
            String password = config.getString("Database.password", "password");

            // 构建数据库连接URL
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database + 
                        "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true" +
                        "&autoReconnect=true";  // 启用自动重连
            connection = DriverManager.getConnection(url, username, password);
            
            // 创建必要的数据库表
            createTables();
        } catch (SQLException e) {
            plugin.getLogger().severe(formatMessage(languageManager.getMessage("databaseConnectionError") + ": " + e.getMessage()));
        }
    }

    /**
     * 创建数据库表
     * 如果表不存在则创建
     */
    private void createTables() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS servers (" +
                    "id VARCHAR(32) PRIMARY KEY," +  // 服务器ID
                    "name VARCHAR(64) NOT NULL," +   // 服务器名称
                    "permission VARCHAR(64)" +       // 权限节点
                    ")");
        } catch (SQLException e) {
            plugin.getLogger().severe(formatMessage(languageManager.getMessage("databaseTableCreationError") + ": " + e.getMessage()));
        }
    }

    /**
     * 断开数据库连接
     */
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                plugin.getLogger().severe(formatMessage(languageManager.getMessage("databaseDisconnectionError") + ": " + e.getMessage()));
            }
        }
    }

    /**
     * 获取所有服务器信息
     * @return 服务器信息列表
     */
    public List<ServerInfo> getServers() {
        List<ServerInfo> servers = new ArrayList<>();
        if (!isEnabled()) {
            return servers;
        }

        try {
            String sql = "SELECT id, name, permission FROM servers";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    String permission = resultSet.getString("permission");
                    servers.add(new ServerInfo(id, name, permission));
                }
            }
        } catch (SQLException e) {
            plugin.getLogger().severe(formatMessage(languageManager.getMessage("databaseGetServersError")));
            e.printStackTrace();
        }
        return servers;
    }

    /**
     * 添加新服务器
     * @param id 服务器ID
     * @param name 服务器名称
     * @param permission 权限节点
     * @return 是否添加成功
     */
    public boolean addServer(String id, String name, String permission) {
        if (!config.getBoolean("Database.enabled", false)) {
            return false;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO servers (id, name, permission) VALUES (?, ?, ?)")) {
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, permission);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            plugin.getLogger().severe(formatMessage(languageManager.getMessage("databaseAddServerError") + ": " + e.getMessage()));
            return false;
        }
    }

    /**
     * 更新服务器信息
     * @param id 服务器ID
     * @param name 服务器名称
     * @param permission 权限节点
     * @return 是否更新成功
     */
    public boolean updateServer(String id, String name, String permission) {
        if (!config.getBoolean("Database.enabled", false)) {
            return false;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE servers SET name = ?, permission = ? WHERE id = ?")) {
            statement.setString(1, name);
            statement.setString(2, permission);
            statement.setString(3, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            plugin.getLogger().severe(formatMessage(languageManager.getMessage("databaseUpdateServerError") + ": " + e.getMessage()));
            return false;
        }
    }

    /**
     * 删除服务器
     * @param id 服务器ID
     * @return 是否删除成功
     */
    public boolean deleteServer(String id) {
        if (!config.getBoolean("Database.enabled", false)) {
            return false;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM servers WHERE id = ?")) {
            statement.setString(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            plugin.getLogger().severe(formatMessage(languageManager.getMessage("databaseDeleteServerError") + ": " + e.getMessage()));
            return false;
        }
    }

    /**
     * 检查数据库是否启用
     * @return 如果数据库已启用返回true，否则返回false
     */
    public boolean isEnabled() {
        return connection != null;
    }

    /**
     * 从数据库删除服务器
     * @param serverId 服务器ID
     * @return 如果删除成功返回true，否则返回false
     */
    public boolean removeServer(String serverId) {
        if (!isEnabled()) {
            return false;
        }

        try {
            String sql = "DELETE FROM servers WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, serverId);
                return statement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            plugin.getLogger().severe(formatMessage(languageManager.getMessage("databaseDeleteServerError")));
            e.printStackTrace();
            return false;
        }
    }
} 