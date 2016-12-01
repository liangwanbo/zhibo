package com.mytv365.zb.model;

import java.util.List;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/29
 * Description:
 */

public class ArticleDetailBean extends BaseBean<ArticleDetailBean.ArticleResult> {
    public static class ArticleResult {
        private List<HeadImage> articleArewardHeadImageList;
        private ArticleInfo articleInfo;
        private List<ArticleComment> articleCommentList;
        private List<ArticleForRead> articleForRead;

        public List<HeadImage> getArticleArewardHeadImageList() {
            return articleArewardHeadImageList;
        }

        public void setArticleArewardHeadImageList(List<HeadImage> articleArewardHeadImageList) {
            this.articleArewardHeadImageList = articleArewardHeadImageList;
        }

        public ArticleInfo getArticleInfo() {
            return articleInfo;
        }

        public void setArticleInfo(ArticleInfo articleInfo) {
            this.articleInfo = articleInfo;
        }

        public List<ArticleComment> getArticleCommentList() {
            return articleCommentList;
        }

        public void setArticleCommentList(List<ArticleComment> articleCommentList) {
            this.articleCommentList = articleCommentList;
        }

        public List<ArticleForRead> getArticleForRead() {
            return articleForRead;
        }

        public void setArticleForRead(List<ArticleForRead> articleForRead) {
            this.articleForRead = articleForRead;
        }
    }

    public class HeadImage {
        private String headImages;

        public String getHeadImages() {
            return headImages;
        }

        public void setHeadImages(String headImages) {
            this.headImages = headImages;
        }
    }

    public class ArticleInfo {
        private int id;
        private String source;
        private int arewardCount;
        private String title;
        private String type;
        private Long createTime;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getArewardCount() {
            return arewardCount;
        }

        public void setArewardCount(int arewardCount) {
            this.arewardCount = arewardCount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class ArticleComment {
        private Long createTime;
        private String nickName;
        private String content;

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public class ArticleForRead {
        private int id;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
