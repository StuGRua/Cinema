package Authority;
import User.Normal;
import User._Manager;


/* *
 * @Author 朝喜
 * @Description 权限规则类，限制各类型用户所做操作
 * @Date  2020-8-12
 **/

public class Role {
    private String description;// 角色名
    private _Manager manager = null;//管理权限
    private Normal normal = null;//顾客权限

    //常用函数
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public _Manager getManager() {
        return manager;
    }

    public void setManager(_Manager Mgr) {
        this.manager = Mgr;
    }

    public Normal getNormal() {
        return normal;
    }

    public void setNormal(Normal normal) {
        this.normal = normal;
    }

    /**
     * 查询（实现分用户类型查询）
     */
    public void Search() {
        if (normal == null && manager == null) {
            System.out.println("您还没有登录，请登陆后再操作");
        } else if (normal != null) {
            normal.Search();
        } else {
            manager.Search();
        }
    }

    /**
     * addMovie
     */
    public void AddMovie() {
        if (manager == null) {
            System.out.println("您没有管理员权限");
            return;
        }
        manager.AddMovie();
    }

    /**
     * delMovie
     */
    public void DelMovie() {
        if (manager == null) {
            System.out.println("您没有管理员权限");
            return;
        }
        manager.DelMovie();
    }

    /**
     * addShow
     */
    public void AddShow() {
        if (manager == null) {
            System.out.println("您没有管理员权限");
            return;
        }
        manager.AddShow();
    }

    /**
     * delShow
     */
    public void DelShow() {
        if (manager == null) {
            System.out.println("您没有管理员权限");
            return;
        }
        manager.DelShow();
    }


    /**
     * change
     */
    public void Change() {
        if (normal == null) {
            System.out.println("您还没有登录，请登陆后再操作");
            return;
        }
        normal.Change();
    }

    /**
     * order
     */
    public void Order() {
        if (normal == null) {
            System.out.println("您还没有登录，请登陆后再操作");
            return;
        }
        normal.Order();
    }

    /**
     * refund
     */
    public void Refund() {
        if (normal == null) {
            System.out.println("您还没有登录，请登陆后再操作");
            return;
        }
        normal.Refund();
    }

}
