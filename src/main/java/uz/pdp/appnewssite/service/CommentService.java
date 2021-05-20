package uz.pdp.appnewssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.Comment;
import uz.pdp.appnewssite.entity.Post;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.CommentDto;
import uz.pdp.appnewssite.repository.CommentRepository;
import uz.pdp.appnewssite.repository.PostRepository;

import java.util.*;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;


    //GET_ALL
    public List<Comment> getAll(){
       return commentRepository.findAll();
    }


    //GET_ONE
    public ApiResponse getOne(Long id){
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.map(comment -> new ApiResponse("ok", true, comment)).orElseGet(() -> new ApiResponse("comment topilmadi", false));
    }


    //ADD
    public ApiResponse add(CommentDto commentDto){
        Optional<Post> optionalPost = postRepository.findById(commentDto.getPostId());
        if (!optionalPost.isPresent())
            return new ApiResponse("post topilmadi",false);
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setPost(optionalPost.get());
        commentRepository.save(comment);
        return new ApiResponse("comment saqlandi",true);
    }



    //EDIT
    public ApiResponse edit(Long id, CommentDto commentDto){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Comment> optionalComment = commentRepository.findById(id);

        //COMMENT EGASI SHU USER MI
        if (!optionalComment.isPresent())
            return new ApiResponse("comment topilmadi",false);
        Comment editingComment = optionalComment.get();

        if (editingComment.getCreatedBy().equals(user)) {

            editingComment.setText(commentDto.getText());
            commentRepository.save(editingComment);
            return new ApiResponse("comment tahrirlandi", true);
        }

        return new ApiResponse("xatolik",false);
    }



    //DELETE
    public ApiResponse delete(Long id){
        try {
        commentRepository.deleteById(id);
        return new ApiResponse("comment uchirildi",true);
        }catch (Exception e){
            return new ApiResponse("comment uchirilmadi",false);
        }
    }



    //DELETE_MY_COMMENT
    public ApiResponse deleteMyComment(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (!optionalComment.isPresent())
            return new ApiResponse("comment topilmadi",false);
        Comment comment = optionalComment.get();
        if (user.equals(comment.getCreatedBy())){
            try {
            commentRepository.delete(comment);
            return new ApiResponse("comment uchirildi",true);
            }catch (Exception e){
                return new ApiResponse("comment uchirilmadi",false);
            }
        }
        return new ApiResponse("xatolik",false);
    }
}
