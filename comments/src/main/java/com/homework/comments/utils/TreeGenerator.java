package com.homework.comments.utils;

import com.homework.comments.domain.Comment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 生成树形结构集合
 *           实现逻辑：第一次遍历，把相同父id的分组成各个子集合；
 *                    第二次遍历，按父id从0级自顶向下，利用递归函数分次便利，根据id获取子集合。
 *                    因为每次递归都是遍历剩下的集合，所以实际只遍历了一次，时间复杂度为O(n)
 */
public class TreeGenerator {


    public List<Comment> createTree(Collection<Comment> treeNodes){
        //把相同父id的分组
        Map<Number, List<Comment>> collect = treeNodes.stream().collect(Collectors.groupingBy(Comment::getCommentPid));

        List<Comment> treeNodeList = new ArrayList<>();
        List<Comment> list = collect.get(0);//获取顶级节点集合

        if (list!=null){
            for (Comment t : list) {
                treeNodeList.add(addChildNode(t, collect));
            }
        }
        return treeNodeList;
    }

    /**
     * @param treeNode
     * @param collect
     * @return 递归，根据每层节点id，获取该节点的下一层节点
     */
    private Comment addChildNode(Comment treeNode, Map<Number, List<Comment>> collect){
        List<Comment> list = collect.get(treeNode.getCommentId());//获取下一层节点集合
        if (list!=null){
            List<Comment> treeNodeList = new ArrayList<>();
            for (Comment t : list) {
                treeNodeList.add(addChildNode(t, collect));
            }
            treeNode.setChildren(treeNodeList);
        }
        return treeNode;
    }

}
