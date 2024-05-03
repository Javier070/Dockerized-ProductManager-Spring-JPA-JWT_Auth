package com.jm.tfg.serviceImpl;

import com.jm.tfg.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public ResponseEntity<String> registro(Map<String, String> requestMap) {
        return null;
    }
}
