package com.guidebook.GuideBook.USER.Service;

import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Repository.ClientAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientAccountService {
    private final ClientAccountRepository clientAccountRepository;
    @Autowired
    public ClientAccountService(ClientAccountRepository clientAccountRepository) {
        this.clientAccountRepository = clientAccountRepository;
    }

    public ClientAccount getAccountByEmail(String email){
        return clientAccountRepository.findByClientAccountEmail(email);
    }
}
