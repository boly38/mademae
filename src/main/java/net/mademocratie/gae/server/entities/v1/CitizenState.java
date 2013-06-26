package net.mademocratie.gae.server.entities.v1;


public enum CitizenState {
    CREATED,
    ACTIVE,
    SUSPENDED,
    REMOVED;

    private CitizenState() {
    }
    public String value() {
        return name();
    }

    public static CitizenState fromValue(String v) {
        return valueOf(v);
    }
}
