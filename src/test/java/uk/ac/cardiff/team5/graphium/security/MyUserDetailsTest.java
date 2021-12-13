package uk.ac.cardiff.team5.graphium.security;

import org.junit.jupiter.api.Test;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.RoleEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.UserEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyUserDetailsTest {

    @Test
    public void authoriseApprovedUser() {
        UserEntity approvedUser = new UserEntity();
        approvedUser.setRole(createRoleEntity());
        approvedUser.setOrganisation_approved(true);
        MyUserDetails userDetails = new MyUserDetails(approvedUser);
        var authorities = userDetails.getAuthorities();
        assertEquals(1 ,authorities.size());
    }

    private RoleEntity createRoleEntity() {
        RoleEntity role = new RoleEntity();
        role.setRoleId("1");
        role.setRoleName("user");
        return role;
    }

    @Test
    public void noAuthorityForUnapprovedUser() {
        UserEntity unapprovedUser = new UserEntity();
        unapprovedUser.setRole(createRoleEntity());
        unapprovedUser.setOrganisation_approved(false);
        MyUserDetails userDetails = new MyUserDetails(unapprovedUser);
        var authorities = userDetails.getAuthorities();
        assertEquals(0, authorities.size());
    }
}
