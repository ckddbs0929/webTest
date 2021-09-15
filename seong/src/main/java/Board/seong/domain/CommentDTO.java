package Board.seong.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO extends CommonDTO {
    // 번호
    private Long idx;
    // 게시글 번호
    private Long boardIdx;
    // 댓글 내용
    private String content;
    // 댓글 작성자
    private String writer;

}
