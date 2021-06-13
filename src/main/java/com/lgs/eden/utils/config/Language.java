package com.lgs.eden.utils.config;

/**
 * Language
 *
 * This class is supposed to wrap a language (code + name)
 * and will be used in Settings to handle languages in a generic way.
 */
public enum Language {
    FR("fr", "Français"), EN("en", "English");

    // language code
    public final String code;
    // language name
    public final String name;

    Language(String text, String name) {
        this.code = text;
        this.name = name;
    }

    // show name

    @Override
    public String toString() {
        return this.name;
    }
}
