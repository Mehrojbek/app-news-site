package uz.pdp.appnewssite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewssite.aop.CheckPermission;
import uz.pdp.appnewssite.entity.Comment;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.CommentDto;
import uz.pdp.appnewssite.repository.CommentRepository;
import uz.pdp.appnewssite.service.CommentService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentService commentService;



    @GetMapping
    public HttpEntity<?> getAll(){
        List<Comment> comments = commentService.getAll();
        return ResponseEntity.ok(comments);
    }



    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Long id){
        ApiResponse apiResponse = commentService.getOne(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }




    @CheckPermission(value = "ADD_COMMENT")
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody CommentDto commentDto){
        ApiResponse apiResponse = commentService.add(commentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }




    @CheckPermission(value = "EDIT_COMMENT")
    @PutMapping("/{id}")
    public HttpEntity<?> add(@PathVariable Long id,
                             @Valid @RequestBody CommentDto commentDto){
        ApiResponse apiResponse = commentService.edit(id, commentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }




    @CheckPermission(value = "DELETE_MY_COMMENT")
    @DeleteMapping("/deleteMyComment/{id}")
    public HttpEntity<?> deleteMyComment(@PathVariable Long id){
        ApiResponse apiResponse = commentService.deleteMyComment(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }



    @CheckPermission(value = "DELETE_COMMENT")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id){
        ApiResponse apiResponse = commentService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


}
