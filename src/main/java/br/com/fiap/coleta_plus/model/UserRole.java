package br.com.fiap.coleta_plus.model;

public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER"),
    AUDIT("AUDIT");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
