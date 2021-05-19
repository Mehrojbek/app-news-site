package uz.pdp.appnewssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.Post;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.PostDto;
import uz.pdp.appnewssite.repository.PostRepository;

import java.util.*;

@Service
public class PostService {
    final
    PostRepository postRepository;

    public PostService(@Lazy PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAll(){
        return postRepository.findAll();
    }


    public Post getOne(Long id){
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }


    public ApiResponse add(PostDto postDto){
        boolean exists = postRepository.existsByUrl(postDto.getUrl());
        if (exists)
            return new ApiResponse("Bunday url bilan post yaratilgan",false);

        Post post = new Post(
                postDto.getTitle(),
                postDto.getText(),
                postDto.getUrl()
        );

        postRepository.save(post);
        return new ApiResponse("Post yaratildi",true);
    }


    public ApiResponse edit(Long id, PostDto postDto){
        boolean exists = postRepository.existsByUrlAndIdNot(postDto.getUrl(), id);
        if (exists)
            return new ApiResponse("Bunday url bilan post yaratilgan",false);

        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent())
            return new ApiResponse("Post topilmadi",false);
        Post editingPost = optionalPost.get();

        editingPost.setTitle(postDto.getTitle());
        editingPost.setText(postDto.getText());
        editingPost.setUrl(postDto.getUrl());

        postRepository.save(editingPost);

        return new ApiResponse("post tahrirlandi",true);
    }


    public ApiResponse delete(Long id){
        try {
            postRepository.deleteById(id);
            return new ApiResponse("post uchirildi",true);
        }catch (Exception e){
            return new ApiResponse("post uchirilmadi",false);
        }
    }
}
