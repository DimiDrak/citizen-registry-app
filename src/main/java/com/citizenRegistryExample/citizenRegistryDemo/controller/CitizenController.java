package com.citizenRegistryExample.citizenRegistryDemo.controller;

import com.citizenRegistryExample.citizenRegistryDemo.CitizenService.CitizenService;
import com.citizenRegistryExample.citizenRegistryDemo.model.Citizen;
import com.citizenRegistryExample.citizenRegistryDemo.repository.CitizenRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CitizenController {
    private final CitizenRepository citizenRepository;
    private final CitizenService citizenService;

    public CitizenController(CitizenRepository citizenRepository, CitizenService citizenService) {
        this.citizenRepository = citizenRepository;
        this.citizenService = citizenService;
    }

    @GetMapping("/citizens")
    public List<Citizen> getAllCitizens(){
        return citizenRepository.findAll();
    }

   @PostMapping("/addCitizen")
    public Citizen addCitizen(@Valid @RequestBody Citizen newCitizen){
      return  citizenRepository.save(newCitizen);
   }

   @DeleteMapping("/delete/{citizenId}")
    public ResponseEntity<String> deleteCitizen(@PathVariable("citizenId") String citizenId){ //REsponseEntity returns status codes too;
    Optional<Citizen> citizen = citizenRepository.findByCitizenId(citizenId);
        if(citizen.isPresent()){
            citizenRepository.delete(citizen.get());
            return ResponseEntity.ok( "Citizen with citizenId "+citizenId+" was successfully deleted");

        }
        //test5
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( "Citizen with citizenId "+citizenId+" was not found");
   }

   @GetMapping("/searchCitizen")
    public List<Citizen> searchCitizen(
           @RequestParam(required = false) String citizenId,
           @RequestParam(required = false) String firstName,
           @RequestParam(required = false) String lastName,
           @RequestParam(required = false) String gender,
           @RequestParam(required = false) String dateOfBirth,
           @RequestParam(required = false) String taxNumber,
           @RequestParam(required = false) String address){
            return citizenService.searchCitizen(citizenId,firstName,lastName,gender,dateOfBirth,taxNumber,address);
   }
    @PutMapping("/updateCitizen")
    public ResponseEntity<String> updateCitizen(
            @RequestParam String citizenId,
            @RequestParam String taxNumber,
            @RequestParam String address
    ) {
        boolean isUpdated = citizenService.updateCitizen(citizenId, taxNumber, address);
        if (isUpdated) {
            return ResponseEntity.ok("Citizen with ID " + citizenId + " was successfully updated.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Citizen with ID " + citizenId + " not found.");
        }
    }
//added test comment

}
