package uk.ac.cardiff.team5.graphium.data.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class RoleEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private String roleId;
    private String roleName;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
