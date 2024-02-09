package org.allaymc.server.command.defaults;

import org.allaymc.api.command.SimpleCommand;
import org.allaymc.api.command.tree.CommandTree;
import org.allaymc.api.server.Server;

/**
 * Allay Project 2024/2/9
 *
 * @author daoge_cmd
 */
public class PluginCommand extends SimpleCommand {

    public PluginCommand() {
        // TODO: I18n
        super("plugin", "Plugin command");
        aliases.add("pl");
    }

    @Override
    public void prepareCommandTree(CommandTree tree) {
        tree.getRoot()
                .key("list")
                .exec(context -> {
                    var str = String.join(", ", Server.getInstance()
                            .getPluginManager()
                            .getEnabledPlugins()
                            .keySet());
                    context.addOutput("Enabled plugins: §a" + str);
                    return context.success();
                })
                .root()
                .key("reload")
                .str("pluginName")
                .exec(context -> {
                    var manager = Server.getInstance().getPluginManager();
                    String name = context.getSecondResult();
                    if (!manager.isPluginEnabled(name)) {
                        context.addOutput("§cPlugin " + name + " is not enabled!");
                        return context.failed();
                    }
                    var plugin = manager.getPlugin(name).plugin();
                    if (!plugin.isReloadable()) {
                        context.addOutput("§cPlugin " + name + " is not reloadable!");
                        return context.failed();
                    }
                    plugin.reload();
                    context.addOutput("§aPlugin " + name + " has been reloaded");
                    return context.success();
                })
                .root()
                .key("reloadall")
                .exec(context -> {
                    var manager = Server.getInstance().getPluginManager();
                    for (var plugin : manager.getEnabledPlugins().values()) {
                        if (plugin.plugin().isReloadable()) {
                            plugin.plugin().reload();
                        }
                    }
                    context.addOutput("§aAll reloadable plugins have been reloaded");
                    return context.success();
                });
    }
}