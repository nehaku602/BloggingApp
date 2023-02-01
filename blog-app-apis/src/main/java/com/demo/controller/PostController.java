package com.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.config.AppConstants;
import com.demo.payloads.ApiResponse;
import com.demo.payloads.PostDto;
import com.demo.payloads.PostResponse;
import com.demo.service.FileService;
import com.demo.service.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	//create post
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
		
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}
	
	
	//get all post of single user by user id
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>>getPostsByUser(@PathVariable Integer userId){
		
		List<PostDto> posts = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
		
	}
	
   //get all posts of single category by category id
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>>getPostsByCategory(@PathVariable Integer categoryId){
		
		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
		
	}
	
	// get all posts
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = AppConstants.PAGE_NUMBER, defaultValue = "0", required = false) Integer pageNumber, 
			@RequestParam(value =  AppConstants.PAGE_SIZE, defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = AppConstants.SORT_BY , defaultValue = "postId" , required = false) String sortBy,
			@RequestParam(value = AppConstants.SORT_BY_DIRECTION , defaultValue = "asc", required = false) String sortByDirection){
		
		PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortByDirection);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	
	//get post details by 
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
	
	
	// delete post
	
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		
		this.postService.deletePost(postId);
		return new ApiResponse("Post is successfully delete !", true);
	}
	
	
	// update post
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
				
		  PostDto updatePost = this.postService.updatePost(postDto,  postId);
		  return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	
	//search post
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
		
		List<PostDto> result = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
	}
	
	
	//post image upload
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException{
		
		PostDto postDto = this.postService.getPostById(postId);
		
		String fileName = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
		
	}
	
	//method to serve files
	
	@GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException{
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
