package com.guidebook.GuideBook.USER.Service;

import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Repository.ClientAccountRepository;
import com.guidebook.GuideBook.USER.dtos.ClientAccountDetailsForZoomSessionFormResponse;
import com.guidebook.GuideBook.USER.dtos.EditClientAccountRequest;
import com.guidebook.GuideBook.USER.exceptions.ClientAccountNotFoundException;
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
    public void editClientAccountDetails(EditClientAccountRequest request, String clientEmail)
            throws ClientAccountNotFoundException {
        ClientAccount clientAccount = clientAccountRepository.findByClientAccountEmail(clientEmail);

        if(clientAccount!=null){
            clientAccount.setClientFirstName(request.getClientFirstName());
            clientAccount.setClientMiddleName(request.getClientMiddleName());
            clientAccount.setClientLastName(request.getClientLastName());
            clientAccount.setClientPhoneNumber(request.getClientPhoneNumber());
            clientAccount.setClientAge(request.getClientAge());
            clientAccount.setClientCollege(request.getClientCollege());
            clientAccount.setClientValidProof(request.getClientValidProof());
            clientAccount.setClientZoomEmail(request.getClientZoomEmail());

            clientAccountRepository.save(clientAccount);
        } else {
            throw new ClientAccountNotFoundException("client account not found at editClientAccountDetails() method");
        }
    }

    public ClientAccountDetailsForZoomSessionFormResponse getClientAccountDetailsForZoomSessionForm(String clientEmail)
            throws ClientAccountNotFoundException {
        ClientAccount clientAccount = clientAccountRepository.findByClientAccountEmail(clientEmail);
        if(clientAccount!=null){
            return ClientAccountDetailsForZoomSessionFormResponse.builder()
                    .clientFirstName(clientAccount.getClientFirstName())
                    .clientMiddleName(clientAccount.getClientMiddleName())
                    .clientLastName(clientAccount.getClientLastName())
                    .clientCollege(clientAccount.getClientCollege())
                    .clientAge(clientAccount.getClientAge())
                    .clientPhoneNumber(clientAccount.getClientPhoneNumber())
                    .clientValidProof(clientAccount.getClientValidProof())
                    .clientAge(clientAccount.getClientAge())
                    .clientZoomEmail(clientAccount.getClientZoomEmail())
                    .build();
        } else {
            throw new ClientAccountNotFoundException("client account not found at editClientAccountDetails() method");
        }
    }
}
