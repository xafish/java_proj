package webServerHomework.services;

public class ClientAuthServiceImpl implements UserAuthService {

    private final String adminUser;
    private final String adminPassword;

    public ClientAuthServiceImpl(String adminUser, String adminPassword) {
        this.adminUser = adminUser;
        this.adminPassword = adminPassword;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return adminUser.equals(login)&adminPassword.equals(password);
    }

}
