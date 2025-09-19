package br.com.andydkeh.enums;

public enum LoginResponse {
    SUCCESS("Login successful."),
    USER_NOT_FOUND("This user does not exist."),
    WRONG_PASSWORD("Wrong password. Please try again."),
    USER_BLOCKED("This user is blocked, please contact your bank manager.");

    private final String message;

    LoginResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}