package model;

public class Comment {

    private int commentId;
    private int contentId;
    private String text;

    public Comment(int commentId, int contentId, String text) {
        this.commentId = commentId;
        this.contentId = contentId;
        this.text = text;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getContentId() {
        return contentId;
    }

    public String getText() {
        return text;
    }

    public void notifyUser(int userId) {
        System.out.println(
                "Usuário " + userId + " notificado sobre novo comentário."
        );
    }
}
