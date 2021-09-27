package Board.seong.controller;

import Board.seong.constant.Method;
import Board.seong.domain.BoardDTO;
import Board.seong.paging.Criteria;
import Board.seong.service.BoardService;
import Board.seong.util.UiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
public class BoardController extends UiUtils {

    @Autowired
    private BoardService boardService;

    @GetMapping(value = "/board/write.do")
    //openBoardWrite 메서드의 파라미터인 Model은 데이터를 뷰로 전달하는데 사용
    public String openBoardWrite(@ModelAttribute("params")BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model){

        if(idx == null){
            model.addAttribute("board", new BoardDTO());
        }
        else{
            BoardDTO board = boardService.getBoardDetail(idx);
            if(board == null || "Y".equals(board.getDeleteYn())){
                return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.", "/board/list.do",
                        Method.GET, null, model);
            }
            model.addAttribute("board",board);
        }

        return "board/write";
    }

    @PostMapping(value = "/board/register.do")
    public String registerBoard(final BoardDTO params, final MultipartFile[] files, Model model){
        Map<String, Object> pagingParams = getPagingParams(params);
        try {
            boolean isRegistered = boardService.registerBoard(params, files);
            if(isRegistered == false){
                // 게시글 등록에 실패했다는 메시지를 전달
                return showMessageWithRedirect("게시글 등록에 실패했습니다.", "/board/list.do",
                                                Method.GET, pagingParams, model);
            }
        } catch (DataAccessException e) {
            //데이터베이스 처리과정에 문제가 생겼다는 메시지를 전달
            return showMessageWithRedirect("데이터베이스 처리과정에 문제가 생겼습니다.", "/board/list.do",
                                            Method.GET, pagingParams, model);
        }
          catch (Exception e){
            // 시스템에 문제가 발생했다는 메시지를 전달
              return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list.do",
                                            Method.GET, pagingParams, model);
          }
        return showMessageWithRedirect("게시글 등록이 완료되었습니다.", "/board/list.do",
                                    Method.GET, pagingParams, model);
    }

    @GetMapping(value = "/board/list.do")
    public String openBoardList(@ModelAttribute("params")BoardDTO params,Model model){
        List<BoardDTO> boardList = boardService.getBoardList(params);
        model.addAttribute("boardList", boardList);
        return "board/list";
    }

    @GetMapping(value = "/board/view.do")
    public String openBoardDetail(@ModelAttribute("params")BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model){
        if(idx == null){
            // 올바르지 않은 접근이라는 메시지를 전잘 후
            // 게시글 리스트로 리다이렉트하는 return값 부여
            return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/list.do", Method.GET, null, model);
        }
        BoardDTO board = boardService.getBoardDetail(idx);
        if(board == null || "Y".equals(board.getDeleteYn())){
            //없는 게시글이거나 삭제된 게시글이라는 메시지를 전달 후
            // 게시글 리스트로 리다이렉트하는 return값 부여
            return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.","/board/list.do",Method.GET,null,model);

        }
        model.addAttribute("board", board);

        return "board/view";
    }

    @PostMapping(value = "/board/delete.do")
    public String deleteBoard(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "idx", required = false)Long idx, Model model){
        if(idx == null){
            return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/list.do",
                                            Method.GET, null, model);
        }
        Map<String, Object> pagingParams = getPagingParams(params);
        try {
            boolean isDeleted = boardService.deleteBoard(idx);
            if(isDeleted == false){
                // 게시글 삭제 실페 문구기입
                return showMessageWithRedirect("게시글 삭제에 실패하였습니다.", "/board/list.do",
                                                Method.GET, pagingParams, model);
            }
        }
        catch (DataAccessException e){
            //데이터베이스 처리과정 문제발생 메시지 기입
            return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/list.do",
                                            Method.GET, pagingParams, model);
        }
        catch (Exception e) {
            // 시스템 문제 발생 메시지 기입
            return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list.do",
                                            Method.GET, pagingParams, model);
        }
        return showMessageWithRedirect("게시글을 삭제하였습니다.", "/board/list.do",
                Method.GET, pagingParams, model);
    }

}
