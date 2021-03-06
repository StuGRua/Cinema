package Service.impl;
import DBopeartion.ChangeMovieDao;
import DBopeartion.TicketDao;
import DBopeartion.UserDao;
import DBopeartion.impl.ChangeMovieDaoImpl;
import DBopeartion.impl.ChangeShowDaoImpl;
import DBopeartion.impl.TicketDaoImpl;
import DBopeartion.impl.UserDaoImpl;
import Entity.Audience;
import Entity.Movie;
import Entity.Show;
import Service.ChangeMovie;
import Service.ChangeShow;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


/* *
 * @Author 朝喜
 * @Description 管理员服务类
 * @Date  2020-8-8
 **/

public class ManagerService extends Service implements ChangeShow, ChangeMovie {
    public ManagerService(int Aud_id,String Aud_type) {
        super(Aud_id,"Manager");
    }

    @Override
    public void AddMovie() {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入电影id：");
        int movie_id = input.nextInt();
        System.out.println("请输入电影名：");
        String movie_name = input.next();
        System.out.println("请输入电影票价：");
        double movie_price = input.nextDouble();
        try {
            System.out.println("请输入上映时间：");
            String movie_sta = input.next();
            System.out.println("请输入下架时间：");
            String movie_en = input.next();
            Date movie_start = format.parse(movie_sta);
            Timestamp timeStamp_start = new Timestamp(movie_start.getTime());
            Date movie_end = format.parse(movie_en);
            Timestamp timeStamp_end = new Timestamp(movie_end.getTime());
            System.out.println("请输入影片时长：");
            int last = input.nextInt();
            Movie temp = new Movie(movie_id, movie_name, movie_price,
                    timeStamp_start, timeStamp_end, last);
            String sql = "insert into Movie(movie_id,movie_name,Base_price," +
                    "Start_time,End_time,last_time) values(?,?,?,?,?,?)";
            Object[] param = {temp.getMovie_id(), temp.getMovie_name(), temp.getMovie_baseprice()
                    , temp.getMovie_starttime(), temp.getMovie_endtime(), temp.getLast_time()};
            ChangeMovieDaoImpl changeMovieDaoimpl = new ChangeMovieDaoImpl();
            int count = changeMovieDaoimpl.updateMovie(sql, param);
            if (count > 0)
                System.out.println("成功添加：" + temp.getMovie_name() + "影片");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DelMovie() {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入电影id：");
        int movie_id = input.nextInt();
        Movie temp = new Movie();
        temp.setMovie_id(movie_id);
        ChangeMovieDao changeMovieDaoImpl = new ChangeMovieDaoImpl();
        Object[] param = {temp.getMovie_id()};
        temp = changeMovieDaoImpl.getMovie(param);
        String sql = "delete from Movie where Movie_id=?";
        int count = changeMovieDaoImpl.updateMovie(sql, param);
        if (count > 0)
            System.out.println("成功删除：" + temp.getMovie_name() + "影片");
    }

    @Override
    public void AddShow() {
        System.out.println("电影信息：");
        printAllMovie();
        Scanner input = new Scanner(System.in);
        System.out.println("请输入电影id：");
        int movie_id = input.nextInt();
        System.out.println("请输入放映厅id：");
        int hall_id = input.nextInt();
        input.nextLine();
        try {
            System.out.println("请输入播放时间：");
            String show = input.nextLine();
            Date shows = format2.parse(show);
            Timestamp show_time = new Timestamp(shows.getTime());
            Show temp = new Show(hall_id, movie_id, show_time);
            String str = "insert into `Show`(Hall_id,Movie_id,Show_time) values(?,?,?)";
            Object[] param = {temp.getHall_id(), temp.getMovie_id(), temp.getShow_time()};
            ChangeShowDaoImpl changeShowDaoimpl = new ChangeShowDaoImpl();
            int count = changeShowDaoimpl.updateShow(str, param);
            if (count > 0) {
                System.out.println("成功添加：" + temp.getHall_id() + "号大厅" + temp.getShow_time() + "场次电影");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DelShow() {
        System.out.println("电影信息：");
        printAllMovie();
        System.out.println("放映信息：");
        getShow();
        Scanner input = new Scanner(System.in);
        System.out.println("请输入电影id：");
        int movie_id = input.nextInt();
        System.out.println("请输入放映厅id：");
        int hall_id = input.nextInt();
        try {
            input.nextLine();
            System.out.println("请输入播放时间：");
            String show = input.nextLine();
            Date shows = format2.parse(show);
            Timestamp show_time = new Timestamp(shows.getTime());
            Show temp = new Show(hall_id, movie_id, show_time);
            String str = "delete from `Show` where Hall_id=? and Movie_id=? and Show_time=?";
            Object[] param = {temp.getHall_id(), temp.getMovie_id(), temp.getShow_time()};
            ChangeShowDaoImpl changeShowDaoimpl = new ChangeShowDaoImpl();
            int count = changeShowDaoimpl.updateShow(str, param);
            if (count > 0) {
                System.out.println("成功删除：" + temp.getHall_id() + "号大厅" + temp.getShow_time() + "场次电影");
            }else System.out.println("删除失败，未找到该场次！");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void ChangeShow() {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入原电影id：");
        int movie_id_old = input.nextInt();
        System.out.println("请输入原放映厅id：");
        int hall_id_old = input.nextInt();
        try {
            input.nextLine();
            System.out.println("请输入原播放时间：");
            String show = input.nextLine();
            Date shows = format2.parse(show);
            Timestamp show_time_old = new Timestamp(shows.getTime());
            Show temp = new Show(movie_id_old, hall_id_old, show_time_old);
            String[] param1 = {movie_id_old + "", hall_id_old + "", show_time_old + ""};
            if (!find(param1)) {
                System.out.println("没有该放映场次！");
                return;
            }
            System.out.println("请输入现电影id：");
            int movie_id_new = input.nextInt();
            System.out.println("请输入现放映厅id：");
            int hall_id_new = input.nextInt();
            input.nextLine();
            System.out.println("请输入现播放时间：");
            show = input.nextLine();
            shows = format2.parse(show);
            Timestamp show_time_new = new Timestamp(shows.getTime());
            Show temp2 = new Show(movie_id_new, hall_id_new, show_time_new);
            String str = "update Show set Hall_id=?,Movie_id=?,Show_time=? where Hall_id=? and Movie_id=? and Show_time=?";
            Object[] param = {temp2.getHall_id(), temp2.getMovie_id(), temp2.getShow_time(), temp.getHall_id(), temp.getMovie_id(), temp.getShow_time()};
            ChangeShowDaoImpl changeShowDaoimpl = new ChangeShowDaoImpl();
            int count = changeShowDaoimpl.updateShow(str, param);
            if (count > 0) {
                System.out.println("成功修改为：" + temp2.getHall_id() + "号大厅" + temp2.getShow_time() + "场次电影");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public boolean find(String[] param) {
        String sql = "select * from show where Hall_id=? and Movie_id=? and Show_time=?";
        TicketDao ticket = new TicketDaoImpl();
        List<Show> showList = ticket.getShow(sql, param);
        return showList.size() > 0;
    }

    //管理员查询
    public void ManageFind() {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("-------------管理员查询-------------");
            System.out.println("请输入查询类型 1.查询所有放映记录 2.查询所有电影 3.查询所有用户4.查询所有订票记录5.返回");

            int i = input.nextInt();
            switch (i) {
                case 1:
                    getShow();
                    break;
                case 2:
                    printAllMovie();
                    break;
                case 3:
                    printAllUser();
                    break;
                case 4:
                    getRecord();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("请输入正确数字(1-5)");
            }
        }
    }
    /*
    按照电影名查询放映记录
    输入电影名
    打印 序号 电影名 影厅号 时间 影厅类型 电影时长 票价
    */

    public void printAllUser() {
        System.out.println("序号\t\t\t用户名\t\t\t\t\t密码\t\t\t\t\t\t手机号\t\t\t用户类型");
        UserDao userDao = new UserDaoImpl();
        List<Audience> audienceList = userDao.getAllUser();
        for (Audience value : audienceList) {
            Audience audience = new Audience();
            audience = value;
            System.out.format("%-8d\t%-20s\t%-20s\t%-15s\t%-10s\n", audience.getAud_id(), audience.getAud_name(), audience.getAud_password(), audience.getAud_tel(), audience.getAud_type());
        }

    }
}
