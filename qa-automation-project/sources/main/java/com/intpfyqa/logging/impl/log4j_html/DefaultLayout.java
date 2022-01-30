package com.intpfyqa.logging.impl.log4j_html;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.spi.LoggingEvent;
import org.testng.util.Strings;


/**
 * Default logger layout
 */
public class DefaultLayout extends SimpleLayout {
    @Override
    public String getHeader() {
        return null;
    }

    @Override
    public String getFooter() {
        return null;
    }

    /**
     * Get header for new test in debug
     *
     * @param testObjectIdentifier event message
     * @return new test debug header
     */
    private String getTestMethodHeader(TestObjectIdentifier testObjectIdentifier) {

        String testName = testObjectIdentifier.getTestName();
        String idx = testObjectIdentifier.getId();
        String group = createGroupString(testObjectIdentifier);

        return String.format("statuses['%s'] = {id: %s, " +
                        "name: '%s', " +
                        "config: %s, group: '%s', duration: '--', attributes: %s, icon: 'jstree-loading'};\n",
                idx, idx, testName.replace("'", "\""), testObjectIdentifier.isConfiguration(), group,
                parseTestAttributes(testObjectIdentifier));

    }

    private String createGroupString(TestObjectIdentifier testObjectIdentifier) {
        if (testObjectIdentifier.isConfiguration()) return "Configuration";
        String testClass = testObjectIdentifier.getClassName();
        if (!Strings.isNullOrEmpty(testObjectIdentifier.getFeature())) return testObjectIdentifier.getFeature();
        if (!Strings.isNullOrEmpty(testObjectIdentifier.getTmsId())) return testObjectIdentifier.getTmsId();
        String group;
        //if (testClass.toLowerCase().startsWith(testsPackage)) {
        group = testClass;
        group = group.replace(".", "->").replace("_", " ");
        String[] packs = group.split("->");
        group = "";
        for (int p = 0; p < packs.length - 1; p++) {
            String[] words = packs[p].split(" ");
            for (int i = 0; i < words.length; i++) {
                words[i] = words[i].length() > 0 ? words[i].substring(0, 1).toUpperCase() + words[i].substring(1) : words[i];
            }
            group += StringUtils.join(words, " ");
            if (p < packs.length - 2) group += "->";
        }

        return group;
    }

    private String parseTestAttributes(TestObjectIdentifier testObjectIdentifier) {
        JsonObject attrs = new JsonObject();
        testObjectIdentifier.getAttributes().forEach((k, v) -> {
            attrs.addProperty(k,
                    v.toString());
        });
        return new Gson().toJson(attrs);
    }

    /**
     * Ends debug test section
     *
     * @return close test method debug
     */
    private String getTestMethodFooter(TestObjectIdentifier testObjectIdentifier) {
        String idx = testObjectIdentifier.getId();
        String icon = testObjectIdentifier.isFailed() ? "jstree-er" : "jstree-ok";

        return String.format("statuses['%s'].icon='%s';statuses['%s'].duration='%s';\n",
                idx, icon, idx, testObjectIdentifier.getDurationString());
    }


    /**
     * Formats debug event
     *
     * @param event event
     * @return formatted HTML string
     */
    public String format(LoggingEvent event) {

        String messageString = event.getMessage() + "";
        TestObjectIdentifier eventObject;
        eventObject = EventObjectHelper.eventToTestObject(messageString);

        if (EventObjectHelper.isStartTest(messageString)) {
            return getTestMethodHeader(eventObject);
        }

        if (EventObjectHelper.isEndTest(messageString)) {
            return getTestMethodFooter(eventObject);
        }

        return "";
    }

}
