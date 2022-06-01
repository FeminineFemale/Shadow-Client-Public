/*
 * Copyright (c) Shadow client, Saturn5VFive and contributors 2022. All rights reserved.
 */

package net.shadow.client.feature.module.impl.render;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import net.shadow.client.ShadowMain;
import net.shadow.client.feature.module.Module;
import net.shadow.client.feature.module.ModuleType;
import net.shadow.client.helper.render.Renderer;
import net.shadow.client.helper.util.Utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Trail extends Module {
    final List<Vec3d> positions = new ArrayList<>();

    public Trail() {
        super("Trail", "Leaves behind a short trail when you travel", ModuleType.RENDER);
    }

    @Override
    public void tick() {

    }

    @Override
    public void onFastTick() {
        positions.add(Utils.getInterpolatedEntityPosition(ShadowMain.client.player));
        while (positions.size() > 1000) {
            positions.remove(0);
        }
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public String getContext() {
        return null;
    }

    @Override
    public void onWorldRender(MatrixStack matrices) {
        List<Vec3d> bk = new ArrayList<>(positions);
        float progressOffset = (System.currentTimeMillis() % 1000) / 1000f;
        for (int i = 0; i < bk.size(); i++) {
            float progress = i / (float) bk.size();
            progress += progressOffset;
            progress %= 1;
            Vec3d vec3d = bk.get(i);
            if (vec3d == null) {
                continue;
            }
            Vec3d before;
            if (i == 0) {
                before = vec3d;
            } else {
                before = bk.get(i - 1);
            }
            if (before == null) {
                before = vec3d;
            }
            Renderer.R3D.renderLine(before, vec3d, Color.getHSBColor(progress, 0.6f, 1f), matrices);
        }

    }

    @Override
    public void onHudRender() {

    }
}
