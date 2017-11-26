package me.wangyuwei.maindexwrapper.plugin;

public class AnnotationsCollector {

    private static def COLLECTOR = []

    public static void put(String keepRules) {
        if (keepRules == null || "" == keepRules) {
            return
        }
        COLLECTOR.add(keepRules)
    }

    public static def get() {
        return COLLECTOR
    }

    public static void reset() {
        COLLECTOR.clear()
    }

}
