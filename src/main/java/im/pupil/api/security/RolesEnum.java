package im.pupil.api.security;

public enum RolesEnum {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String description;

    RolesEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
