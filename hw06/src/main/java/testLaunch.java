public class testLaunch {
    public static void main(String[] args) throws Exception {
        TestAnalyzer analyzer = new TestAnalyzer();
        // проверим класс reflectionTest
        analyzer.parse("reflectionTest");
    }
}
