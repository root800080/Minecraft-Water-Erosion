package com._13rac1.erosion.common;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TestMaybeDecayUnder extends TestTasksCommon {

    @BeforeAll
    static void beforeAll() throws Exception {
        // Bootstrap the whole world.
        FakeWorldVersion.init();
    }

    @Test
    @SuppressWarnings("null")
    void testMaybeDecayUnder() {
        final Level world = mock(Level.class, levelSettings);
        final BlockPos pos = new BlockPos(0, 0, 0);
        final RandomSource rand = RandomSource.create(); // unused, in tests but required

        final BlockState water = Blocks.WATER.defaultBlockState();
        final BlockState dirt = Blocks.DIRT.defaultBlockState();
        final BlockState clay = Blocks.DIRT.defaultBlockState();
        final BlockState cobblestone = Blocks.COBBLESTONE.defaultBlockState();
        final BlockState sand = Blocks.SAND.defaultBlockState();

        Vec3 degree0 = new Vec3(0, 0, 0);
        Vec3 degree45 = new Vec3(0.707, 0, 0.707);

        // No decay under source blocks
        Integer level = FluidLevel.SOURCE;
        Assertions.assertFalse(tasks.maybeDecayUnder(world, pos, rand, level, degree0));

        // No decay of water on top of water.
        level = FluidLevel.FLOW1;
        when(world.getBlockState(pos.below())).thenReturn(water);
        Assertions.assertFalse(tasks.maybeDecayUnder(world, pos, rand, level, degree0));

        // No decay if block will become air.
        level = FluidLevel.FLOW1;
        when(world.getBlockState(pos.below())).thenReturn(clay);
        Assertions.assertFalse(tasks.maybeDecayUnder(world, pos, rand, level, degree0));

        // No decay for 45 degree angles.
        when(world.getBlockState(pos.below())).thenReturn(cobblestone);
        Assertions.assertFalse(tasks.maybeDecayUnder(world, pos, rand, FluidLevel.FLOW1, degree45));

        // Decay dirt
        when(world.getBlockState(pos.below())).thenReturn(dirt);
        Vec3 south = new Vec3(0.0, 0, 1);
        when(world.getBlockState(pos.below().south())).thenReturn(sand);
        Assertions.assertTrue(tasks.maybeDecayUnder(world, pos, rand, FluidLevel.FLOW1, south));
    }
}
