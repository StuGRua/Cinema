package Menu;

import java.util.*;

import User.*;


//主页面
public class MainMenu {
    public User user = new User();
    Scanner input = new Scanner(System.in);

    public void menu() {
        if (!user.init()) {
            System.out.println("初始化失败...");
            return;
        }
        while (true) {
            System.out.println("-------------电影管理系统-------------");
            System.out.println("1.用户登陆\t2.用户注册\t3.退出");
            int temp = input.nextInt();
            switch (temp) {
                case 1:
                    LoginMenu();
                    break;
                case 2:
                    RegisterMenu();
                    break;
                case 3:
                    System.out.println("欢迎下次光临！");
                    return;
                default:
                    System.out.println("输入错误，请重新输入！");
            }
        }
    }

    public void LoginMenu() {
        System.out.println("-------------登陆页面-------------");
        if (user.login())
            next();
    }

    public void RegisterMenu() {
        System.out.println("-------------注册页面-------------");
        if (user.register()) {
            System.out.println("注册成功！");
        } else {
            System.out.println("注册失败，请重新注册！");
        }
    }

    public void next() {
        System.out.println("-------------Loading...-------------");
        while (true) {
            if (user.getRole().getDescription().equals("管理员")) {
                System.out.println("-------------管理系统-------------");
                System.out.println("欢迎，管理员" + user.username + "，请选择您的操作");
                System.out.println("1.查询信息\t2.影片管理\t3.放映管理\t4.退出登陆");
                int temp = input.nextInt();
                int temp2;
                switch (temp) {
                    case 1:
                        user.search();
                        break;
                    case 2:
                        System.out.println("1.上架影片\t2.下架影片\t3.返回");
                        temp2 = input.nextInt();
                            switch (temp2){
                                case 1:
                                    user.addMovie();
                                    break;
                                case 2:
                                    user.delMovie();
                                    break;
                                default:
                                    break;
                            }
                            break;
                    case 3:
                        System.out.println("1.开始放映\t2.结束放映\t3.返回");
                        temp2 = input.nextInt();
                        switch (temp2){
                            case 1:
                                user.addShow();
                                break;
                            case 2:
                                user.delShow();
                                break;
                            default:
                                break;
                        }
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("输入错误，请重新输入！");
                }

            } else if (user.getRole().getDescription().equals("用户")) {
                System.out.println("-------------观众系统-------------");
                System.out.println("欢迎，用户" + user.username + "，请选择您的服务");
                System.out.println("1.订票\t2.退票\t3.改签\t4.查询电影&购票记录\t5.退出登陆");
                int temp = input.nextInt();
                switch (temp) {
                    case 1:
                        user.order();
                        break;
                    case 2:
                        user.refund();
                        break;
                    case 3:
                        user.change();
                        break;
                    case 4:
                        user.search();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("输入错误，请重新输入");
                        //temp2 = input.nextInt();
                }
            } else {
                System.out.println("输入错误，请重新输入");
            }
        }

    }
}
