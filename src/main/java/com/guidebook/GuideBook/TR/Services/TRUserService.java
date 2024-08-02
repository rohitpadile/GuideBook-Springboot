package com.guidebook.GuideBook.TR.Services;

import com.guidebook.GuideBook.TR.Models.TRUser;
import com.guidebook.GuideBook.TR.Repository.TRUserRepository;
import com.guidebook.GuideBook.TR.dtos.AddTRUserRequest;
import com.guidebook.GuideBook.TR.dtos.DeleteTRUserRequest;
import com.guidebook.GuideBook.TR.dtos.UpdateTRUserRequest;
import com.guidebook.GuideBook.TR.exceptions.TRAdminPasswordException;
import com.guidebook.GuideBook.TR.exceptions.TRUserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TRUserService {
    private TRUserRepository trUserRepository;
    @Value("${tradminpassword}")
    private String trAdminPassword;
    @Autowired
    public TRUserService(TRUserRepository trUserRepository) {
        this.trUserRepository = trUserRepository;
    }
    @Transactional
    public void addTRUser(AddTRUserRequest addTRUserRequest)
            throws TRAdminPasswordException {
        if(addTRUserRequest.getTrAdminPassword().equals(this.trAdminPassword)){
             trUserRepository.save(
                    TRUser.builder()
                    .trUserFirstName(addTRUserRequest.getTrUserFirstName())
                    .trUserLastName(addTRUserRequest.getTrUserLastName())
                    .trUserPassword(addTRUserRequest.getTrUserPassword())
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
                    updateTRUserRequest.getTrUserFirstName(),
                    updateTRUserRequest.getTrUserLastName()
            );
            if(!checkUser.isPresent()){
                throw new TRUserNotFoundException("TRUser not found at updateTRUSerPassword() method");
            }
            TRUser user = checkUser.get();
            user.setTrUserPassword(updateTRUserRequest.getTrUserPassword());
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
                    deleteTRUserRequest.getTrUserFirstName(),
                    deleteTRUserRequest.getTrUserLastName()
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
}
