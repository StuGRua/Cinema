package Authority;

import User.impl.Customer;
import User.impl.Manager;
import DBopeartion.UserDao;
import DBopeartion.impl.UserDaoImpl;
import Entity.Audience;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


/* *
 * @Author StuG
 * @Description 用户实际使用类：登陆、注册、忘记密码（登录界面）、修改账户信息（管理员）
 * @Date  2020-8-11
 **/

public class User {
    public UserDao userdao = new UserDaoImpl();
    public String username;
    HashMap<String, Audience> map = new HashMap<>();
    private Role role = null;

    /**
     * 读取用户信息
     * @return 用户信息表
     */
    public boolean init() {
        List<Audience> audienceList = userdao.getAllUser();
        for (Audience value : audienceList) {
            Audience audience = new Audience();
            audience = value;
            map.put(audience.getAud_name(), audience);
        }
        return map.size() > 0;
    }

    /**
     * 用户登陆，读取信息实现管理员or用户角色的分配
     * @return 是否登陆成功
     */
    public boolean Login() {
        role = new Role();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名");
        username = scanner.next();
        System.out.println("请输入密码");
        String password = scanner.next();
        //管理员登录
        if (!map.containsKey(username)) {
            System.out.println("用户名不存在！");
            return false;
        } else if (map.get(username).getAud_password().equals(password) && map.get(username).getAud_type().equals("Manager")) {
            role.setDescription("管理员");
            Manager admin = new Manager(map.get(username).getAud_id());
            role.setManager(admin);
            return true;
        } else if (map.get(username).getAud_type().equals("Manager")) {
            System.out.println("管理员密码错误");
            return false;
        } else if (!map.get(username).getAud_password().equals(password)) {
            System.out.println("密码错误！重新输入");
            password = scanner.next();
            if (!map.get(username).getAud_password().equals(password)) {
                System.out.println("密码错误！重新输入");
                password = scanner.next();
                if (!map.get(username).getAud_password().equals(password)) {
                    System.out.println("输入错误三次，本次登陆失败");
                    return false;
                } else {
                    role.setDescription("用户");
                    Customer audience = new Customer(map.get(username).getAud_id());
                    role.setNormal(audience);
                    return true;
                }
            } else {
                role.setDescription("用户");
                Customer audience = new Customer(map.get(username).getAud_id());
                role.setNormal(audience);
                return true;
            }
        } else {
            role.setDescription("用户");
            Customer audience = new Customer(map.get(username).getAud_id());
            role.setNormal(audience);
            return true;
        }
    }

    /**
     *  注册函数
     *  新增用户放到泛型集合里
     * @return 是否成功注册
     */

    public boolean Register() {
        System.out.println("请输入要注册的用户名(用户名长度3~20)：");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("请输入密码");
        String password = scanner.nextLine();
        if (name.length() >= 3 && name.length() <= 20) {
            if (map.containsKey(name)) {
                System.out.println("用户名已存在");
            } else {
                System.out.println("请再次输入密码：");
                String password2 = scanner.nextLine();
                if (password.equals(password2)) {
                    System.out.println("请输入您的电话");
                    String tel = scanner.next();
                    Save(map.size() + 1, name, tel, "Authority", password);
                    map.put(name, new Audience(map.size() + 1, name, tel, "Authority", password));
                    return true;
                } else
                    System.out.println("两次密码输入必须相同！");
            }
            return false;
        } else
            System.out.println("请输入正确长度的用户名！");
        return false;
    }

    public void Save(int i, String name, String tel, String type, String password) {
        Audience audience = new Audience(i, name, tel, type, password);
        if (userdao.AddUser(audience) > 0)
            System.out.println("用户信息保存成功");
        else System.out.println("用户信息保存失败");
    }

    /**
     * 忘记密码
     * @return 是否成功获得密码
     */
    public boolean Forget() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        username = scanner.next();
        System.out.println("请输入预留的手机号码：");
        String tel = scanner.next();
        if (!map.containsKey(username)) {
            System.out.println("用户名不存在！");
            return false;
        } else if (map.get(username).getAud_tel().equals(tel)) {
            System.out.println("密码为：" + map.get(username).getAud_password());
            return true;
        }
        return false;
    }

    /**
     * 修改用户信息（允许修改权限）
     */
    public void EditUser() {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入原用户id：");
        int user_id_old = input.nextInt();
        System.out.println("请输入原用户名：");
        String user_name_old = input.next();
        input.nextLine();
        if (!map.containsKey(user_name_old) || map.get(user_name_old).getAud_id() != user_id_old) {
            System.out.println("没有该用户！");
            return;
        }
        System.out.println("找到用户：" + user_id_old + "-" + user_name_old);
        System.out.println("请输入现用户名：");
        String Name2 = input.nextLine();
        System.out.println("请输入现用户电话号码：");
        String tel2 = input.nextLine();
        System.out.println("请输入现用户权限：（Manager/User）");
        String Type2 = input.nextLine();
        System.out.println("请输入现用户密码：");
        String PSW2 = input.nextLine();
        Audience temp2 = new Audience(user_id_old, Name2, tel2, Type2, PSW2);
        String str = "update audience set Name=?,PassWord=?,Tel=?,Type=? where Aud_id=? and Name=?";
        Object[] param = {temp2.getAud_name(), temp2.getAud_password(), temp2.getAud_tel(), temp2.getAud_type(), user_id_old, user_name_old};
        UserDaoImpl userdaoimpl = new UserDaoImpl();
        int count = userdaoimpl.updateUser(str, param);
        if (count > 0) {
            System.out.println("成功修改！id:" + temp2.getAud_id() + "\t用户名：" + temp2.getAud_name() + "\t密码：" + temp2.getAud_password() + "\t手机号码：" + temp2.getAud_tel() + "\t类型：" + temp2.getAud_type());
        }
        init();
    }

    public void Search() {

        role.Search();
    }

    public void Order() {

        role.Order();
    }

    public void refund() {

        role.Refund();
    }

    public void change() {

        role.Change();
    }

    public void addMovie() {

        role.AddMovie();
    }

    public void delMovie() {
        role.DelMovie();
    }

    public void addShow() {

        role.AddShow();
    }

    public void delShow() {

        role.DelShow();
    }

    public Role getRole() {

        return role;
    }

    //public void setRole(Role role) { this.role = role; }


}
