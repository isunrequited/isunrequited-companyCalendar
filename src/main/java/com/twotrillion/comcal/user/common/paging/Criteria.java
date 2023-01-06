package com.twotrillion.comcal.user.common.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
    private int pageNum;		// 현재 페이지
    private int amount;			// 한페이지당 출력되는 아이템 수
    private int skip;			// skip 할 아이템 수

    public Criteria() {
        this(1, 20);
        this.skip = 0;
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
        this.skip = (pageNum - 1) * amount;
    }
}
