package io.hahnsoftware.mapper;

import io.hahnsoftware.dto.response.CommentDTO;
import io.hahnsoftware.entity.Comment;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {
    public static List<CommentDTO> commentToCommentDTO(List<Comment> comments) {
        List<CommentDTO> commentDTOS=new ArrayList<>();
        for (Comment comment:comments){
            commentDTOS.add(commentToCommentDTO(comment));
        }
        return commentDTOS;
    }

    public static CommentDTO commentToCommentDTO(Comment comment){
        CommentDTO commentDTO=new CommentDTO();
        BeanUtils.copyProperties(comment,commentDTO);
        return commentDTO;
    }
}
