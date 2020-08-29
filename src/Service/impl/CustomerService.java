package Service.impl;
import DBopeartion.impl.HallDaoImpl;
import DBopeartion.impl.TicketDaoImpl;
import Entity.ArrangeTicket;
import Entity.Hall;
import Entity.Show;
import Service.Ticket;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/* *
 * @Author StuG
 * @Description 客户服务类
 * @Date  2020-8-10
 **/

public class CustomerService extends Service implements Ticket {

    public CustomerService(int Aud_id,String Aud_type) {
        super(Aud_id,Aud_type);
    }

    @Override
    /*
      订票函数：分为自动购票和手动购票
      自动购票根据具体座位情况和购票数量选择是否能选择连续座位
     */
    public void Order() {
        List<String> movieList = getShow();
        Scanner input = new Scanner(System.in);
        System.out.println("请输入电影名：");
        String movie_name = input.next();
        int i;
        for (i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).equals(movie_name))
                break;
        }
        if (i == movieList.size()) {
            System.out.println("没有该电影");
            return;
        }
        TicketDaoImpl ticket = new TicketDaoImpl();
        String sql = "select Show.Movie_id ,show.Show_time,show.Hall_id\n" +
                "from `Show` join Movie on Movie.Movie_id =Show.Movie_id\n" +
                "where Movie_name like ?";
        String[] param = {"%" + movie_name + "%"};
        List<Show> showList = ticket.getShow(sql, param);
        System.out.println("序号\t\t\t电影名\t\t\t\t厅号\t\t时间");
        for (i = 0; i < showList.size(); i++) {
            Show show = showList.get(i);
            System.out.format("%-8d\t%-14s\t%-3s\t%-26s\n", (i + 1), movie_name, show.getHall_id() + "号厅", show.getShow_time());
        }
        int choice;
        while (true) {
            System.out.println("请选择场次（填序号）：(0返回)");
            choice = input.nextInt();
            if (choice == 0)
                return;
            else if (choice > showList.size()) {
                System.out.println("请输入列表中的序号！");
            } else
                break;
        }
        System.out.println("您已选择：\n" + choice + "\t" + movie_name + "\t" + showList.get(choice - 1).getHall_id() + "号厅\t" + showList.get(choice - 1).getShow_time());
        System.out.println("请选择预订的电影票数");
        int num = input.nextInt();
        System.out.println("是否手动选座：（y/n）");
        char c = input.next().charAt(0);
        if (c == 'y' || c == 'Y') {
            while (num != 0) {
                selfChooseSeat(showList.get(choice - 1));
                num--;
            }
        } else {
            autoChooseSeat(showList.get(choice - 1), num);
        }
    }

    /**
     * 自动选座
     */
    public void autoChooseSeat(Show show, int num) {
        //得到当前影厅行数和列数
        String sql = "select * from MovieHall where Hall_id = ?";
        String[] param = {show.getHall_id() + ""};
        HallDaoImpl hallDaoimpl = new HallDaoImpl();
        List<Hall> hallList = hallDaoimpl.getHall(sql, param);
        int row = hallList.get(0).getHallRowSum();
        int line = hallList.get(0).getHallLineSum();
        //得到当前选票数据
        sql = "select * from arrange where arrange.hall_id = ? and arrange.arrange_time = ?";
        String[] param1 = {show.getHall_id() + "", show.getShow_time() + ""};
        TicketDaoImpl ticket = new TicketDaoImpl();
        List<ArrangeTicket> arrangeTicketList = ticket.search(sql, param1);

        char[][] chars = new char[line][row];
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < row; j++) {
                chars[i][j] = '_';
            }
        }
        for (ArrangeTicket arrangeTicket : arrangeTicketList) {
            chars[arrangeTicket.getLine() - 1][arrangeTicket.getRow() - 1] = '*';
        }//生成选座数组


        //---------------------------------------------
        //开始优化选座的处理
        int autoLine = 0;
        int autoRow = 0;
        //缩小选座区域
        int font, back, left, right;
        font = line / 4;
        back = line * 3 / 4;
        left = row / 4;
        right = row * 3 / 4;

        int temp_i = font;
        int temp_j = left - 1;
        boolean flag = false;

        for (int i = font; i <= back; i++) {
            for (int j = left; j <= right; j++) {
                if (chars[i][j] == '*' && !flag) {//____*<-____*____
                    if (j - left >= num) {//前面够否？
                        autoLine = i;
                        autoRow = left;
                        break;
                    }
                    temp_i = i;
                    temp_j = j;
                    flag = true;
                } else if (chars[i][j] == '*' && flag) {//_____*____*<-____
                    if (j - temp_j - 1 >= num) {//中间够否？
                        autoLine = temp_i;
                        autoRow = temp_j + 1;
                        break;
                    } else {
                        temp_i = i;
                        temp_j = j;
                    }
                }
            }
            if (right - temp_j - 1 >= num) {//后面够否？整行空
                autoLine = temp_i;
                autoRow = temp_j + 1;
                break;
            } else {
                temp_i = i + 1;
                temp_j = left - 1;
                flag = false;
            }
        }
        //优化选座处理完毕，接下来进行优化购票是否越界的检测，如果越界则执行顺序购票
        //----------------------------------------------------------
        int number = 0;
        autoLine++;
        autoRow++;
        if (row<num||autoRow+num>row||autoLine>line)//越界检测
        {
            System.out.println("无法优化选座，进入顺序选座模式！");
            for (int i=0;i<line;i++){
                for (int j=0;j<row&&number<num;j++){
                    if (chars[i][j]!='*'){
                        String[] param2 = {super.getAud_id() + "", show.getHall_id() + "", show.getMovie_id() + "", (i+1) + "", (j+1) + "", show.getShow_time() + ""};
                        sql = "insert into arrange(Aud_id,Hall_id,Movie_id,line,row,Arrange_time) values(?,?,?,?,?,?)";
                        int count = ticket.insertArrange(sql, param2);
                        if (count > 0) {
                            System.out.println("顺序购票成功！在第" + (i+1) + "排第" + (j+1) + "号");
                        } else System.out.println("顺序购票失败！");
                        number++;
                    }
                }
            }
            return;
        }//无法最优选座

        //未越界：优化购票：同时订多张连坐的实现
        String[] param2 = {super.getAud_id() + "", show.getHall_id() + "", show.getMovie_id() + "", autoLine + "", (autoRow + number) + "", show.getShow_time() + ""};
        while (number != num) {
            sql = "insert into arrange(Aud_id,Hall_id,Movie_id,line,row,Arrange_time) values(?,?,?,?,?,?)";
            param2[4] = (autoRow + number) + "";

            int count = ticket.insertArrange(sql, param2);
            if (count > 0) {
                System.out.println("优化购票成功！在第" + autoLine + "排第" + (autoRow + number) + "号");
            } else System.out.println("优化购票失败！"+ Arrays.toString(param2));
            number++;
        }

    }

    /**
     * 手动选座
     */
    public void selfChooseSeat(Show show) {
        //显示座位
        //从数据库中得到厅的行列数
        String sql = "select * from MovieHall where Hall_id = ?";
        String[] param = {show.getHall_id() + ""};
        HallDaoImpl hallDaoimpl = new HallDaoImpl();
        List<Hall> hallList = hallDaoimpl.getHall(sql, param);
        int row = hallList.get(0).getHallRowSum();
        int line = hallList.get(0).getHallLineSum();
        char[][] chars = new char[line][row];
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < row; j++) {
                chars[i][j] = '_';
            }
        }
        //用Arrange表中的数据进行安排
        sql = "select * from arrange where arrange.hall_id = ? and arrange.arrange_time = ?";
        String[] param1 = {show.getHall_id() + "", show.getShow_time() + ""};
        TicketDaoImpl ticket = new TicketDaoImpl();
        List<ArrangeTicket> arrangeTicketList = ticket.search(sql, param1);
        for (ArrangeTicket arrangeTicket : arrangeTicketList) {
            chars[arrangeTicket.getLine() - 1][arrangeTicket.getRow() - 1] = '*';
        }
        printSeat(chars, line, row);
        System.out.println("\t\t\t _可选，*不可选");
        //输入列数和行数
        System.out.println("请选择您的座位排");
        Scanner input = new Scanner(System.in);
        int i = input.nextInt();
        System.out.println("请选择您的座位列");
        int j = input.nextInt();
        while (i > line || i < 0 || j > row || j < 0) {
            System.out.println("选座非法，请重新选座");
            i = input.nextInt();
            j = input.nextInt();
        }
        while (chars[i - 1][j - 1] == '*') {
            System.out.println("已经有人选了哦~，请重新选座");
            i = input.nextInt();
            j = input.nextInt();
        }
        sql = "insert into arrange(Aud_id,Hall_id,Movie_id,line,row,Arrange_time) values(?,?,?,?,?,?)";
        String[] param2 = {super.getAud_id() + "", show.getHall_id() + "", show.getMovie_id() + "", i + "", j + "", show.getShow_time() + ""};
        int count = ticket.insertArrange(sql, param2);
        if (count > 0) {
            System.out.println("购票成功！");
        }
        else System.out.println("购票失败！");
    }

    /**
     * 打印座位
     */
    public void printSeat(char[][] p, int line, int row) {
        System.out.print(" \t\t|");
        for (int i = 0; i < row; i++) {
            if ((i + 1) / 10 == 0)
                System.out.print(" " + (i + 1) + "|");
            else
                System.out.print((i + 1) + "|");
        }
        System.out.print("\n");
        for (int i = 0; i < line; i++) {
            System.out.print("第" + (i + 1) + "排\t|");
            for (int j = 0; j < row; j++) {
                System.out.print(" " + p[i][j] + "|");
            }
            System.out.print("\n");
        }
    }

    @Override
    /*
     *退票实现
     **/
    public void Refund() {
        System.out.println("订票记录为：");
        getRecord();
        Scanner input = new Scanner(System.in);
        System.out.println("请输入电影名：");
        String movie_name = input.nextLine();
        TicketDaoImpl ticket = new TicketDaoImpl();
        List<ArrangeTicket> arrangeTicketList = SearchTickets(movie_name);
        if (arrangeTicketList == null) {
            System.out.println("没有可退的票哦~");
            return;
        }
        printTickets(movie_name, arrangeTicketList);
        System.out.println("请输入退票序号");
        int c = input.nextInt();
        ArrangeTicket delArrangeTicket = arrangeTicketList.get(c - 1);
        String sql = "delete from arrange where movie_id= ? and hall_id=? and Aud_id=? and Line = ? and Row = ?";
        String[] param2 = {delArrangeTicket.getMovie_id() + "", delArrangeTicket.getHall_id() + "", super.getAud_id() + "", delArrangeTicket.getLine() + "", delArrangeTicket.getRow() + ""};

        if (ticket.delArrange(sql, param2) > 0)
            System.out.println("退票成功是否继续退票(y/n)?");
        else {
            System.out.println("退票失败");
            return;
        }
        char ct;
        ct = input.next().charAt(0);
        while (true) {
            if (ct == 'y' || ct == 'Y') {
                System.out.println("请输入退票序号");
                c = input.nextInt();
                delArrangeTicket = arrangeTicketList.get(c - 1);
                param2[3] = delArrangeTicket.getLine() + "";
                param2[4] = delArrangeTicket.getRow() + "";
                if (ticket.delArrange(sql, param2) > 0) {
                    System.out.println("退票成功是否继续退票(y/n)?");
                    ct = input.next().charAt(0);
                } else {
                    System.out.println("退票失败");
                    return;
                }
            } else {
                break;
            }
        }

    }

    @Override
    /*
     改票实现
       */

    public void Change() {
        System.out.println("订票记录为：");
        getRecord();
        TicketDaoImpl ticket = new TicketDaoImpl();
        Scanner input = new Scanner(System.in);
        System.out.println("请输入电影名：");
        String movie_name = input.nextLine();
        List<ArrangeTicket> arrangeTicketList = SearchTickets(movie_name);
        if (arrangeTicketList == null) {
            System.out.println("没有可改的票！");
            return;
        }
        printTickets(movie_name, arrangeTicketList);
        System.out.println("选择要改签的票序号");
        int c = input.nextInt();
        ArrangeTicket changeArrangeTicket = arrangeTicketList.get(c - 1);//用于临时删除对象
        String sql = "delete from arrange where movie_id= ? and hall_id=? and Aud_id=? and Line = ? and Row = ?";
        String[] param2 = {changeArrangeTicket.getMovie_id() + "", changeArrangeTicket.getHall_id() + "", super.getAud_id() + "", changeArrangeTicket.getLine() + "", changeArrangeTicket.getRow() + ""};
        if (ticket.delArrange(sql, param2) > 0) {
            //获得当前电影的其他场次
            sql = "select Movie.Movie_id ,Show_time,Hall_id\n" +
                    "from `Show` join Movie on Movie.Movie_id =Show.Movie_id\n" +
                    "where Movie_name=?";
            String[] param = {movie_name};
            List<Show> showList = ticket.getShow(sql, param);
            //预定电影
            System.out.println("序号\t\t\t电影名\t\t\t\t厅号\t\t时间");
            for (int i = 0; i < showList.size(); i++) {
                Show show = showList.get(i);
                System.out.format("%-8d\t%-14s\t%-3s\t%-26s\n", (i + 1), movie_name, show.getHall_id() + "号厅", show.getShow_time());
            }
            System.out.println("请选择场次（序号）：");
            int choice = input.nextInt();
            System.out.println("您已选择：\n" + choice + "\t" + movie_name + "\t" + showList.get(choice - 1).getHall_id() + "号厅\t" + showList.get(choice - 1).getShow_time());
            selfChooseSeat(showList.get(choice - 1));
        } else {
            System.out.println("改票失败");
        }

    }

    /**
     *
     * @param movie_name 用于查找的电影名
     * @param arrangeTicketList 用于输出的电影集合
     */
    private void printTickets(String movie_name, List<ArrangeTicket> arrangeTicketList) {
        System.out.println("序号\t\t\t电影名\t\t\t\t厅号\t\t排\t\t列\t\t时间");
        for (int i = 0; i < arrangeTicketList.size(); i++) {
            ArrangeTicket arrangeTicket = arrangeTicketList.get(i);
            System.out.format("%-8d\t%-14s\t%-3s\t%-3s\t%-3s\t%-26s\n", (i + 1), movie_name, arrangeTicket.getHall_id() + "号厅", "第" + arrangeTicket.getLine() + "排", "第" + arrangeTicket.getRow() + "列", arrangeTicket.getArrange_time());
        }
    }

    @Override
    /*
      根据电影名进行查询
      返回订票记录
      */
    public List<ArrangeTicket> SearchTickets(String movie_name) {
        //按电影名查找
        String sql = "select Arrange.Aud_id,Arrange.Hall_id,Arrange.Movie_id,Line,Row,Arrange_time\n"
                + "from Arrange join Movie on Arrange.Movie_id=Movie.Movie_id\n" +
                "where Arrange_time>? and movie_name=? and Aud_id=?";
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String[] param = {timestamp.toString(), movie_name, super.getAud_id() + ""};
        TicketDaoImpl ticket = new TicketDaoImpl();
        return ticket.search(sql, param);
    }

    @Override
    /*
      1.查询当前电影
      2.查询订票记录
      */
    public void Find() {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入查询类型 1.查询当前上映电影 2.查询订票记录");
        int c = input.nextInt();
        while (true) {
            if (c == 1) {
                getShow();
                break;
            } else if (c == 2) {
                getRecord();
                break;
            } else {
                System.out.println("请输入正确的数字(1-2)");
                c = input.nextInt();
            }
        }

    }

}
