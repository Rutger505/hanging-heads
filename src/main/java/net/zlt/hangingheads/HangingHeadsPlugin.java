package net.zlt.hangingheads;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class HangingHeadsPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        HangingHeads heads = new HangingHeads(this);
        for (World world : getServer().getWorlds()) {
            heads.track(world.getEntities());
        }
        getServer().getPluginManager().registerEvents(new HangingHeadListener(heads), this);
    }
}
