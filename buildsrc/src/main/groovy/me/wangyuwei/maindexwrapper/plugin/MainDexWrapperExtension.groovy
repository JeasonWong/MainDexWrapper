package me.wangyuwei.maindexwrapper.plugin;

public class MainDexWrapperExtension {

    static String NAME = "mainDexWrapper"

    File keepFile

    boolean enable

    MainDexWrapperExtension() {
        this.keepFile = null
        this.enable = false
    }

    File getKeepFile() {
        return keepFile
    }

    boolean getEnable() {
        return enable
    }


    @Override
    public String toString() {
        return "MainDexWrapperExtension{" +
                "keepFile=" + keepFile +
                ", enable=" + enable +
                '}';
    }
}
