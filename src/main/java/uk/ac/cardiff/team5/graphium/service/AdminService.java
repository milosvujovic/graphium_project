package uk.ac.cardiff.team5.graphium.service;

import uk.ac.cardiff.team5.graphium.service.dto.OrganisationDTO;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;

import java.util.List;

public interface AdminService {
    List<UserDTO> verify(Long organisationId);

    void verifyUser(String userName);

    List<OrganisationDTO> findPossiblePartners(String name);

    void recordPartnership(String orgId, String name);
}
