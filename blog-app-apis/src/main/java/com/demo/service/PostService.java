package com.demo.service;

import java.util.List;

import com.demo.payloads.PostDto;
import com.demo.payloads.PostResponse;



public interface PostService {

	
	//create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	
	//delete
	void deletePost(Integer postId);
	
	
	//get all posts
	PostResponse  getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortByDirection);
	
	
	//get single post
	PostDto getPostById(Integer postId);
	
	
	//get all post by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	
	//get all posts by user
	List<PostDto> getPostByUser(Integer userId);
	
	
	//search posts
	List<PostDto> searchPosts(String keyword);
	
	
}
