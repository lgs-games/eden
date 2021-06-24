package com.lgs.eden.api.games;

import com.lgs.eden.utils.Utility;
import javafx.scene.image.Image;

/**
 * Basic Game information. Mainly
 * used when we don't want to fetch to much
 * data from the API unless the client wants
 * more.
 */
public class BasicGameData {

    public final String id;
    public final String name;
    public final Image icon;
    private final String path;

    public BasicGameData(String id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon != null ? Utility.loadImage(icon) : null;
        this.path = icon;
    }

    public String getIconPath() { return this.path; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicGameData)) return false;

        BasicGameData that = (BasicGameData) o;

        if (!this.id.equals(that.id)) return false;
        return this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    // can't size Image not clonable it seems
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public BasicGameData clone() {
        return new BasicGameData(
                this.id,
                this.name,
                this.path
        );
    }

    @Override
    public String toString() {
        return "BasicGameData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
