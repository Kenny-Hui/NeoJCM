package com.lx862.jcm.mod.block.entity;

import com.lx862.jcm.mod.registry.BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class ButterflyLightBlockEntity extends JCMBlockEntity {
    private int startBlinkingSeconds = 10;
    public ButterflyLightBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntities.BUTTERFLY_LIGHT.get(), blockPos, blockState);
    }

    @Override
    public void readCompoundTag(CompoundTag compoundTag) {
        super.readCompoundTag(compoundTag);
        this.startBlinkingSeconds = compoundTag.getInt("seconds_to_blink");
    }

    @Override
    public void writeCompoundTag(CompoundTag compoundTag) {
        super.writeCompoundTag(compoundTag);
        compoundTag.putInt("seconds_to_blink", startBlinkingSeconds);
    }

    public void setData(int secondsToBlink) {
        this.startBlinkingSeconds = secondsToBlink;
        this.setChanged();
        this.syncData();
    }

    public int getStartBlinkingSeconds() {
        return startBlinkingSeconds;
    }
}
