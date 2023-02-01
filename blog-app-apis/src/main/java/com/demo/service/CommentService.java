package com.demo.service;

import com.demo.payloads.CommentDto;

public interface CommentService {

	// create comment
	CommentDto createComment(CommentDto commentDto, Integer postId);
	
	
	//delete comment
	void deleteComment(Integer commentId);
}
