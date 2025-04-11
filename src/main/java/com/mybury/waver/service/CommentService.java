package com.mybury.waver.service;

import com.mybury.waver.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void test() {
        commentRepository.findAll();
    }


}
