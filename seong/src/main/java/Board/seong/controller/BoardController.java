package Board.seong.controller;

import Board.seong.domain.BoardDTO;
import Board.seong.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping(value = "/board/write.do")
    //openBoardWrite 메서드의 파라미터인 Model은 데이터를 뷰로 전달하는데 사용
    public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model){

        if(idx == null){
            model.addAttribute("board", new BoardDTO());
        }
        else{
            BoardDTO board = boardService.getBoardDetail(idx);
            if(board == null){
                return "redirect:/board/list.do";
            }
            model.addAttribute("board",board);
        }

        return "board/write";
    }

    @PostMapping(value = "/board/register.do")
    public String registerBoard(final BoardDTO params){
        try {
            boolean isRegistered = boardService.registerBoard(params);
            if(isRegistered == false){
                // 게시글 등록에 실패했다는 메시지를 전달
            }
        } catch (DataAccessException e) {
            //데이터베이스 처리과정에 문제가 생겼다는 메시지를 전달
        }
          catch (Exception e){
            // 시스템에 문제가 발생했다는 메시지를 전달
          }
        return "redirect:/board/list.do";
    }
}
