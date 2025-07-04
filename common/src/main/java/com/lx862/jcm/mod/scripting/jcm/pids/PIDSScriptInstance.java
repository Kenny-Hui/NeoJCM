package com.lx862.jcm.mod.scripting.jcm.pids;

import com.lx862.jcm.mod.block.entity.PIDSBlockEntity;
import com.lx862.mtrscripting.api.ScriptResultCall;
import com.lx862.mtrscripting.core.ParsedScript;
import com.lx862.mtrscripting.core.ScriptInstance;

import java.util.ArrayList;
import java.util.List;

public class PIDSScriptInstance extends ScriptInstance<PIDSWrapper> {
    private final PIDSBlockEntity blockEntity;
    private final String presetId;
    public final List<ScriptResultCall> drawCalls;

    public PIDSScriptInstance(PIDSBlockEntity blockEntity, ParsedScript script, String presetId, PIDSWrapper wrapperObject) {
        super(new PIDSScriptContext(), script);
        setWrapperObject(wrapperObject);
        this.blockEntity = blockEntity;
        this.presetId = presetId;
        this.drawCalls = new ArrayList<>();
    }

    @Override
    public boolean shouldInvalidate() {
        return blockEntity.isRemoved() || !blockEntity.getPresetId().equals(presetId);
    }
}
