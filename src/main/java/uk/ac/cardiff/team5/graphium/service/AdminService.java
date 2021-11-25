package uk.ac.cardiff.team5.graphium.service;

import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;

import java.util.List;

public interface AdminService {
    List<UserDTO> verify(Long organisationId);

}
