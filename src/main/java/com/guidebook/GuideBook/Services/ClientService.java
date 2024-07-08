package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.Client;
import com.guidebook.GuideBook.Repository.ClientRepository;
import com.guidebook.GuideBook.account.UtilityClass;
import com.guidebook.GuideBook.dtos.accountDtos.SignupRequest;
import com.guidebook.GuideBook.dtos.accountDtos.SignupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private ClientRepository clientRepository;
    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public SignupResponse addAccount(SignupRequest request) {
        Client newClient = new Client();
        newClient.setClientFirstName(request.getClientFirstName());
        if(request.getClientMiddleName()!=null)
            newClient.setClientMiddleName(request.getClientMiddleName());
        newClient.setClientLastName(request.getClientLastName());
        newClient.setClientEmail(request.getClientEmail());
        newClient.setClientPhoneNumber(request.getClientPhoneNumber());
        newClient.setClientAge(request.getClientAge());
        newClient.setClientCollege(request.getClientCollege());
        newClient.setClientProofDocPath(request.getClientProofDocPath());
        newClient.setClientPassword(request.getClientPassword());

        UtilityClass.currentLoggedInProfileEmail = newClient.getClientEmail();
        return getSignupResponse(clientRepository.save(newClient));
    }

    private static SignupResponse getSignupResponse(Client client){
        return SignupResponse.builder()
                .clientFirstName(client.getClientFirstName())
                .clientMiddleName(client.getClientMiddleName())
                .clientLastName(client.getClientLastName())
                .clientEmail(client.getClientEmail())
                .clientPhoneNumber(client.getClientPhoneNumber())
                .clientAge(client.getClientAge())
                .clientCollege(client.getClientCollege())
                .clientProofDocPath(client.getClientProofDocPath())
                //not sending password to frontend
                .build();

    }
}
