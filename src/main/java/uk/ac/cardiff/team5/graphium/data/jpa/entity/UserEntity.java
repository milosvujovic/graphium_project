package uk.ac.cardiff.team5.graphium.data.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.OrganisationRepository;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    private Boolean active = true;
    private Boolean organisation_approved = false;
    private Boolean email_verified = false;
    private String role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    List<RoleEntity> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    OrganisationEntity organisation;

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public String getRole() {return role;}

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public OrganisationEntity getOrganisation() {
        return organisation;
    }

    public void setOrganisation(OrganisationEntity organisation) {
        this.organisation = organisation;
    }

    public UserEntity(String firstName, String lastName, OrganisationEntity organisation, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.organisation = organisation;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
