package com.almasgali.news.service;

import com.almasgali.news.repository.ArticleRepository;
import com.almasgali.news.repository.CommentRepository;
import com.almasgali.news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(@Autowired ArticleRepository articleRepository,
                          @Autowired UserRepository userRepository,
                          @Autowired CommentRepository commentRepository) {
//        this.articleRepository = articleRepository;
//        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }


}
