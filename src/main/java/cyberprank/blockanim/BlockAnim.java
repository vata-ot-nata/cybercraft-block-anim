package cyberprank.blockanim;

import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import org.bukkit.Location;


import java.io.File;
import java.io.FileOutputStream;

public final class BlockAnim extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        File config = new File(getDataFolder() + File.separator + "config.yml");
        if(!config.exists()) {
            getConfig().options().copyDefaults(true);
        }

        try {
            ConfigurationSection configSection = getConfig().getConfigurationSection("anim");

            for (String anim : configSection.getKeys(false)) {

                new BukkitRunnable() {
                    int i = 0;

                    @Override
                    public void run() {
                        World world = getServer().getWorld("world");

                        com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);

                        int distance = configSection.getInt(anim + ".long");

                        String[] pos1 = configSection.getString(anim + ".pos1").split(" ");
                        String[] pos2 = configSection.getString(anim + ".pos2").split(" ");
                        String[] pos3 = configSection.getString(anim + ".pos3").split(" ");

                        BlockVector3 min = BlockVector3.at(
                                Integer.parseInt(pos1[0]) + (i * distance), // there was -2 for some reason
                                Integer.parseInt(pos1[1]),
                                Integer.parseInt(pos1[2]));
                        BlockVector3 max = BlockVector3.at(
                                Integer.parseInt(pos2[0]) + (i * distance), // there was +1 for some reason
                                Integer.parseInt(pos2[1]),
                                Integer.parseInt(pos2[2]));
                        BlockVector3 to = BlockVector3.at(
                                Integer.parseInt(pos3[0]),
                                Integer.parseInt(pos3[1]),
                                Integer.parseInt(pos3[2]));




                        CuboidRegion region = new CuboidRegion(min, max);
                        BlockArrayClipboard clipboard = new BlockArrayClipboard(region);

                        ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(
                                weWorld, region, clipboard, region.getMinimumPoint()
                        );
                        try {
                            Operations.complete(forwardExtentCopy);
                        } catch (WorldEditException e) {
                            e.printStackTrace();
                        }

                        try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
                            Operation operation = new ClipboardHolder(clipboard)
                                    .createPaste(editSession)
                                    .to(to)
                                    .build();
                            try {
                                Operations.complete(operation);
                            } catch (WorldEditException e) {
                                e.printStackTrace();
                            }
                        }


                    /*
                    CuboidRegion region = new CuboidRegion(min, max);
                    EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld, -1);
                    ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(
                            weWorld, region, min, editSession, to
                    );

                     try {
                        Operations.complete(forwardExtentCopy);
                    } catch (Exception ignored) {}

                     */

                        i++;
                        if (i >= configSection.getInt(anim + ".frames")) {
                            i = 0;
                        }
                    }
                }.runTaskTimer(this, 0, configSection.getInt(anim + ".delay"));
            }
        } catch (Exception ignored) {}

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            try {
                if (args.length == 13) {
                    if (command.getName().equals("animcreate")) {
                        this.getConfig().set("anim." + args[0] + ".name", args[0]);
                        this.getConfig().set("anim." + args[0] + ".frames", Integer.parseInt(args[1]));
                        this.getConfig().set("anim." + args[0] + ".delay", Integer.parseInt(args[2]));
                        this.getConfig().set("anim." + args[0] + ".pos1", args[3] + " " + args[4] + " " + args[5]);
                        this.getConfig().set("anim." + args[0] + ".pos2", args[6] + " " + args[7] + " " + args[8]);
                        this.getConfig().set("anim." + args[0] + ".pos3", args[9] + " " + args[10] + " " + args[11]);
                        this.getConfig().set("anim." + args[0] + ".long", Integer.parseInt(args[12]));

                        sender.sendMessage("Анимация " + args[0] + " создана");

                        return true;
                    }
                } else if (args.length == 1) {
                    switch (command.getName()) {
                        case ("animdelete"):
                            this.getConfig().set("anim." + args[0], null);
                            sender.sendMessage("Анимация " + args[0] + " удалена");
                            return true;
                        case ("addframe"):
                            int frames1 = this.getConfig().getInt("anim." + args[0] + ".frames");
                            sender.sendMessage("-1 кадр у анимации " + args[0]);
                            this.getConfig().set("anim." + args[0] + ".frames", frames1 + 1);
                            return true;
                        case ("removeframe"):
                            int frames2 = this.getConfig().getInt("anim." + args[0] + ".frames");
                            sender.sendMessage("+1 кадр у анимации " + args[0]);
                            this.getConfig().set("anim." + args[0] + ".frames", frames2 - 1);
                            return true;
                    }
                } else if (args.length == 2) {
                    if (command.getName().equals("setdelay")) {
                        this.getConfig().set("anim." + args[0] + ".frames", Integer.parseInt(args[1]));
                        sender.sendMessage("Анимации " + args[0] + " установлена задержка " + args[1]);
                        return true;
                    }
                } else if (command.getName().equals("animreload")) {
                    sender.sendMessage("Перезагрузка плагина...");
                    this.getPluginLoader().disablePlugin(this);
                    this.getPluginLoader().enablePlugin(this);
                }
            } catch (Exception ignored) {}
        }

        return super.onCommand(sender, command, label, args);
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }
}
