package uk.ac.cardiff.team5.graphium.data.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class RoleEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int roleId;
    private String roleName;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
