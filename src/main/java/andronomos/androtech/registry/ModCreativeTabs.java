package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AndroTech.MODID);

	public static final String BASETABNAME = "blockpalette_tab";
	public static final String GLASSTABNAME = "blockpalette_glass_tab";

	public static final RegistryObject<CreativeModeTab> BLOCKPALETTE_TAB = CREATIVE_MODE_TABS.register(BASETABNAME, () -> CreativeModeTab.builder()
			.title(Component.translatable("creativetab." + BASETABNAME))
			.icon(Blocks.BAMBOO.asItem()::getDefaultInstance)
			.displayItems((parameters, output) -> ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(b -> {
				switch (b.getClass().getSimpleName()) {
					case "GlassBlock", "StainedGlassBlock", "StainedGlassPaneBlock":
						return;
				}
				output.accept(b);
			})).build());

	public static final RegistryObject<CreativeModeTab> BLOCKPALETTE_GLASS_TAB = CREATIVE_MODE_TABS.register(GLASSTABNAME, () -> CreativeModeTab.builder()
			.title(Component.translatable("creativetab." + GLASSTABNAME))
			.icon(Blocks.BAMBOO.asItem()::getDefaultInstance)
			.displayItems((parameters, output) -> ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(b -> {
				switch (b.getClass().getSimpleName()) {
					case "GlassBlock", "StainedGlassBlock", "StainedGlassPaneBlock" -> output.accept(b);
				}
			})).build());
}
