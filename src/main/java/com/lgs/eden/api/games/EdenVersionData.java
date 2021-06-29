package com.lgs.eden.api.games;

/**
 * Wrapper for Eden version result from the API.
 */
public record EdenVersionData (String version, String downloadURL, double size) {}
