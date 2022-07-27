package work.skymoyo.mock.common.exception;

public class MockException extends RuntimeException {

    private String rspMsg;

    public MockException(String msg) {
        super(msg, null, false, false);
        this.rspMsg = msg;
    }


}