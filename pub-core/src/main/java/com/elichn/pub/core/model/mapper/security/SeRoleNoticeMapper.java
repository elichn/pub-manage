package com.elichn.pub.core.model.mapper.security;

import com.elichn.pub.core.model.pojo.security.SeRoleNotice;
import com.elichn.pub.core.model.pojo.security.SeRoleNoticeExample;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface SeRoleNoticeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_role_notice
     *
     * @mbggenerated Sun Oct 29 22:40:20 CST 2017
     */
    int countByExample(SeRoleNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_role_notice
     *
     * @mbggenerated Sun Oct 29 22:40:20 CST 2017
     */
    int deleteByExample(SeRoleNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_role_notice
     *
     * @mbggenerated Sun Oct 29 22:40:20 CST 2017
     */
    @Delete({
        "delete from se_role_notice",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_role_notice
     *
     * @mbggenerated Sun Oct 29 22:40:20 CST 2017
     */
    @Insert({
        "insert into se_role_notice (role_id, notice_id)",
        "values (#{roleId,jdbcType=INTEGER}, #{noticeId,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(SeRoleNotice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_role_notice
     *
     * @mbggenerated Sun Oct 29 22:40:20 CST 2017
     */
    int insertSelective(SeRoleNotice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_role_notice
     *
     * @mbggenerated Sun Oct 29 22:40:20 CST 2017
     */
    List<SeRoleNotice> selectByExample(SeRoleNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_role_notice
     *
     * @mbggenerated Sun Oct 29 22:40:20 CST 2017
     */
    @Select({
        "select",
        "id, role_id, notice_id",
        "from se_role_notice",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    SeRoleNotice selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_role_notice
     *
     * @mbggenerated Sun Oct 29 22:40:20 CST 2017
     */
    int updateByExampleSelective(@Param("record") SeRoleNotice record, @Param("example") SeRoleNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_role_notice
     *
     * @mbggenerated Sun Oct 29 22:40:20 CST 2017
     */
    int updateByExample(@Param("record") SeRoleNotice record, @Param("example") SeRoleNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_role_notice
     *
     * @mbggenerated Sun Oct 29 22:40:20 CST 2017
     */
    int updateByPrimaryKeySelective(SeRoleNotice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_role_notice
     *
     * @mbggenerated Sun Oct 29 22:40:20 CST 2017
     */
    @Update({
        "update se_role_notice",
        "set role_id = #{roleId,jdbcType=INTEGER},",
          "notice_id = #{noticeId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(SeRoleNotice record);
}