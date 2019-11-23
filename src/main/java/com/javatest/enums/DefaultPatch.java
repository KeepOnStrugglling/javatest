package com.javatest.enums;

public enum DefaultPatch {

    ATS("ATS_V500"),CCF("CCF_V600"),CSCF("CSCF_V500"),SBC("SE2900_V300"),
    SPG("SPG_V500"),UT("UIM_V900"),UT_USCDB("USCDB_V100"),MRFP("V500");

    private final String patch;

    DefaultPatch(String patch) {
        this.patch = patch;
    }

    public String getPatch(){
        return this.patch;
    }

    /**
     * 根据枚举的名称获取对应的枚举值
     */
    public static String getPatchByType(String type) {
        for (DefaultPatch patch : DefaultPatch.values()) {
            if (patch.name().equals(type)) {
                return patch.getPatch();
            }
        }
        return null;
    }
}
