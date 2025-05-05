package com.meowstp;

/**
 * 服务器信息类
 */
public class ServerInfo {
    private final String id;
    private final String name;
    private final String permission;

    /**
     * 构造函数
     * @param id 服务器ID
     * @param name 服务器名称
     * @param permission 权限节点
     */
    public ServerInfo(String id, String name, String permission) {
        this.id = id;
        this.name = name;
        this.permission = permission;
    }

    /**
     * 获取服务器ID
     * @return 服务器ID
     */
    public String getId() {
        return id;
    }

    /**
     * 获取服务器名称
     * @return 服务器名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取权限节点
     * @return 权限节点
     */
    public String getPermission() {
        return permission;
    }
} 