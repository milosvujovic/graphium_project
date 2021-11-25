package uk.ac.cardiff.team5.graphium.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.OrganisationEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.OrganisationRepository;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.UserRepository;
import uk.ac.cardiff.team5.graphium.service.AdminService;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganisationRepository organisationRepository;


    @Override
    public List<UserDTO> verify(Long organisationId) {
        OrganisationEntity organisation = organisationRepository.findById(organisationId).get();
        return userRepository.findUserEntitiesByOrganisation(organisation)
                .stream()
                .filter(c -> c.getOrganisation_approved() == false)
                .map(c -> new UserDTO(c))
                .collect(Collectors.toList());
    }
}
