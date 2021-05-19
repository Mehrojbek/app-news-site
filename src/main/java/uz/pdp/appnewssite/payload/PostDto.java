package uz.pdp.appnewssite.payload;

import lombok.Data;


import javax.validation.constraints.NotNull;

@Data
public class PostDto {
    @NotNull
    private String title;

    @NotNull
    private String text;

    @NotNull
    private String url;
}
