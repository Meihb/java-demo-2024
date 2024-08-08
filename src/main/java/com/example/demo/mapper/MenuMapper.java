package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    @Select("select * from menus where ${column} = #{value}")
    Menu getMenuByColumn(@Param("column") String column, @Param("value") String value);

    /**
     * 获取全部菜单, 包含子菜单
     * @return
     */
    List<Menu> getMenusWithChild();
}
