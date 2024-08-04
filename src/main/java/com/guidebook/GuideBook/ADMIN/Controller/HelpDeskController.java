//package com.guidebook.GuideBook.ADMIN.Controller;
//
//import com.guidebook.GuideBook.ADMIN.Services.HelpDeskService;
//import com.guidebook.GuideBook.ADMIN.dtos.AddHelpDeskEmailRequest;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins = {
//        "http://localhost:3000", "http://localhost:8080",
//        "https://www.guidebookx.com","https://guidebookx.com",
//        "https://api.guidebookx.com",
//        "https://diugkigakpnwm.cloudfront.net"})
//@RestController
//@RequestMapping("/api/v1/admin/")
//public class HelpDeskController {
//    private HelpDeskService helpDeskService;
//    @Autowired
//    public HelpDeskController(HelpDeskService helpDeskService) {
//        this.helpDeskService = helpDeskService;
//    }
//    @PostMapping("/addHelpDeskEmail")
//    public ResponseEntity<Void> addHelpDeskEmail(
//            @RequestBody @Valid AddHelpDeskEmailRequest addHelpDeskEmailRequest
//    ){
//        helpDeskService.addHelpDeskEmail(addHelpDeskEmailRequest);
//        return new ResponseEntity<>(HttpStatus.ACCEPTED);
//    }
//}
