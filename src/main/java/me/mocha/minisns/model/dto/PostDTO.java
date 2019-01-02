package me.mocha.minisns.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
public class PostDTO {

    @Getter
    private long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String content;

    @Getter
    private long views;

    @Getter
    private String username;

    public PostDTO addViews(int i) {
        this.views += i;
        return this;
    }

}
