package uz.pdp.appnewssite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewssite.aop.CheckPermission;
import uz.pdp.appnewssite.entity.Post;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.PostDto;
import uz.pdp.appnewssite.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    final
    PostService postService;

    public PostController(@Lazy PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public HttpEntity<?> getAll(){
        List<Post> posts = postService.getAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Long id){
        Post post = postService.getOne(id);
        return ResponseEntity.status(post != null?200:404).body(post);
    }


    @CheckPermission(value = "ADD_POST")
    @PostMapping
    public HttpEntity<?> add(@RequestBody @Valid PostDto postDto){
        ApiResponse apiResponse = postService.add(postDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @CheckPermission(value = "EDIT_POST")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Long id,
                              @RequestBody @Valid PostDto postDto){
        ApiResponse apiResponse = postService.edit(id, postDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @CheckPermission(value = "DELETE_POST")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id){
        ApiResponse apiResponse = postService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}
