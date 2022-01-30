package com.intpfy.model;

import java.util.*;

public enum Language {

    English("ENG"),
    German("DEU"),
    French("FRA"),
    Spanish("SPA"),
    Russian("RUS"),
    Chinese("ZHO"),
    Japanese("JPN"),
    Arabic("ARA"),
    Italian("ITA"),
    Interpreting("INT"),
    Source("SRC"),
    Empty("-");

    private final String abbreviation;

    Language(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public static Language getRandomLanguage() {
        return getRandomLanguages(1).get(0);
    }

    public static List<Language> getRandomLanguages(int languagesCount) {

        Set<Language> resultSet = new HashSet<>();
        Language[] languages = values();

        int sourceIndex = Source.ordinal();
        int emptyIndex = Empty.ordinal();

        int index;
        Random random = new Random();

        while (resultSet.size() != languagesCount) {

            index = random.nextInt(languages.length);

            if (index == sourceIndex || index == emptyIndex) {
                continue;
            }

            resultSet.add(languages[index]);
        }

        return List.copyOf(resultSet);
    }

    public static Language getLanguage(String nameOrAbbreviation) {
        for (Language language : values()) {
            if (language.getAbbreviation().equalsIgnoreCase(nameOrAbbreviation) ||
                    language.name().equalsIgnoreCase(nameOrAbbreviation)) {
                return language;
            }
        }
        throw new IllegalArgumentException(String.format("There is no language with name or abbreviation '%s'.", nameOrAbbreviation));
    }
}
