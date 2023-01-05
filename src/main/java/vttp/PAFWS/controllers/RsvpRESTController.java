package vttp.PAFWS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import vttp.PAFWS.repositories.RsvpRepository;

@RestController
@EnableWebMvc
public class RsvpRESTController {
    
    @GetMapping(path = "/rsvps")
    public ResponseEntity<String> getAllRSVP(){
        return null;
    }

    @Autowired
    RsvpRepository RSrepo;

    @PostMapping(path = "/addRsvp")
    public ResponseEntity<String> addRsvp(@RequestBody MultiValueMap<String, String> form){
        String name = form.getFirst("name");
        String email = form.getFirst("email");
        String comment = form.getFirst("comment");
        if(RSrepo.findRSVPByEmail(email).isEmpty()){
            RSrepo.insertRsvp(name, email, comment);
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.TEXT_HTML).body("new RSVP");
        }else{
            return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.TEXT_HTML).body("updated RSVP" + RSrepo.updateRSVP(name, email, comment));
        }
        
    }
}
