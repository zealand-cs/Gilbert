package dk.zealandcs.gilbert.domain.post;

public enum PostStatus {
    PendingApproval (0),
    Available       (1),
    Reserved        (2),
    Sold            (3);


    private final int status;

    PostStatus(int status) {
        this.status = status;
    }

    public boolean isAtLeast(PostStatus postStatus) {return this.status >= postStatus.status;
    }

    public static PostStatus getDefault() {
        return PendingApproval;
    }

}
