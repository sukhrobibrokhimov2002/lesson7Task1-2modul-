package uz.sukhrob.lesson7task1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @NotNull(message = "Post title must not be empty")
    private String title;
    @NotNull(message = "Text title must not be empty")
    private String text;
    @NotNull(message = "Url title must not be empty")
    private String url;
}
