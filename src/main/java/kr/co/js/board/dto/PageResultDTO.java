package kr.co.js.board.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
//DTO 클래스와 Entity 클래스를 Generic으로 설정
public class PageResultDTO <DTO,EN>{
    private List<DTO> dtoList;

    private int totalPage, start, end;
    private int page;
    private int size;

    private boolean prev, next;

    private List<Integer> pageList;

    //생성자
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){
        //Entity로 넘어온 결과를 DTO로 변환
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        //전체 페이지 개수 구하기
        totalPage = result.getTotalPages();
        //페이지 번호 목록 구하기
        makePageList(result.getPageable());
    }

    //페이지번호 목록을 만들어 주는 메서드
    private void makePageList(Pageable pageable){
        //현재 페이지
        this.page = pageable.getPageNumber() +1;
        //페이지당 출력되는 데이터 개수
        this.size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(page/10.0))*10;
        start = tempEnd - 9;
        prev = start > 1;

        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage> tempEnd;

        pageList = new ArrayList<>();
        for(int i = start; i <= end; i=i+1){
            pageList.add(i);
        }

    }







}
