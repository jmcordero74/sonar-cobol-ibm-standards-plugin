package com.plugin.cobol.language;

import org.sonar.api.resources.AbstractLanguage;

/**
 * Definición del lenguaje COBOL para SonarQube.
 */
public class CobolLanguage extends AbstractLanguage {

    public static final String KEY = "cobol-standard";
    public static final String NAME = "COBOL";
    public static final String[] FILE_SUFFIXES = {".cbl", ".cob", ".cobol", ".cpy", ".CBL", ".COB"};

    public CobolLanguage() {
        super(KEY, NAME);
    }

    @Override
    public String[] getFileSuffixes() {
        return FILE_SUFFIXES;
    }
}
