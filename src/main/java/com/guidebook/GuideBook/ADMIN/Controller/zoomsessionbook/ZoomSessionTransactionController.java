package com.guidebook.GuideBook.ADMIN.Controller.zoomsessionbook;

import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.ADMIN.Services.ZoomSessionTransactionService;
import jakarta.transaction.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/admin/")
@Slf4j
public class ZoomSessionTransactionController {
    private final ZoomSessionTransactionService zoomSessionTransactionService;
    @Autowired
    public ZoomSessionTransactionController(ZoomSessionTransactionService zoomSessionTransactionService) {
        this.zoomSessionTransactionService = zoomSessionTransactionService;
    }

    @GetMapping("/getAllZoomSessionTransaction")
    public ResponseEntity<List<ZoomSessionTransaction>> getAllZoomSessionTransaction(){
        List<ZoomSessionTransaction> res = zoomSessionTransactionService.getAllZoomSessionTransaction();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
