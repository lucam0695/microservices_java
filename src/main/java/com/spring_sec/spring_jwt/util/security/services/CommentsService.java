package com.spring_sec.spring_jwt.util.security.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring_sec.spring_jwt.model.Comments;
import com.spring_sec.spring_jwt.repository.CommentsRepository;



@Service
public class CommentsService {
    @Autowired
    CommentsRepository commentsRepository;

    public Page<Comments> findAll(Pageable pageable){
        return commentsRepository.findAll(pageable);
    }

    public Optional<Comments> findById(Long id) {
		return commentsRepository.findById(id);
	}

    public Comments save(Comments comment) {
		return commentsRepository.save(comment);
	}

    public Comments update(Long id, Comments comment) {
		Optional<Comments> commentResult = commentsRepository.findById(id);

		if (commentResult.isPresent()) {
			Comments commentUpdate = commentResult.get();
            commentUpdate.setText(comment.getText());
			return commentUpdate;
		} else {
			throw new Error("Comment has no uptated");
		}

	}

    public void delete(Long id) {
		commentsRepository.deleteById(id);
	}

}