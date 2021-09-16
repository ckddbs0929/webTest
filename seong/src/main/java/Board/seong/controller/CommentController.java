package Board.seong.controller;

import Board.seong.domain.CommentDTO;
import Board.seong.service.CommentService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/comments/{boardIdx}")
    public JsonObject getCommentList(@PathVariable("boardIdx")Long boardIdx, @ModelAttribute("params")CommentDTO params){
        JsonObject jsonObject = new JsonObject();

        List<CommentDTO> commentList = commentService.getCommentList(params);
        if(CollectionUtils.isEmpty(commentList)==false){
            JsonArray jsonArray = new Gson().toJsonTree(commentList).getAsJsonArray();
            jsonObject.add("commentList",jsonArray);
        }
        return jsonObject;
    }
}
