package net.mcreator.mystryxmod.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Containers;
import net.minecraft.world.Container;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.mystryxmod.init.MystryxmodModForgeContent;
import net.mcreator.mystryxmod.block.entity.ForgeFurnaceBlockEntity;

import org.jetbrains.annotations.Nullable;

/**
 * Forge Furnace : un four noir qui transforme un Mystryx Ingot en Burned Mystryx Ingot.
 * Le carburant (slot du bas) est de la netherite : 2 lingots de netherite = 1 Mystryx Ingot brule.
 */
public class ForgeFurnaceBlock extends BaseEntityBlock {
	public static final MapCodec<ForgeFurnaceBlock> CODEC = simpleCodec(ForgeFurnaceBlock::new);
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public static final net.minecraft.world.level.block.state.properties.EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

	public ForgeFurnaceBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(3.5f, 6f).requiresCorrectToolForDrops().lightLevel(state -> state.getValue(LIT) ? 13 : 0));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, LIT);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ForgeFurnaceBlockEntity(pos, state);
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return level.isClientSide() ? null : createTickerHelper(type, MystryxmodModForgeContent.FORGE_FURNACE_ENTITY, ForgeFurnaceBlockEntity::serverTick);
	}

	@Override
	public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!level.isClientSide()) {
			MenuProvider menu = state.getMenuProvider(level, pos);
			if (menu != null)
				player.openMenu(menu);
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean moved) {
		BlockEntity be = level.getBlockEntity(pos);
		if (be instanceof Container container) {
			Containers.dropContents(level, pos, container);
			level.updateNeighbourForOutputSignal(pos, this);
		}
		super.affectNeighborsAfterRemoval(state, level, pos, moved);
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (state.getValue(LIT)) {
			double x = pos.getX() + 0.5;
			double y = pos.getY();
			double z = pos.getZ() + 0.5;
			Direction facing = state.getValue(FACING);
			double offset = random.nextDouble() * 0.6 - 0.3;
			double dx = facing.getAxis() == Direction.Axis.X ? facing.getStepX() * 0.52 : offset;
			double dy = random.nextDouble() * 6.0 / 16.0;
			double dz = facing.getAxis() == Direction.Axis.Z ? facing.getStepZ() * 0.52 : offset;
			level.addParticle(ParticleTypes.SMOKE, x + dx, y + dy, z + dz, 0.0, 0.0, 0.0);
			level.addParticle(ParticleTypes.FLAME, x + dx, y + dy, z + dz, 0.0, 0.0, 0.0);
		}
	}
}
