package net.zlt.hangingheads;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.zlt.hangingheads.block.ModBlocks;
import org.slf4j.Logger;

public class HangingHeads implements ModInitializer {
    public static final String ID = "hanging_heads";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        ModBlocks.register();
    }
}
