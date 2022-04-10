package andronomos.androtech.item;

import andronomos.androtech.AndroTech;
import com.google.common.collect.Iterables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class DebugStickItem extends Item {
	public DebugStickItem(Properties properties) {
		super(properties);
	}

	public InteractionResult useOn(UseOnContext context) {
		if(context.getLevel().isClientSide) return InteractionResult.PASS;

		BlockEntity clickedEntity = context.getLevel().getBlockEntity(context.getClickedPos());
		BlockState state = context.getLevel().getBlockState(context.getClickedPos());
		Block block = state.getBlock();

		AndroTech.LOGGER.info("DebugStickItem#useOn | clickedEntity {}", clickedEntity);
		AndroTech.LOGGER.info("DebugStickItem#useOn | state {}", state);
		AndroTech.LOGGER.info("DebugStickItem#useOn | block {}", block);
		AndroTech.LOGGER.info("DebugStickItem#useOn | state.is(Blocks.NETHER_WART) {}", state.is(Blocks.NETHER_WART));
		AndroTech.LOGGER.info("DebugStickItem#useOn | state == Blocks.NETHER_WART.defaultBlockState() {}", state == Blocks.NETHER_WART.defaultBlockState());
		AndroTech.LOGGER.info("DebugStickItem#useOn | block.getDrops {}", block.getDrops(state, (ServerLevel) context.getLevel(), context.getClickedPos(), null));

		if (block instanceof IPlantable) {
			AndroTech.LOGGER.info("DebugStickItem#useOn | block instanceof IPlantable {}", block instanceof IPlantable);
			PlantType type = ((IPlantable)block).getPlantType(context.getLevel(), context.getClickedPos());

			AndroTech.LOGGER.info("DebugStickItem#useOn | getPlantType {}", type);
		}

		if(state == Blocks.NETHER_WART.defaultBlockState()) {
			AndroTech.LOGGER.info("DebugStickItem#useOn | state == state.getProperties() {}", state.getProperties());
		}

		if(block instanceof NetherWartBlock) {
			NetherWartBlock netherWart = (NetherWartBlock)block;
			AndroTech.LOGGER.info("DebugStickItem#useOn | netherWart.AGE {}", netherWart.AGE);

			int maxAge = Iterables.getLast(netherWart.AGE.getPossibleValues());
			int age = state.getValue(netherWart.AGE);

			AndroTech.LOGGER.info("DebugStickItem#useOn | maxAge {}", maxAge);
			AndroTech.LOGGER.info("DebugStickItem#useOn | age {}", age);
			AndroTech.LOGGER.info("DebugStickItem#useOn | age == maxAge {}", age == maxAge);
		}

		if(block instanceof CropBlock) {
			CropBlock crop = (CropBlock)block;
			AndroTech.LOGGER.info("DebugStickItem#useOn | crop.getAgeProperty {}", crop.getAgeProperty());
			AndroTech.LOGGER.info("DebugStickItem#useOn | crop.getMaxAge {}", crop.getMaxAge());
		}

		return InteractionResult.SUCCESS;
	}
}
