package com.hzero.order.app.assembler;

import java.util.Objects;

import com.hzero.order.api.dto.OrderDTO;
import com.hzero.order.domain.entity.HodrItem;
import com.hzero.order.domain.repository.HodrItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author wanpeng.rong@hand-china.com 2020/08/03 9:32
 */
@Component
public class TestAssembler {
    private final HodrItemRepository hodrItemRepository;

    public TestAssembler(HodrItemRepository hodrItemRepository) {
        this.hodrItemRepository = hodrItemRepository;
    }

    /**
     *
     * Assembler：DTO组装器，XxxAssembler.java，复杂DTO的组装，简单的直接使用Entity即可
     * 测试Assembler，将vo层的对象整合为DTO，或者将DTO对象补全
     *
     * @param current
     * @author shengzhou.kong@hand-china.com 2020-08-03 9:42
     * @return com.hzero.order.api.dto.OrderDTO
     */
    public OrderDTO assemblerParentInfo(OrderDTO current) {
        if (Objects.nonNull(current.getOrderNumber())
                && !"".equals(current.getOrderNumber())) {
            HodrItem parent = hodrItemRepository.selectByPrimaryKey(current.getItemId());
            current.setItemCode(parent.getItemCode());
            current.setItemDescription(parent.getItemDescription());
            current.setItemCodeMeaning(parent.getItemCodeMeaning());
        }
        return current;
    }

}
