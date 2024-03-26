package com.spring_sec.spring_jwt.controllers;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring_sec.spring_jwt.model.Post;
import com.spring_sec.spring_jwt.model.PostType;
import com.spring_sec.spring_jwt.model.Profile;
import com.spring_sec.spring_jwt.util.security.services.PostService;
import com.spring_sec.spring_jwt.util.security.services.ProfileService;

import org.springframework.http.MediaType;



@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private ProfileService profileService;

    
    //aggiungere postbyprofile
    @GetMapping(path= "/post/profile/{id}")
       public ResponseEntity<?> findByUser(@PathVariable(required = true) Long id){
        Optional<Profile> profile = profileService.findById(id);
        if (profile.isPresent()) {
            List<Post> posts = postService.findByProfile(profile.get());
            if (!posts.isEmpty()) {
                return ResponseEntity.ok(posts);
            } else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Posts not found");
            }
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found");
        }
    }

    @GetMapping(path = "/post")
    public ResponseEntity<Page<Post>> findAll(Pageable pageable) {
        Page<Post> findAll = postService.findAll(pageable);

        if (findAll.hasContent()) {
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/post/{id}")
    public ResponseEntity<Post> findById(@PathVariable(required = true) Long id) {
        Optional<Post> post = postService.findById(id);
        if (post.isPresent()) {
            return new ResponseEntity<>(post.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/post/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Post> save(
            @PathVariable(required = true) Long id,
            @RequestPart("title") String title,
            @RequestPart("description") String description,
            @RequestPart("file") MultipartFile file) {

        
        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        post.setLikes(0);
        post.setCreatedAt(LocalDateTime.now());
        post.setType(PostType.SINGLE);
        post.setProfile(profileService.findById(id).get());
            

        if (file != null && !file.isEmpty()) {
            try {
                String fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + file.getOriginalFilename());
                String rootPath = System.getProperty("user.dir");
                String filePath = rootPath + File.separator + "src/main/resources/static/uploads";
                File uploadPath = new File(filePath);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }
                file.transferTo(new File(uploadPath, fileName));

                String fileUrl = "/uploads/" + fileName;
                post.setUrl(fileUrl);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Post savedPost = postService.save(post);
        return new ResponseEntity<>(savedPost, HttpStatus.OK);

    }

    @PatchMapping(path = "/post/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Post> update(
            @PathVariable(required = true) Long id,
            @RequestPart(value = "title", required = false) String title,
            @RequestPart(value = "description", required = false) String description,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        Optional<Post> postResult = postService.findById(id);

        if (postResult.isPresent()) {
            Post post = postResult.get();
            System.out.println(post);

            if (title != null) {
                post.setTitle(title);
            }

            if (description != null) {
                post.setDescription(description);
            }

            if (file != null && !file.isEmpty()) {
                try {
                    String fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + file.getOriginalFilename());
                    String rootPath = System.getProperty("user.dir");
                    String filePath = rootPath + File.separator + "src/main/resources/static/uploads";
                    File uploadPath = new File(filePath);
                    if (!uploadPath.exists()) {
                        uploadPath.mkdirs();
                    }
                    file.transferTo(new File(uploadPath, fileName));

                    String fileUrl = "/uploads/" + fileName;
                    post.setUrl(fileUrl);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                // If no file is provided, keep the existing URL
                post.setUrl(post.getUrl());
            }

            Post postUpdate = postService.update(id, post);
            return new ResponseEntity<>(postUpdate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/post/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        postService.delete(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
