package com.guidebook.GuideBook.COEPTECH.Services;

import com.guidebook.GuideBook.COEPTECH.Models.Reply;
import com.guidebook.GuideBook.COEPTECH.Repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public Optional<Reply> getReplyByReplyId(Long replyId) {
        return replyRepository.findById(replyId);
    }

    public Reply saveReply(Long commentId, Long parentReplyId, String text) {
        Reply reply = new Reply();
        reply.setText(text);

        if (parentReplyId != null) {
            Reply parentReply = replyRepository.findById(parentReplyId)
                    .orElseThrow(() -> new RuntimeException("Parent reply not found"));
            reply.setParentReply(parentReply);
        }

        return replyRepository.save(reply);
    }

    public Reply saveReply(Reply reply){
        return replyRepository.save(reply);
    }
}

