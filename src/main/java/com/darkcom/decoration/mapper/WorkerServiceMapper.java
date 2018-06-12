package com.darkcom.decoration.mapper;

import com.darkcom.decoration.model.WorkerService;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkerServiceMapper {
    int deleteByPrimaryKey(Long serviceId);

    int insert(WorkerService record);

    int insertSelective(WorkerService record);

    WorkerService selectByPrimaryKey(Long serviceId);

    int updateByPrimaryKeySelective(WorkerService record);

    int updateByPrimaryKey(WorkerService record);

    List<WorkerService> getServiceList(Integer serviceType);
}