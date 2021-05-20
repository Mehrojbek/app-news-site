package uz.pdp.appnewssite.payload;

import lombok.Data;

@Data
public class CommentDto {
    private String text;
    private Long postId;
}
