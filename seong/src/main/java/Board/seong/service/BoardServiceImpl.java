package Board.seong.service;

import Board.seong.domain.AttachDTO;
import Board.seong.domain.BoardDTO;
import Board.seong.mapper.AttachMapper;
import Board.seong.mapper.BoardMapper;
import Board.seong.paging.Criteria;
import Board.seong.paging.PaginationInfo;
import Board.seong.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private AttachMapper attachMapper;

    @Autowired
    private FileUtils fileUtils;

   @Override
   //  insert와 update 둘다 데이터를 등록하는 행위기 때문에
   // 게시글 번호의 유무를 기준으로 insert or update로 실행
    public boolean registerBoard(BoardDTO params){
        int queryResult = 0;
         if(params.getIdx() == null){
             queryResult = boardMapper.insertBoard(params);
         }
         else{
             queryResult = boardMapper.updateBoard(params);
         }
         return (queryResult == 1) ? true : false;
    }

    @Override
    public boolean registerBoard(BoardDTO params, MultipartFile[] files){
       int queryResult =1;
       if(registerBoard(params) == false){
           return false;
       }
       List<AttachDTO> fileList = fileUtils.uploadFiles(files, params.getIdx());
       if(CollectionUtils.isEmpty(fileList) == false){
           queryResult = attachMapper.insertAttach(fileList);
           if(queryResult<1){
               queryResult = 0;
           }
       }
       return (queryResult>0);
    }


    @Override
    public BoardDTO getBoardDetail(Long idx){
       return boardMapper.selectBoardDetail(idx);
    }


    @Override
    public boolean deleteBoard(Long idx){
        int queryResult = 0;

        BoardDTO board = boardMapper.selectBoardDetail(idx);
        if(board != null && "N".equals(board.getDeleteYn())){
            queryResult = boardMapper.deleteBoard(idx);
        }
        return (queryResult ==1) ? true : false;
    }


    @Override
    public List<BoardDTO> getBoardList(BoardDTO params) {
        List<BoardDTO> boardList = Collections.emptyList();

        int boardTotalCount = boardMapper.selectBoardTotalCount(params);

        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(boardTotalCount);

        params.setPaginationInfo(paginationInfo);

        if (boardTotalCount > 0) {
            boardList = boardMapper.selectBoardList(params);
        }

        return boardList;
    }
}
