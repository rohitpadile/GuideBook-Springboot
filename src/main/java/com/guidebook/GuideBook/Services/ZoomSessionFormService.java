package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.ZoomSessionForm;
import com.guidebook.GuideBook.Repository.ZoomSessionFormRepository;
import com.guidebook.GuideBook.dtos.ZoomSessionFormRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class ZoomSessionFormService {

    private ZoomSessionFormRepository zoomSessionFormRepository;
    @Autowired
    public ZoomSessionFormService(ZoomSessionFormRepository zoomSessionFormRepository) {
        this.zoomSessionFormRepository = zoomSessionFormRepository;
    }

    public ZoomSessionForm submitForm(ZoomSessionFormRequest formDTO) {
        ZoomSessionForm form = ZoomSessionForm.builder()
                .clientFirstName(formDTO.getClientFirstName())
                .clientMiddleName(formDTO.getClientMiddleName())
                .clientLastName(formDTO.getClientLastName())
                .clientEmail(formDTO.getClientEmail())
                .clientPhoneNumber(formDTO.getClientPhoneNumber())
                .clientAge(formDTO.getClientAge())
                .clientCollege(formDTO.getClientCollege())
                .clientProofDocLink(formDTO.getClientProofDocLink())
                .build();

        return zoomSessionFormRepository.save(form);
    }
}
