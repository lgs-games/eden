package com.lgs.eden.api.games;

/**
 * Class when API return some informations
 * like the version of the game and
 * how we can download the new version (links).
 */
public record GameUpdateData (String version, String downloadURL, String runnableURL, String uninstallURL, double size) {}
