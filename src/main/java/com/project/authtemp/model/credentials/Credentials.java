package com.project.authtemp.model.credentials;

import java.time.LocalDateTime;
import java.util.List;

import com.project.authtemp.model.token.Token;
import com.project.authtemp.model.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {

    @Id
    @GeneratedValue
    int id;
    String username;
    String password;
    String websiteUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "token_id", nullable = false)
    private Token token;
    // dev

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "shared_credentials", joinColumns = @JoinColumn(name = "credential_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> sharedWithUsers; // List of users with whom the credential is shared

    @Column(nullable = false)
    private LocalDateTime sharedAt;

    public void shareCredential(Credentials credentials, User userToShareWith) {
        credentials.getSharedWithUsers().add(userToShareWith);
        credentials.setSharedAt(LocalDateTime.now()); // Optionally track when the credential was shared
        CredentialsRepository credentialsRepository;
        // Save the updated credentials object to the database
        credentialsRepository.save(credentials);
    }

}
