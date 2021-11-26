package uk.ac.cardiff.team5.graphium.data.jpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    RoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    OrganisationEntity organisation;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="user_id", nullable = false)
    private List<DBFile> files;


    public String getRole() {
        return role.getRoleId();
    }

    public void setRole(RoleEntity newRole) {this.role = newRole;}

    public OrganisationEntity getOrganisation() {
        return organisation;
    }

    public void setOrganisation(OrganisationEntity organisation) {
        this.organisation = organisation;
    }

    public UserEntity(String firstName, String lastName, OrganisationEntity organisation, String username, String email, String password, RoleEntity userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.organisation = organisation;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = userRole;
    }

    public UserEntity(String username) {
        this.username = username;
    }

    public void addFile(DBFile aFile){
        this.files.add(aFile);
    }

}
