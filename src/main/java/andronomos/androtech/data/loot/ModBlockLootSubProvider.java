package andronomos.androtech.data.loot;

import andronomos.androtech.registry.BlockRegistry;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;

public class ModBlockLootSubProvider extends BlockLootSubProvider {
	public ModBlockLootSubProvider() {
		super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
	}

	@Override
	protected void generate() {
		BlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(b -> {
			this.dropSelf(b);
		});
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return BlockRegistry.BLOCKS.getEntries()
				.stream()
				.flatMap(RegistryObject::stream)
				::iterator;
	}
}
