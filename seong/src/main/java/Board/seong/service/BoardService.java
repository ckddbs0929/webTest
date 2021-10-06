package Board.seong.service;

import Board.seong.domain.AttachDTO;
import Board.seong.domain.BoardDTO;
import Board.seong.paging.Criteria;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    public boolean registerBoard(BoardDTO params);

    // 파일 업로드용 객체
    public boolean registerBoard(BoardDTO params, MultipartFile[] files);

    public BoardDTO getBoardDetail(Long idx);

    public boolean deleteBoard(Long idx);

    public List<BoardDTO> getBoardList(BoardDTO params);

    public List<AttachDTO> getAttachFileList(Long boardIdx);

    public AttachDTO getAttachDetail(Long idx);
}
