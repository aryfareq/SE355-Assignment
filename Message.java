class Message {
    private final String word;
    private final int lamportClock;

    public Message(String word, int lamportClock) {
        this.word = word;
        this.lamportClock = lamportClock;
    }

    public String getWord() {
        return word;
    }

    public int getLamportClock() {
        return lamportClock;
    }
}
