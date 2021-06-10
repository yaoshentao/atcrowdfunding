public class StudentTest {


    private String name;
    private static int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        StudentTest.age = age;
    }


    protected class Child{
        private String name1;
        private int age1;

        public String getName1() {
            return name1;
        }
        public void setName1(String name1) {
            this.name1 = name1;
        }
        public int getAge1() {
            System.out.println(age);
            return age1;
        }
        public void setAge1(int age1) {
            this.age1 = age1;
        }


    }

    public static void main(String[] args) {
        StudentTest s = new StudentTest();

        StudentTest.Child c = s.new Child();
    }
}

