package com.meowstp;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MeowServerTP extends JavaPlugin implements Listener {
    private static MeowServerTP instance;
    private LanguageManager languageManager;
    @Override
    public void onEnable() {
        instance = this;

        // bstats
        int pluginId = 25586;
        Metrics metrics = new Metrics(this, pluginId);

        // 加载配置文件
        saveDefaultConfig();

        // 初始化语言管理器
        languageManager = new LanguageManager(getConfig());
        
        // 检查前置库是否加载
        if (!Bukkit.getPluginManager().isPluginEnabled("MeowLibs")) {
            getLogger().warning(languageManager.getMessage("CanNotFoundMeowLibs"));
            // 禁用插件
            getServer().getPluginManager().disablePlugin(this); 
            return;           
        }

        // ------------------------------------------开始加载插件------------------------------------------

        getLogger().info(languageManager.getMessage("startup"));
        
        // 翻译者
        getLogger().info(languageManager.getMessage("TranslationContributors"));        

        // 注册 BungeeCord 和 Velocity 通道
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "velocity:player_info");

        // 注册命令
        getCommand("mstp").setExecutor(new TeleportCommand(languageManager));

        // 创建 CheckUpdate 实例
        CheckUpdate updateChecker = new CheckUpdate(
            getLogger(), // log记录器
            languageManager, // 语言管理器
            getDescription() // 插件版本信息
        );    

        // 异步执行更新检查
        new BukkitRunnable() {
            @Override
            public void run() {
                updateChecker.checkUpdate();
            }
        }.runTaskAsynchronously(this);        

    }
    @Override
    public void onDisable() {
        getLogger().info(languageManager.getMessage("shutdown"));   
    }
    public static MeowServerTP getInstance() {
        return instance;
    }

}
