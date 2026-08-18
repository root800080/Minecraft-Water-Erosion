package com._13rac1.erosion.common;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import javax.annotation.Nonnull;

import cpw.mods.modlauncher.api.IModuleLayerManager;

import net.minecraft.WorldVersion;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.storage.DataVersion;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.LoadingModList;

public class FakeWorldVersion implements WorldVersion {
    static private boolean once = true;

    // init() starts up the Minecraft system, once
    static void init() {
        if (!once) {
            return;
        }
        once = false;

        // Since 1.21, vanilla's Bootstrap.bootStrap() reaches into modded hooks (item component
        // gathering, mod state scanning) via FMLLoader, which normally only exist in a real
        // FML/ModLauncher runtime. Stub in empty versions of what FMLLoader exposes so a plain
        // unit test JVM can still run the vanilla bootstrap.
        try {
            LoadingModList modList = LoadingModList.of(Collections.emptyList(), Collections.emptyList(), null);
            modList.setBrokenFiles(Collections.emptyList());
            setField(FMLLoader.class, "loadingModList", modList);

            IModuleLayerManager layerManager = layer -> Optional.of(ModuleLayer.boot());
            setField(FMLLoader.class, "moduleLayerManager", layerManager);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        WorldVersion version = new FakeWorldVersion();
        net.minecraft.SharedConstants.setVersion(version);
        net.minecraft.server.Bootstrap.bootStrap();
    }

    private static void setField(Class<?> cls, String name, Object value) throws ReflectiveOperationException {
        Field field = cls.getDeclaredField(name);
        field.setAccessible(true);
        field.set(null, value);
    }

    @Override
    public DataVersion getDataVersion() {
        return new DataVersion(13);
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    @Override
    public int getProtocolVersion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProtocolVersion'");
    }

    @Override
    public int getPackVersion(@Nonnull PackType p_265245_) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPackVersion'");
    }

    @Override
    public Date getBuildTime() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBuildTime'");
    }

    @Override
    public boolean isStable() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isStable'");
    }

}
