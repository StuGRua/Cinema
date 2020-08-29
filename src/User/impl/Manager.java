package User.impl;

import User._Manager;
import Service.impl.ManagerService;

/* *
 * @Author StuG
 * @Description 管理功能表
 * @Date  2020-8-1
 **/
public class Manager implements _Manager {
    private int Aud_id;
    private String Aud_type="Manager";
    public ManagerService managerservice = new ManagerService(Aud_id,Aud_type);

    public Manager(int aud_id) {
        Aud_id = aud_id;
        managerservice.setAud_id(aud_id);
    }

    @Override
    public void Search() {

        managerservice.ManageFind();
    }

    @Override
    public void AddMovie() {

        managerservice.AddMovie();
    }

    @Override
    public void DelMovie() {

        managerservice.DelMovie();
    }

    @Override
    public void AddShow() {

        managerservice.AddShow();
    }

    @Override
    public void DelShow() {
        managerservice.DelShow();
    }

}
