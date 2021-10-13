<template>
	<div class="hbl-fa">
        <div v-if="userName!=null">
            <el-header style="text-align: right;">
                <div class="nickname author">{{userName}}[{{email}}], 你好</div>
                <el-button @click="exit">退出登录</el-button>
            </el-header>
    <!--		留言-->
            <div class="hbl-comm">
                <div class="comment" :style="{width:commentWidth}">
                    <el-input
                      @focus="showButton(0)"
                      type="textarea"
                      :autosize="{ minRows: minRows, maxRows: maxRows}"
                      :placeholder=placeholder
                      v-model="textareaMap[0]">
                    </el-input>

                     <div v-if="buttonMap[0]" class="hbl-owo">
                            <div class="publish publish-btn">
                                  <button class="btn" @click="doSend()">发送</button>
                                  <button @click="cancel(0)" class="btn btn-cancel">取消</button>
                            </div>
                     </div>

                </div>
            </div>
        </div>
        <div v-else>
            <el-header style="text-align: right;">
                <p class="register" @click="gotoLogin()">登录</p>
                <p class="register" @click="gotoRegister()">注册/</p>
            </el-header>
        </div>

		<div class="comm">
			<div class="su com-rep"></div>
			<div class="com-rep com-title">
                评论
<!--                <span class="com-span">({{commentNum}})</span>-->
            </div>
		</div>

<!--        评论列表-->
		<div v-for="(item,index) in commentList" class="hbl-child">
			<div class="reply">
			</div>
			<div class="content">
<!--				用户名，发表时间-->
				<div  class="comment-f">
					<div>
						<div class="nickname author">
							{{item.userName}}
						</div>
						<div class="date">
							{{item.createTime}}
						</div>
					</div>
				</div>
<!--                评论内容-->
                <div class="reply-content" v-html="item.content">{{item.content}}</div>
<!--                回复评论操作区-->
				<div v-if="userName!=null" class="reply-content reply-fa">
                    <div class="reply-font" @click="doReply(item.commentId)">
                        <div>
                            <img src="./img/icon/reply.png" class="icon-reply"><span class="icon-reply icon-hf">回复</span>
                        </div>

                    </div>

                    <div class="comment" :style="{width:commentWidth}" v-if="replyMap[item.commentId]" >
                        <el-input
                          @focus="showButton(item.commentId)"
                          type="textarea"
                          :autosize="{ minRows: minRows, maxRows: maxRows}"
                          :placeholder=placeholder2
                          v-model="textareaMap[item.commentId]">
                        </el-input>

                         <div v-if="buttonMap[item.commentId]" class="hbl-owo">
                            <div class="publish publish-btn">
                              <button class="btn" @click="doReplySend(item.commentId,item.userId,item.userName)">发送</button>
                              <button @click="cancel(item.commentId)" class="btn btn-cancel">取消</button>
                            </div>
                         </div>
                    </div>
				</div>

			</div>

<!--	        回复列表-->
            <Reply title='sonComp'
                   :replies="item.chilren"
                   @listenToChild="getDataFromChild"
                   :uName="userName">
            </Reply>

		</div>




    </div>
</template>

<script>
    import api from "@/api/index"
    import Reply from "./Reply";

	export default {
        components: {Reply},
        props:{
	  	emojiWidth:{
	  		type:String,
	  		default:'560px'
	  	},
	  	placeholder:{
	  		type:String,
	  		default:'在此输入评论内容...'
	  	},
        placeholder2:{
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
        // commentList: Array
	  },
	  data() {
	    return {
	        userId:null,
            userName:null,
            email:null,
            errorInfo:false,
			replyMap:[
				],
			buttonMap:[],
			pBodyMap:[],
			textareaMap:[],
            commentList:[],
	    }
	  },

        methods: { //事件处理器
            exit(){
                localStorage.clear(),
                this.$router.push('/login')
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
            gotoRegister(){
                this.$router.push('/register')
            },
            gotoLogin() {
                this.$router.push('/login')
            },
            async doSend(){
                const self=this;
                let comment={}
                comment["content"]=this.textareaMap[0]
                let result=await api.remark(comment)
                // console.log(result)
                if (result.data.status==-1){
                    self.errorInfo=true,
                    self.errInfo=result.data.content,
                    self.$message.error(result.data.content)
                }else if (result.data.status==0){
                    self.$message.success("评论成功！")
                    this.$set(this.textareaMap,0,'')
                    this.getAllComments()
                }


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
                    this.cancel(commentPid)
                    this.getAllComments()
                }
            },
            async getAllComments(){
                let result=await api.getAllComments()
                if (result.data.status==0) {
                    let commentList = result.data.content
                    // console.log(commentList)
                    this.commentList = JSON.parse(commentList)
                }
            },

            doReply(index){
                this.$set(this.replyMap,index,true)
                // console.log(this.replyMap[index]);
            },
            // 父子组件通信的方法
            getDataFromChild () {
                this.getAllComments()
            }
        },
        watch: {
           // 如果路由有变化，会再次执行该方法
           // '$route':'routeChange'
         },
        async created() { //生命周期函数
            let result=await api.getUserInfo()
            if (result.data.status==0){
                let userId = result.data.content.user.userId
                let userName = result.data.content.user.userName
                let email = result.data.content.user.userName
                // console.log(userName)
                // console.log(userId)
                this.userId = userId
                this.userName = userName
                this.email=email
            }
            this.getAllComments()

        },
        mounted(){//页面加载完成后

        }

	}
</script>
<style type="text/css" scoped>
    .register {
        font-size: 14px;
        line-height: 30px;
        color: #999;
        cursor: pointer;
        float: right;
    }
	.comment{
		display: inline-block;
		vertical-align:top;
	}

	.publish{
		margin-top: 10px;
		display: inline-block;
		vertical-align:top;	
	}
	.publish-btn{
		float: right;
	}
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

.tmsg-respond h3{
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 8px;
}
.tmsg-respond h3 small{
    font-size: smaller;
    cursor: pointer;
}
.tmsg-respond textarea{
    background:#f4f6f7;
    height:100px;
    margin-bottom: 10px;
}

/*用户输入表单*/

.tmsg-r-info input{
    height:30px;
    border-radius: 4px;
    background: #f4f6f7;
}
.tmsg-r-info .info-submit{
    margin: 10px 0;
    text-align: center;
}
.tmsg-r-info .info-submit p,.tmsg-commentshow h1{
    /*background: #97dffd;*/
    color:#fff;
    border-radius: 5px;
    cursor: pointer;
    /*transition: all .3s ease-in-out;*/
    height:30px;
    line-height: 30px;
    text-align: center;
}

/*评论列表*/
.tmsg-comments .tmsg-comments-tip{
    display: block;
    border-left: 2px solid #363d4c;
    padding: 0 10px;
    margin: 40px 0;
    font-size: 20px;
}
.tmsg-commentlist {
    margin-bottom:20px;

}
.tmsg-commentshow>.tmsg-commentlist{
    border-bottom: 1px solid #e5eaed;
}
.tmsg-c-item{
    border-top: 1px solid #e5eaed;
}
.tmsg-c-item article{
    margin:20px 0;
}
.tmsg-c-item article header{
    margin-bottom:10px;
}
.tmsg-c-item article header img{
    width: 65px;
    height: 65px;
    border-radius: 50%;
    float: left;
    transition: all .4s ease-in-out;
    -webkit-transition: all .4s ease-in-out;
    margin-right: 15px;
    object-fit: cover;
}
.tmsg-c-item article header img:hover{
    transform: rotate(360deg);
    -webkit-transform: rotate(360deg);
}
.tmsg-c-item article header .i-name{
    font-size: 14px;
    margin:5px 8px 7px 0;
    color:#444;
    font-weight: bold;
    display: inline-block;
}
.tmsg-c-item article header .i-class{
    display: inline-block;
    margin-left:10px;
    background: #dff0d8;
    color:#3c763d;
    border-radius: 5px;
    padding: 3px 6px;
    font-size: 12px;
    font-weight: 400;
}
.tmsg-c-item article header .i-time{
    color:#aaa;
    font-size: 12px;
}
.tmsg-c-item article section{
    margin-left: 80px;
}
.tmsg-c-item article section p img{
    vertical-align: middle;
}
.tmsg-c-item article section .tmsg-replay{
    margin:10px 0;
    font-size: 12px;
    color:#64609E;
    cursor: pointer;
}
.hbl-owo{
	text-align: left;
}
.comm{
		padding: 20px;
	}
	.su{
		margin-top: 2px;
		width: 5px;
		height: 23px;
		background:#3CB371; 	/*#1E90FF*/
	}
	.com-rep{
		display: inline-block;
		vertical-align:top;	
	}
	.com-title{
		font-size: 20px;
		margin-left: 5px;
	}
	.com-span{
		font-size: 16px;
	}
	.hbl-fa{
		text-align: left;
	}
	.hbl-comm{
		padding: 40px;
	}

	.reply{
		border-top: solid 1px #D9D9D9;
	}
	.content{
		margin-top: 20px;
		margin-bottom: 20px;
	}
	.comment-f{
		display: inline-block;
		vertical-align: top;
	}
	.nickname{
		font-size: 14px;
	}
	.author{
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