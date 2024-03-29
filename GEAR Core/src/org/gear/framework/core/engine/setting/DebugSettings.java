package org.gear.framework.core.engine.setting;

public class DebugSettings {
    private boolean enableFileTracking;
    private boolean showLogs;
    private boolean generateCriticalFiles;

    public DebugSettings(boolean showLogs, boolean generateCriticalFiles, boolean enableFileTracking) {
        this.showLogs = showLogs;
        this.generateCriticalFiles = generateCriticalFiles;
        this.enableFileTracking = enableFileTracking;
    }

    public Boolean isShowLogs() {
        return showLogs;
    }

    public Boolean isGenerateCriticalFiles() {
        return generateCriticalFiles;
    }

    public Boolean isEnableFileTracking() {
        return enableFileTracking;
    }
}
