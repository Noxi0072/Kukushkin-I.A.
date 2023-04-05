import java.util.Scanner;
public  class Main {
    public static void main(String[]args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите строку:");
        String input = scan.nextLine();
        String result = StringConverter.convert(input);
        System.out.println("Результат:" + result);
    }

}
