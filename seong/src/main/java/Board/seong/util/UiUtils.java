package Board.seong.util;

import Board.seong.constant.Method;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class UiUtils {

    public String showMessageWithRedirect(@RequestParam(value = "message", required = false) String message,
                                          @RequestParam(value = "redirectUri", required = false) String redirectUri,
                                          @RequestParam(value = "method", required = false) Method method,
                                          @RequestParam(value = "params", required = false) Map<String, Object> params, Model model) {

        //사용자에서 전달할 메시지
        model.addAttribute("message", message);
        // 리다이렉트할 url
        model.addAttribute("redirectUri", redirectUri);
        //constant 패키지에 있는 method enum 클래스에 선언한 http 요청 메소드
        model.addAttribute("method", method);
        //화면으로 전달할 파라미터 -> 페이지마다 파라미터 갯수가 달라질수 있으므로 (key,value형태로 담을수 있는 map을 사용
        model.addAttribute("params", params);

        return "utils/message-redirect";
    }

}