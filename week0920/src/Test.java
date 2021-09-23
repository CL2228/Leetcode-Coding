public class Test {



    private String name = "hahha";

    public String getName(String name) {
        return this.name;
    }



    public static void main(String[] args) {
        Test t = new Test();
        System.out.println(t.getName("??"));
    }
}
