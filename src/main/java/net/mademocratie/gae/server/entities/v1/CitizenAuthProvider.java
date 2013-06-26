package net.mademocratie.gae.server.entities.v1;

public enum CitizenAuthProvider {
    NONE,
    GOOGLE;

    private CitizenAuthProvider() {
    }
    public String value() {
        return name();
    }

    public static CitizenAuthProvider fromValue(String v) {
        return valueOf(v);
    }
}
