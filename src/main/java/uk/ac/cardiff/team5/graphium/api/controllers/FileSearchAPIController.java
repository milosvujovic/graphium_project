package uk.ac.cardiff.team5.graphium.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.cardiff.team5.graphium.domain.FileDisplayer;
import uk.ac.cardiff.team5.graphium.domain.User;
import uk.ac.cardiff.team5.graphium.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api")
public class FileSearchAPIController {
    private UserService userService;
    public FileSearchAPIController(UserService aUserService){
        this.userService = aUserService;
    }

    @GetMapping("/myFiles/{username}")
    public ResponseEntity<List<FileDisplayer>> findMyFiles(@PathVariable String username){
        return ResponseEntity.ok(userService.getsUsersFiles(username));
    }

    @GetMapping("/myOrgFiles/{username}")
    public ResponseEntity<List<FileDisplayer>> findOrgFiles(@PathVariable String username){
        return ResponseEntity.ok(userService.getFilesForOrg(username));
    }

    @GetMapping("/myPartnerFiles/{username}")
    public ResponseEntity<List<FileDisplayer>> findPartnerFiles(@PathVariable String username){
        return ResponseEntity.ok(userService.getPartnersFiles(username));
    }

}
