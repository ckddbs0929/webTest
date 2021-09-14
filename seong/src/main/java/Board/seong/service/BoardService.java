package Board.seong.service;

import Board.seong.domain.BoardDTO;
import Board.seong.paging.Criteria;

import java.util.List;

public interface BoardService {
    public boolean registerBoard(BoardDTO params);

    public BoardDTO getBoardDetail(Long idx);

    public boolean deleteBoard(Long idx);

    public List<BoardDTO> getBoardList(BoardDTO params);
}
