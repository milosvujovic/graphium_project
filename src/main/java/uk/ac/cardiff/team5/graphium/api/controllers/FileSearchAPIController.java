package uk.ac.cardiff.team5.graphium.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.cardiff.team5.graphium.domain.FileDisplayer;
import uk.ac.cardiff.team5.graphium.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
public class FileSearchAPIController {
    private UserService userService;
    public FileSearchAPIController(UserService aUserService){
        this.userService = aUserService;
    }

    @GetMapping("/myFiles")
    public ResponseEntity<List<FileDisplayer>> findMyFiles(Principal principal){
        return ResponseEntity.ok().body(userService.getsUsersFiles(principal.getName()));
    }

    @GetMapping("/myOrgFiles")
    public ResponseEntity<List<FileDisplayer>> findOrgFiles(Principal principal){
        return ResponseEntity.ok(userService.getFilesForOrg(principal.getName()));
    }

    @GetMapping("/myPartnerFiles")
    public ResponseEntity<List<FileDisplayer>> findPartnerFiles(Principal principal){
        return ResponseEntity.ok(userService.getPartnersFiles(principal.getName()));
    }
    @GetMapping("/publicFiles")
    public ResponseEntity<List<FileDisplayer>> findPublicFiles(){
        return ResponseEntity.ok(userService.getPublicFiles());
    }
    @GetMapping("/allFiles")
    public ResponseEntity<List<FileDisplayer>> findAllFiles(Principal principal){
        return ResponseEntity.ok(userService.getAllFiles(principal.getName()));

    }
    @GetMapping("/searchFiles/{term}")
    public ResponseEntity<List<FileDisplayer>> findMyFiles(@PathVariable Optional<String> term, Principal principal){
        return term.map(s -> ResponseEntity.ok(userService.findBySearchTerm(s, principal.getName()))).orElseGet(() -> ResponseEntity.ok(userService.getAllFiles(principal.getName())));
    }
}
