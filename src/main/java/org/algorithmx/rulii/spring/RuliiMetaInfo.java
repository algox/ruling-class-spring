package org.algorithmx.rulii.spring;

import java.util.Arrays;

public class RuliiMetaInfo {

    private String scriptLanguage;
    private String[] messageSources;

    public RuliiMetaInfo(String scriptLanguage, String[] messageSources) {
        super();
        this.scriptLanguage = scriptLanguage;
        this.messageSources = messageSources;
    }

    public String getScriptLanguage() {
        return scriptLanguage;
    }

    public String[] getMessageSources() {
        return messageSources;
    }

    @Override
    public String toString() {
        return "RuliiMetaInfo{" +
                "scriptLanguage='" + scriptLanguage + '\'' +
                ", messageSources=" + Arrays.toString(messageSources) +
                '}';
    }
}
