package Board.seong.mapper;

import Board.seong.domain.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    public int insertComment(CommentDTO params);
    public CommentDTO selectCommentDetail(Long idx);
    public int updateComment(CommentDTO params);
    public int deleteComment(Long idx);
    // 댓글 목록 조회 메서드
    public List<CommentDTO> selectCommentList(CommentDTO params);
    public int selectCommentTotalCount(CommentDTO params);
}
