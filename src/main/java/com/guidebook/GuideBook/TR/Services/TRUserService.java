package com.guidebook.GuideBook.TR.Services;

import com.guidebook.GuideBook.ADMIN.exceptions.AlreadyPresentException;
import com.guidebook.GuideBook.ADMIN.exceptions.EncryptionFailedException;
import com.guidebook.GuideBook.TR.Models.TRUser;
import com.guidebook.GuideBook.TR.Repository.TRUserRepository;
import com.guidebook.GuideBook.TR.dtos.*;
import com.guidebook.GuideBook.TR.exceptions.TRAdminPasswordException;
import com.guidebook.GuideBook.TR.exceptions.TRUserNotFoundException;
import com.guidebook.GuideBook.TR.exceptions.TRUserPasswordNotMatchException;
import com.guidebook.GuideBook.TR.util.EncryptionUtilForStudentProfileEdit;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TRUserService {
    private TRUserRepository trUserRepository;
//    @Value("${tradminpassword}")
    private final String trAdminPassword = "Rohit@2003";
    @Autowired
    public TRUserService(TRUserRepository trUserRepository) {
        this.trUserRepository = trUserRepository;
    }
    @Transactional
    public void addTRUser(AddTRUserRequest addTRUserRequest)
            throws TRAdminPasswordException,
            AlreadyPresentException {
        if(addTRUserRequest.getTrAdminPassword().equals(this.trAdminPassword)){
            if((trUserRepository.findByTrUserFirstNameAndTrUserLastName(
                    addTRUserRequest.getTrUserFirstName(), addTRUserRequest.getTrUserLastName()
            )).isPresent()){
                throw new AlreadyPresentException("TRUSer already present at addTRUser() method.");
            }
             trUserRepository.save(
                    TRUser.builder()
                    .trUserFirstName(addTRUserRequest.getTrUserFirstName().toLowerCase().trim())
                    .trUserLastName(addTRUserRequest.getTrUserLastName().toLowerCase().trim())
                    .trUserPassword(addTRUserRequest.getTrUserPassword().trim())
                    .build()
            );
        } else {
            throw new TRAdminPasswordException("Wrong TR Admin Password at addTRUser() method ");
        }
    }
    @Transactional
    public void updateTRUserPassword(UpdateTRUserRequest updateTRUserRequest)
            throws TRUserNotFoundException, TRAdminPasswordException {
        if (updateTRUserRequest.getTrAdminPassword().equals(this.trAdminPassword)) {
            Optional<TRUser> checkUser = trUserRepository.findByTrUserFirstNameAndTrUserLastName(
                    updateTRUserRequest.getTrUserFirstName().toLowerCase().trim(),
                    updateTRUserRequest.getTrUserLastName().toLowerCase().trim()
            );
            if(!checkUser.isPresent()){
                throw new TRUserNotFoundException("TRUser not found at updateTRUSerPassword() method");
            }
            TRUser user = checkUser.get();
            user.setTrUserPassword(updateTRUserRequest.getTrUserPassword().trim());
            trUserRepository.save(user);
        } else {
            throw new TRAdminPasswordException("Wrong TR Admin Password at addTRUser() method ");
        }
    }
    @Transactional
    public void deleteTRUser(DeleteTRUserRequest deleteTRUserRequest)
            throws TRUserNotFoundException, TRAdminPasswordException {
        if (deleteTRUserRequest.getTrAdminPassword().equals(this.trAdminPassword)) {
            Optional<TRUser> checkUser = trUserRepository.findByTrUserFirstNameAndTrUserLastName(
                    deleteTRUserRequest.getTrUserFirstName().toLowerCase().trim(),
                    deleteTRUserRequest.getTrUserLastName().toLowerCase().trim()
            );
            if(!checkUser.isPresent()){
                throw new TRUserNotFoundException("TRUser not found at updateTRUSerPassword() method");
            }
            TRUser user = checkUser.get();
            trUserRepository.delete(user);
        } else {
            throw new TRAdminPasswordException("Wrong TR Admin Password at deleteTRUser() method ");
        }
    }

    public List<String> getTRUserList() {
        List<String> response = new ArrayList<>();
        List<TRUser> allTRUsers = trUserRepository.findAll();
        for(TRUser user : allTRUsers){
            response.add(user.getTrUserFirstName() + " " + user.getTrUserLastName());
        }
        return response;
    }

    public void loginTRUser(TRUserLoginRequest trUserLoginRequest)
            throws TRUserNotFoundException, TRUserPasswordNotMatchException {

        Optional<TRUser> checkUser = trUserRepository.findByTrUserFirstNameAndTrUserLastName(
                trUserLoginRequest.getTrUserFirstName().toLowerCase().trim(),
                trUserLoginRequest.getTrUserLastName().toLowerCase().trim()
        );
        if(!checkUser.isPresent()){
            throw new TRUserNotFoundException("TRUser not found at loginTRUser() method");
        }
        TRUser user = checkUser.get();
        if(!(user.getTrUserPassword().trim().equals(trUserLoginRequest.getTrUserPassword()))){
            throw new TRUserPasswordNotMatchException("Wrong Password at loginTRUser() method");
        }
    }
    public String generateEncryptedUrlForTRUser(GetSecretUrlRequest getSecretUrlRequest)
            throws TRAdminPasswordException,
            EncryptionFailedException{
        if (getSecretUrlRequest.getTrAdminPassword().equals(this.trAdminPassword)) {
            try {
                // Combine first and last names and convert to lower case
                String combinedName = getSecretUrlRequest.getTrUserFirstName().toLowerCase() + " " +
                        getSecretUrlRequest.getTrUserLastName().toLowerCase();

                // Encrypt the combined name
                String encryptedName = EncryptionUtilForStudentProfileEdit.encrypt(combinedName);

                // URL-encode the encrypted name
                String encodedEncryptedName = URLEncoder.encode(encryptedName, StandardCharsets.UTF_8.toString());

                // Define your base URL and paths here
                String baseUrl = "http://localhost:3000"; // Replace with your actual base URL
                String trStudentApplicationPath = "/TRStudentApplicationForm/";
                String trUpdateStudentApplicationPath = "/TRUpdateStudentApplicationForm/";

                // Generate full URLs
                String trStudentApplicationUrl = baseUrl + trStudentApplicationPath + encodedEncryptedName;
                String trUpdateStudentApplicationUrl = baseUrl + trUpdateStudentApplicationPath + encodedEncryptedName;

                // Return URLs as needed (modify according to your requirements)
                return trStudentApplicationUrl + "\n" + trUpdateStudentApplicationUrl;
            } catch (Exception e) {
                throw new EncryptionFailedException("Error generating URL for TR user at generateEncryptedUrlForTRUser() method");
            }
        } else {
            throw new TRAdminPasswordException("Wrong TR Admin Password at generateEncryptedUrlForTRUser() method ");
        }
    }
    
}
