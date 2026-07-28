package net.zlt.hangingheads;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.zlt.hangingheads.block.ModBlocks;
import org.slf4j.Logger;

@Mod(HangingHeads.ID)
public class HangingHeads {
    public static final String ID = "hanging_heads";
    public static final Logger LOGGER = LogUtils.getLogger();

    public HangingHeads(IEventBus modEventBus) {
        ModBlocks.register(modEventBus);
    }
}
