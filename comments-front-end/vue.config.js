module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8090',
                changeOrigin: true,
                pathRewrite: {                   //路径重写
                    '^/api': ''                     //选择忽略拦截器里面的单词
                },
            },
        }
    }
}