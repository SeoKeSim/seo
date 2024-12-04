package team.beans;

public class QnADTO {
    private int uniqueId;
    private String userId;
    private String title;
    private String question;
    private String answer;

    // Getters and Setters
    public int getUniqueId() {
        return uniqueId;
    }
    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}