package com.example.demo.service.impl;

import com.example.demo.entity.Area;
import com.example.demo.mapper.AreaMapper;
import com.example.demo.service.IAreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.executor.BatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author meihaibo
 * @since 2024-08-08
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

}
