package com.project.authtemp.model.accountshared;

@Data
@Builder

public class AccountShared {
    
    @Id
    int id ;
    String username;
    String password;
    String websiteUrl;

    @OneToOne(mappedBy = "user")
    private User user;
    //dev
}
