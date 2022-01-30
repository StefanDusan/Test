package com.intpfy.test;

import com.intpfy.model.event.Role;
import com.intpfyqa.settings.FileSettings;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface FileSharingTest extends EventTest {

    FileSettings SETTINGS = FileSettings.instance();

    static String getSelenoidTestFilePath() {
        return SETTINGS.getSelenoidTestFilePath();
    }

    static String getFileNameFromPath(String path) {
        return new File(path).getName();
    }

    static Set<Role> getSelectedRoles(Map<Role, Boolean> roles) {

        return roles.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
