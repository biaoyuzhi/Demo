package com.example.demo.mapper;

import com.example.demo.pojo.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author wuzhihai
 */
@Mapper
public interface PersonMapper {

    @Update("UPDATE person SET name = #{name} WHERE id = #{id}")
    void updateNameById(@Param("id") Long id,
                        @Param("name") String name);

    @Select("SELECT * FROM person WHERE id = #{id}")
    Person selectById(Long id);
}
