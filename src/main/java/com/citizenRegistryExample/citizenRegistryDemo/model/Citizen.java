package com.citizenRegistryExample.citizenRegistryDemo.model;





import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


import java.time.LocalDate;


@Entity
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 8, max = 8,message = "Id must be 8 characters in length")
    private String citizenId ;//AT
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message="Last name is required")
    private String lastName;
    @Pattern(
            regexp = "\\d{2}-\\d{2}-\\d{4}",
            message = "Date of birth must follow the format dd-MM-YYYY."
    )
    private String dateOfBirth;
    private String gender;
    @Size(min=9,max=9,message="Vat number should be 9 characters in length")
    private String taxNumber;
    private String address;


    public Citizen() {


    }

    public Citizen(String citizenId, String firstName, String lastName, String dateOfBirth, String gender, String taxNumber, String address) {
        this.citizenId = citizenId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.taxNumber = taxNumber;
        this.address = address;
    }


    public String getCitizenId() {
        return citizenId;
    }
    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getTaxNumber() {
        return taxNumber;
    }
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    //test3 check
}
