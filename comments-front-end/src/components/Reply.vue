<template>
    <div>
        <div class="children" v-for="(ritem,jndex) in replies">
            <div class="content">
<!--				用户名，发表时间-->
                <div  class="comment-f">
                    <div>
                        <div class="nickname author">
                            {{ritem.userName}} @ {{ritem.replyName}}
                        </div>
                        <div class="date">
                            {{ritem.createTime}}
                        </div>
                    </div>
                </div>
<!--                评论内容-->
                <div class="reply-content">
                    <div class="cc" v-html="ritem.content">{{ritem.content}}</div>
                </div>
<!--                回复评论操作区-->
                <div v-if="uName!=null" class="reply-content reply-fa">
                    <div class="reply-font" @click="doReply(ritem.commentId)">
                        <div>
                            <img src="./img/icon/reply.png" class="icon-reply"><span class="icon-reply icon-hf">回复</span>
                        </div>

                    </div>

                    <div class="comment" :style="{width:commentWidth}" v-if="replyMap[ritem.commentId]">
                        <el-input
                                @focus="showButton(ritem.commentId)"
                                type="textarea"
                                :autosize="{ minRows: minRows, maxRows: maxRows}"
                                :placeholder=placeholder
                                v-model="textareaMap[ritem.commentId]">
                        </el-input>

                        <div v-if="buttonMap[ritem.commentId]" class="hbl-owo">
                            <div class="publish publish-btn">
                                <button class="btn" @click="doReplySend(ritem.commentId,ritem.userId,ritem.userName)">发送</button>
                                <button @click="cancel(ritem.commentId)" class="btn btn-cancel">取消</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <Reply title='sonComp'
                   :replies="ritem.chilren"
                   @listenToChild="getDataFromChild"
                   :uName="uName">
            </Reply>

        </div>
    </div>
</template>

<script>
    import api from "@/api/index"
    export default {
        name: "Reply",
        props: {
            replies: Array,
            uName:String,
            emojiWidth:{
                type:String,
                default:'560px'
            },
            placeholder:{
                type:String,
                default:'在此输入回复内容...'
            },
            minRows:{
                type:Number,
                default:4
            },
            maxRows:{
                type:Number,
                default:8
            },
            commentNum:{
                type:Number,
                default:2
            },
            authorId:{
                type:Number,
                default:1
            },
            commentWidth:{
                type:String,
                default:'80%',
            },
        },
        data() {
            return {
                replyMap:[
                ],
                buttonMap:[],
                textareaMap:[],
            }
        },
        mounted () {
        },
        methods:{
            doReply(index){
                this.$set(this.replyMap,index,true)
                // console.log(this.replyMap[index]);
            },
            showButton(index){
                //this.showFlag = true;
                // console.log(index+"index");
                this.$set(this.buttonMap,index,true)
            },
            cancel(index){
                this.$set(this.buttonMap,index,false)
                if(index!==0){
                    this.$set(this.replyMap,index,false)
                }
                // console.log(index+"index");
                //this.showFlag = false;
            },
            async doReplySend(commentPid,replyId,replyName){
                const self=this;
                let comment={}
                comment["content"]=this.textareaMap[commentPid]
                comment["commentPid"]=commentPid
                comment["replyId"]=replyId
                comment["replyName"]=replyName
                let result=await api.remark(comment)
                // console.log(result)
                if (result.data.status==-1){
                    self.errorInfo=true,
                        self.errInfo=result.data.content,
                        self.$message.error(result.data.content)
                }else if (result.data.status==0){
                    self.$message.success("回复成功！")
                    this.$set(this.textareaMap,commentPid,'')
                    this.$emit('listenToChild')
                    this.cancel(commentPid)
                }
            },
            // 父子组件通信的方法
            getDataFromChild () {
                this.$emit('listenToChild')
            }
        }
    }
</script>

<style type="text/css" scoped>
    .btn{
        width: 70px; /* 宽度 */
        margin-top: 3px;
        height: 30px; /* 高度 */
        border-width: 0px; /* 边框宽度 */
        border-radius: 3px; /* 边框半径 */
        background: #3CB371; /* 背景颜色 */
        cursor: pointer; /* 鼠标移入按钮范围时出现手势 */
        outline: none; /* 不显示轮廓线 */
        font-family: Microsoft YaHei; /* 设置字体 */
        color: white; /* 字体颜色 */
        font-size: 13px; /* 字体大小 */
        text-align: center;
        line-height: 30px;
        border-radius:5px;
        display: inline-block;
        margin-left: 5px;
        margin-right: 5px;
    }
    .btn-cancel{
        background:grey; /* 背景颜色 */
    }
    .reply{
        border-top: solid 1px #D9D9D9;
    }
    .children{
        border : solid 1px #D9D9D9;
        padding: 20px;
        padding-left: 40px;
    }
    .cc{
        display: inline-block;
    }
    .date{
        font-size: 12px;
        margin-top: 5px;
        color: grey;
    }
    .reply-content{
        word-wrap : break-word ;
        width: 90%;
        font-size: 15px;
        line-height: 25px;
        margin-left: 56px;
    }

    .reply-fa{
        margin-top: 5px;
    }
    .reply-font{
        margin-bottom: 5px;
        color: grey;
        cursor: pointer;
    }

    .cc-to a{
        text-decoration:none;
        color: #409eff;
    }
    .icon-reply{
        display: inline-block;
        vertical-align: top;

    }

    .icon-hf{
        margin-top: 2px;
    }
    .hbl-child{
        padding: 20px;
    }
</style>