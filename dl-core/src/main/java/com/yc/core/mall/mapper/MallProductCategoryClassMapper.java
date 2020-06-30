package com.yc.core.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.core.mall.entity.MallProductCategory;
import com.yc.core.tree.TreeNode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * 功能描述:
 *
 * @Author:  xieyc && 紫色年华
 * @Date 2020-05-08
 * @Version: 1.0.0
 */
@Repository
public interface MallProductCategoryClassMapper extends BaseMapper<MallProductCategory> {

    /**
     * 类目树
     * @return tree
     */
    List<TreeNode> classTree();

    /**
     * 子级类目
     * @param page 分页信息
     * @param parentId 父级ID
     * @return page
     */
    Page<MallProductCategory> childrenClass(@Param("page") Page<MallProductCategory> page, @Param("parentId") String parentId);

}
