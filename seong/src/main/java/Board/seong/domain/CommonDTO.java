package Board.seong.domain;

import Board.seong.paging.Criteria;
import Board.seong.paging.PaginationInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//서비스에서 페이징 정보를 계산할 수 있도록 만든 클래스
@Getter
@Setter
public class CommonDTO extends Criteria {

    //페이징 정보
    private PaginationInfo paginationInfo;

    //기존 BoardDTO에서의 변수들을 해당클래스에 정의하고 BoardDTO클래스가 상속받는 형태로 바꿈

    // 삭제여부
    private String deleteYn;

    // 등록일
    private LocalDateTime insertTime;

    // 수정일
    private LocalDateTime updateTime;

    // 삭제일
    private LocalDateTime deleteTime;


}
