package User;

import Authority.Manager;
import Authority.Normal;


public class Role {
    private String description;// 角色名
    private Manager manager = null;//管理权限
    private Normal normal = null;//顾客权限

    //常用函数
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Manager getManager() {
        return manager;
    }

    public void setMange(Manager Mgr) {
        this.manager = Mgr;
    }

    public Normal getNormal() {
        return normal;
    }

    public void setNormal(Normal normal) {
        this.normal = normal;
    }

    /**
     * search
     */
    public void search() {
        if (normal == null && manager == null) {
            System.out.println("您还没有登录，请登陆后再操作");
        } else if (normal != null) {
            normal.search();
        } else {
            manager.search();
        }
    }

    /**
     * addMovie
     */
    public void addMovie() {
        if (manager == null) {
            System.out.println("您没有管理员权限");
            return;
        }
        manager.addMovie();
    }

    /**
     * delMovie
     */
    public void delMovie() {
        if (manager == null) {
            System.out.println("您没有管理员权限");
            return;
        }
        manager.delMovie();
    }

    /**
     * addShow
     */
    public void addShow() {
        if (manager == null) {
            System.out.println("您没有管理员权限");
            return;
        }
        manager.addShow();
    }

    /**
     * delShow
     */
    public void delShow() {
        if (manager == null) {
            System.out.println("您没有管理员权限");
            return;
        }
        manager.delShow();
    }

    /**
     * change
     */
    public void change() {
        if (normal == null) {
            System.out.println("您还没有登录，请登陆后再操作");
            return;
        }
        normal.change();
    }

    /**
     * order
     */
    public void order() {
        if (normal == null) {
            System.out.println("您还没有登录，请登陆后再操作");
            return;
        }
        normal.order();
    }

    /**
     * refund
     */
    public void refund() {
        if (normal == null) {
            System.out.println("您还没有登录，请登陆后再操作");
            return;
        }
        normal.refund();
    }

}
