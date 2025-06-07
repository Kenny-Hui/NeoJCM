package com.lx862.jcm.mod.network.block;

import com.lx862.jcm.mod.block.base.JCMBlock;
import com.lx862.jcm.mod.block.entity.PIDSBlockEntity;
import com.lx862.jcm.mod.network.JCMPacketHandlerHelper;
import com.lx862.jcm.mod.network.PacketHandler;
import com.lx862.jcm.mod.util.BlockUtil;
import it.unimi.dsi.fastutil.longs.LongAVLTreeSet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PIDSUpdatePacket extends PacketHandler {
    protected final BlockPos blockPos;
    protected final LongAVLTreeSet filteredPlatforms;
    protected final String[] customMessages;
    protected final boolean[] rowHidden;
    protected final boolean hidePlatformNumber;
    protected final String presetId;

    public PIDSUpdatePacket(FriendlyByteBuf packetBufferReceiver) {
        this.blockPos = BlockPos.of(packetBufferReceiver.readLong());
        int rows = packetBufferReceiver.readInt();
        this.customMessages = new String[rows];
        this.rowHidden = new boolean[rows];
        this.filteredPlatforms = new LongAVLTreeSet();

        JCMPacketHandlerHelper.readArray(packetBufferReceiver, i -> this.customMessages[i] = packetBufferReceiver.readUtf());
        JCMPacketHandlerHelper.readArray(packetBufferReceiver, i -> this.rowHidden[i] = packetBufferReceiver.readBoolean());
        JCMPacketHandlerHelper.readArray(packetBufferReceiver, i -> filteredPlatforms.add(packetBufferReceiver.readLong()));

        this.hidePlatformNumber = packetBufferReceiver.readBoolean();
        this.presetId = packetBufferReceiver.readUtf();
    }

    public PIDSUpdatePacket(BlockPos blockPos, LongAVLTreeSet filteredPlatforms, String[] customMessages, boolean[] rowHidden, boolean hidePlatformNumber, String pidsPreset) {
        this.blockPos = blockPos;
        this.customMessages = customMessages;
        this.rowHidden = rowHidden;
        this.presetId = pidsPreset;
        this.hidePlatformNumber = hidePlatformNumber;
        this.filteredPlatforms = filteredPlatforms;
    }

    @Override
    public void write(FriendlyByteBuf packetBufferSender) {
        packetBufferSender.writeLong(blockPos.asLong());
        packetBufferSender.writeInt(customMessages.length);
        JCMPacketHandlerHelper.writeArray(packetBufferSender, customMessages, i -> packetBufferSender.writeUtf(customMessages[i]));
        JCMPacketHandlerHelper.writeArray(packetBufferSender, rowHidden, i -> packetBufferSender.writeBoolean(rowHidden[i]));
        JCMPacketHandlerHelper.writeArray(packetBufferSender, filteredPlatforms, packetBufferSender::writeLong);
        packetBufferSender.writeBoolean(hidePlatformNumber);
        packetBufferSender.writeUtf(presetId);
    }

    @Override
    public void runServer(MinecraftServer minecraftServer, ServerPlayer serverPlayer) {
        Level world = serverPlayer.level();
        BlockState state = BlockUtil.getBlockState(world, blockPos);

        if(state == null || !(state.getBlock() instanceof JCMBlock)) return;

        ((JCMBlock)state.getBlock()).loopStructure(state, world, blockPos, (bs, be) -> {
            if(be instanceof PIDSBlockEntity) {
                ((PIDSBlockEntity)be).setData(customMessages, filteredPlatforms, rowHidden, hidePlatformNumber, presetId);
            }
        });
    }
}