import request from '@/utils/request'


const loginUser = (data) => request(
    {
        url:'/CommentsTree/userLogin',
        method:'post',
        data:data

    }
)

const register = data => request(
    {
        url:'/CommentsTree/userRegister',
        method:'post',
        data:data
    }
)


const getAllComments = () => request(
    {
        url:'/CommentsTree/comments',
    }
)

const remark = (data) => request(
    {
        url:'/CommentsTree/comment',
        method:'post',
        data:data
    }
)
const getUserInfo = () => request(
    {
        url:'/CommentsTree/user',
    }
)

const getBizApp = () => request(
    {
        url:'/tBizApp/getAllBizApp'
    }
)

const getParams = appType => request(
    {
        url:`/TBizAppraisalArgument/getParamsNameByType/${appType}`
    }
)

const getRelatedOptions = appType => request(
    {
        url:`/TBizAppraisalArgumentOption/getRelatedOptions/${appType}`
    }
)

export default {
    getBizApp,
    getParams,
    getRelated: getRelatedOptions,
    loginUser,
    register,
    getUserInfo,
    getAllComments,
    remark,
}
