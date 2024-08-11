package com.guidebook.GuideBook.USER.Service;

import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Repository.ClientAccountRepository;
import com.guidebook.GuideBook.USER.dtos.EditClientAccountRequest;
import jakarta.transaction.Transactional;
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
    @Transactional
    public void editClientAccountDetails(EditClientAccountRequest request, String clientEmail) {
        ClientAccount clientAccount = clientAccountRepository.findByClientAccountEmail(clientEmail);

        clientAccount.setClientFirstName(request.getClientFirstName());
        clientAccount.setClientMiddleName(request.getClientMiddleName());
        clientAccount.setClientLastName(request.getClientLastName());
        clientAccount.setClientPhoneNumber(request.getClientPhoneNumber());
        clientAccount.setClientAge(request.getClientAge());
        clientAccount.setClientCollege(request.getClientCollege());
        clientAccount.setClientValidProof(request.getClientValidProof());
        clientAccount.setClientZoomEmail(request.getClientZoomEmail());

        clientAccountRepository.save(clientAccount);
    }
}
