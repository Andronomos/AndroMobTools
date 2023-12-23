package andronomos.androtech.data;

import andronomos.androtech.AndroTech;
import andronomos.androtech.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
	public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, AndroTech.MODID, existingFileHelper);
	}

	@Override
	public String getName() {
		return "AndroTech Tags";
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(b -> {
			String blockType = b.getClass().getSimpleName();
			tag(BlockTags.MINEABLE_WITH_PICKAXE).add(b);
		});
	}
}
