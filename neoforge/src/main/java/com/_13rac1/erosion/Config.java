package com._13rac1.erosion;

import net.neoforged.neoforge.common.ModConfigSpec;

// https://github.com/neoforged/MDK/blob/main/src/main/java/com/example/examplemod/Config.java

public class Config {
  public static final String CATEGORY_GENERAL = "general";
  public static ModConfigSpec COMMON_CONFIG;

  public static ModConfigSpec.BooleanValue ERODE_FARMLAND;

  static {
    ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

    COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

    ERODE_FARMLAND = COMMON_BUILDER.comment("Enable erode and decay of Farmland").define("erodeFarmland", true);

    COMMON_BUILDER.pop();

    COMMON_CONFIG = COMMON_BUILDER.build();
  }
}
