package com.guidebook.GuideBook.USER.Controller;

import com.guidebook.GuideBook.ADMIN.Repository.StudentRepository;
import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Models.MyUser;
import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import com.guidebook.GuideBook.USER.Repository.ClientAccountRepository;
import com.guidebook.GuideBook.USER.Repository.MyUserRepository;
import com.guidebook.GuideBook.USER.Repository.StudentMentorAccountRepository;
import com.guidebook.GuideBook.USER.Service.CustomUserDetailsService;
import com.guidebook.GuideBook.USER.Service.JwtUtil;
import com.guidebook.GuideBook.USER.Service.TokenBlacklistService;
import com.razorpay.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/user/")
public class SecurityController {


    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final MyUserRepository myUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenBlacklistService tokenBlacklistService;
    private final StudentRepository studentRepository;
    private final ClientAccountRepository clientAccountRepository;
    private final StudentMentorAccountRepository studentMentorAccountRepository;
    private final StudentService studentService;
    @Autowired
    public SecurityController(AuthenticationManager authenticationManager,
                              CustomUserDetailsService userDetailsService,
                              JwtUtil jwtUtil,
                              MyUserRepository myUserRepository,
                              PasswordEncoder passwordEncoder,
                              TokenBlacklistService tokenBlacklistService,
                              StudentRepository studentRepository,
                              ClientAccountRepository clientAccountRepository,
                              StudentMentorAccountRepository studentMentorAccountRepository,
                              StudentService studentService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.myUserRepository = myUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenBlacklistService = tokenBlacklistService;
        this.studentRepository = studentRepository;
        this.clientAccountRepository = clientAccountRepository;
        this.studentMentorAccountRepository = studentMentorAccountRepository;
        this.studentService = studentService;
    }

    @PostMapping("/signup")
    @Transactional
    public String signup(@RequestBody MyUser user) {

        if((studentRepository.findByStudentWorkEmail(user.getUsername())) != null){
            //Student Mentor exists
            if((studentMentorAccountRepository.findByStudentMentorAccountWorkEmail(user.getUsername())) == null){
                //Student Mentor account not exists.
                //Create one.
                StudentMentorAccount studentMentorAccount = new StudentMentorAccount();
                studentMentorAccount.setStudentMentorAccountWorkEmail(user.getUsername());
                studentMentorAccount.setStudentMentorAccountSubscription_Monthly(0);//monthly sub disabled initially
                studentMentorAccount.setClientCollege(studentService.getStudentByWorkEmail(user.getUsername()).getStudentCollege().getCollegeName());
                studentMentorAccount.setStudentMentorAccountZoomSessionCount(0L);
                studentMentorAccount.setStudentMentorAccountOfflineSessionCount(0L);
                studentMentorAccountRepository.save(studentMentorAccount);
            } else {
                //Student Mentor account also exists
                //Student is trying to sign up twice - it can't because MyUser Entity will store only unique emails.
            }

        } else {
            //Client
            if((clientAccountRepository.findByClientAccountEmail(user.getUsername())) == null){
                //Client account not exists.
                //Create one
                ClientAccount clientAccount = new ClientAccount();
                clientAccount.setClientAccountSubscription_Monthly(0);//monthly sub disabled initially
                clientAccount.setClientAccountEmail(user.getUsername());
                clientAccount.setClientAccountZoomSessionCount(0L);
                clientAccount.setClientAccountOfflineSessionCount(0L);
                clientAccountRepository.save(clientAccount);
            } else{
                //Client Account already exists.
                //Client is trying to sign up twice - it can't because MyUser Entity will store only unique emails.
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        myUserRepository.save(user); //It will only store unique emails.
        //Those who are double signing up - to create both accounts maybe - won't be able to create.
        return "User registered successfully";
    }

    @PostMapping("/login")
    @Transactional
    public String login(@RequestBody MyUser user) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Invalid username or password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return jwt;
    }

//    @GetMapping("/user")
//    public String user() {
//        return "Hello, authenticated user!";
//    }

    @GetMapping("/check-login")
    public ResponseEntity<?> checkLogin(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Check if the token is blacklisted
            if (tokenBlacklistService.isTokenBlacklisted(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Check token validity
            String username = jwtUtil.extractUsername(token);
            if (username != null && jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(username))) {
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/logout")
    @Transactional
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.blacklistToken(token);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}