package uk.ac.cardiff.team5.graphium.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.cardiff.team5.graphium.service.AdminService;
import uk.ac.cardiff.team5.graphium.service.dto.OrganisationDTO;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminAPIController {
    private AdminService adminService;
    public AdminAPIController(AdminService adminService){
        this.adminService = adminService;
    }

    @GetMapping("/potentialPartners")
    public ResponseEntity<List<OrganisationDTO>> potentialPartners(Principal principal){
        return ResponseEntity.ok().body(adminService.findPossiblePartners(principal.getName()));
    }
    @GetMapping("/currentPartners")
    public ResponseEntity<List<OrganisationDTO>> currentPartners(Principal principal){
        return ResponseEntity.ok().body(adminService.findSharingPartners(principal.getName()));
    }
    @GetMapping("/partnersThatYouCanView")
    public ResponseEntity<List<OrganisationDTO>> viewingPartners(Principal principal){
        System.out.println(adminService.findPartnersThatYouCanView(principal.getName()));
        return ResponseEntity.ok().body(adminService.findPartnersThatYouCanView(principal.getName()));
    }
}
