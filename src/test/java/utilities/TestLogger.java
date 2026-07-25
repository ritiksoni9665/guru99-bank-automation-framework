package utilities;

public class TestLogger {

    public static synchronized void print(String message) {
        System.out.println(message);
    }

    public static synchronized void pass(String testCase) {

        System.out.println(
                "\n" + testCase + "\n" +
                "Status : PASS \u2705\n");
    }

    public static synchronized void fail(
            String testCase,
            String reason) {

        System.out.println(
                "\n" + testCase + "\n" +
                "Status : FAIL \u274C\n" +
                "Reason : " + reason + "\n");
    }

    public static synchronized void blocked(
            String testCase,
            String reason) {

        System.out.println(
                "\n" + testCase + "\n" +
                "Status : BLOCKED \u26A0\n" +
                "Reason : " + reason + "\n");
    }
}