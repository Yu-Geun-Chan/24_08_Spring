package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ReactionPointRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;

@Service
public class ReactionPointService {

    @Autowired
    private ReactionPointRepository reactionPointRepository;

    public int getReactionPointCount(String relTypeCode, int relId, int point) {
        return reactionPointRepository.getReactionPointCount(relTypeCode, relId, point);
    }

    public void setReactionPoint(int memberId, String relTypeCode, int relId, int point) {
    	reactionPointRepository.setReactionPoint(memberId, relTypeCode, relId, point);
    }
}