<template>
    <div>
        <Comment></Comment>
    </div>
</template>

<script>
    import Comment from '@/components/Comment';
    import Reply from '@/components/Reply';

    export default {
        name: "comment",
        components:{Comment,Reply},
    }
</script>

<style scoped>

</style>