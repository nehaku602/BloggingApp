package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.demo.entities.Category;
import com.demo.entities.Post;
import com.demo.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);
	
}
