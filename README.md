# MeowServerTP

[English Version](https://github.com/Zhang12334/MeowServerTP/blob/main/README_EN.md)

**MeowServerTP** 是一个支持 Bukkit/Spigot 的 BungeeCord 和 Velocity 跨服传送插件

## 语言支持

| 语言 | 代码 | 状态 |
|------|------|------|
| 简体中文 | zh_hans | ✅ 完整 |
| 繁体中文 | zh_hant | ✅ 完整 |
| 英语 | en_us | ✅ 完整 |
| 日语 | ja_jp | ✅ 完整 |
| 德语 | de_de | ✅ 完整 |

> **注意**: 语言文件会根据最新版本自动更新

---

## 依赖

- **MeowLibs** (必需)

---

## 配置

> **注意**: 配置文件位于 plugins/MeowServerTP/config.yml
> **注意**: 数据库配置优先级高于本地配置

```yaml
# 数据库配置
Database:
  # 是否启用数据库存储
  enabled: false
  # 数据库类型 (目前仅支持MySQL)
  type: MySQL
  # 数据库连接信息
  host: localhost
  port: 3306
  database: meowservertp
  username: root
  password: password

# 语言（zh_hans / zh_hant / en_us / ja_jp / de_de）
language: zh_hans

# 插件消息前缀
Prefix: '&6[MeowServerTP] &2'

# 服务器列表
Server-list:
  lobby:
    Name: 大厅Lobby
    Permission: false
  survival:
    Name: 生存服Survival
    Permission: meowstp.survival
```

---

## 命令

- `/mstp help` - 显示帮助信息 (需要权限: `meowservertp.command.help`)
- `/mstp tp <服务器配置名>` - 传送到指定服务器
- `/mstp send <玩家名/@all> <服务器配置名>` - 将玩家传送到指定服务器
- `/mstp server list` - 查看所有可用服务器
- `/mstp server add <服务器配置名> [服务器显示名] [权限节点]` - 添加新服务器
- `/mstp server remove <服务器配置名>` - 删除服务器
- `/mstp server modify <服务器配置名> <新显示名> [新权限节点]` - 修改服务器信息

## 权限

- `meowservertp.command.use` - 允许使用基本命令 (默认: true)
- `meowservertp.command.help` - 允许查看帮助信息 (默认: op)
- `meowservertp.command.send` - 允许使用传送他人的命令 (默认: op)
- `meowservertp.command.send.all` - 允许使用传送所有玩家的命令 (默认: op)
- `meowservertp.command.server.add` - 允许添加服务器到数据库 (默认: op)
- `meowservertp.command.server.remove` - 允许从数据库删除服务器 (默认: op)
- `meowservertp.command.server.list` - 允许查看服务器列表 (默认: op)
- `meowservertp.command.server.modify` - 允许修改服务器信息 (默认: op)

---

## 服务器兼容性

> **注意**: 本插件兼容 1.12.2 及以上版本的 Spigot 服务端

---

## 贡献者

| 名称 | 贡献 |
|------|------|
| Zhang1233 | 开发者 |
| TongYi-Lingma LLM | 语言翻译 |

---

## 许可证

本项目采用 [LICENSE.md](https://github.com/Zhang12334/zhcn_opensource_sharing_license?tab=License-1-ov-file) (中文版) 许可证

您可以：
  - 自由使用此插件
  - 在遵守协议的情况下进行二次开发，但需要注明原作者