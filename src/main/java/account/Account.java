package account;

import java.time.LocalDateTime;

public class Account {
    private Long id;
    private String email;
    private String password;
    private String name;
    private LocalDateTime regis_date;
    private LocalDateTime lastvisit_date;

    public Account(String email, String password, String name, LocalDateTime regis_date, LocalDateTime lastvisit_date) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.regis_date = regis_date;
        this.lastvisit_date = lastvisit_date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRegis_date(LocalDateTime regis_date) {
        this.regis_date = regis_date;
    }

    public LocalDateTime getRegis_date() {
        return regis_date;
    }

    public void setLastvisit_date(LocalDateTime lastvisit_date) {
        this.lastvisit_date = lastvisit_date;
    }

    public LocalDateTime getLastvisit_date() {
        return lastvisit_date;
    }

}
