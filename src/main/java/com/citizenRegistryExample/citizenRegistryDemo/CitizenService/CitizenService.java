package com.citizenRegistryExample.citizenRegistryDemo.CitizenService;

import com.citizenRegistryExample.citizenRegistryDemo.model.Citizen;
import com.citizenRegistryExample.citizenRegistryDemo.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CitizenService {
    private final CitizenRepository citizenRepository;
    public CitizenService(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }
    public List<Citizen> searchCitizen(
            String citizenId,
            String firstName,
            String lastName,
            String gender,
            String dateOfBirth,
            String taxNumber,
            String address
    ) {
        return citizenRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (citizenId != null && !citizenId.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("citizenId"), citizenId));
            }
            if (firstName != null && !firstName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%"));
            }
            if (lastName != null && !lastName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%"));
            }
            if (gender != null && !gender.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("gender"), gender));
            }
            if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("dateOfBirth"), dateOfBirth));
            }
            if (taxNumber != null && !taxNumber.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("taxNumber"), taxNumber));
            }
            if (address != null && !address.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("address"), "%" + address + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
    public boolean updateCitizen(String citizenId, String taxNumber, String address) {
        return citizenRepository.findByCitizenId(citizenId).map(citizen -> {
            citizen.setTaxNumber(taxNumber);
            citizen.setAddress(address);

            citizenRepository.save(citizen);
            return true;
        }).orElse(false);
    }
}
