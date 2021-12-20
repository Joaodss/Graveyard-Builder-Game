package com.ironhack.usermodelservice.dao;

import com.ironhack.usermodelservice.dto.RegisterUserDTO;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Slf4j
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(name = "experience")
    private Long experience;

    @Column(name = "gold")
    private Long gold;

    @Column(name = "party_level")
    private Integer partyLevel;


    // -------------------- Custom Constructor --------------------
    public User(
            String username,
            String email,
            String password,
            Set<Role> roles,
            String profilePictureUrl,
            Long experience,
            Long gold,
            Integer partyLevel
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.profilePictureUrl = profilePictureUrl;
        this.experience = experience;
        this.gold = gold;
        this.partyLevel = partyLevel;
        log.info("New User created -> {}", this);
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = new HashSet<>();
        this.profilePictureUrl = "";
        this.experience = 0L;
        this.gold = 0L;
        this.partyLevel = 0;
        log.info("New User created -> {}", this);
    }

    public User(RegisterUserDTO registerUserDTO) {
        this.username = registerUserDTO.getUsername();
        this.email = registerUserDTO.getEmail();
        this.password = registerUserDTO.getPassword();
        this.roles = new HashSet<>();
        this.profilePictureUrl = "";
        this.experience = 0L;
        this.gold = 0L;
        this.partyLevel = 0;
        log.info("New User created -> {}", this);
    }


    // -------------------- Equals and HashCode --------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(roles, user.roles) &&
                Objects.equals(experience, user.experience) &&
                Objects.equals(gold, user.gold) &&
                Objects.equals(partyLevel, user.partyLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, roles, experience, gold, partyLevel);
    }

}
