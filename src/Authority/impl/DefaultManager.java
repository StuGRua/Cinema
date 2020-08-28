package Authority.impl;

import Authority.Manager;

import Service.impl.ManagerService;

/**
 * Created by diandian on 2019/7/7.
 */
public class DefaultManager implements Manager {
    private int Aud_id;
    public ManagerService managerservice =new ManagerService(Aud_id);
    public DefaultManager(int aud_id) {
        Aud_id = aud_id;
        managerservice.setAud_id(aud_id);
    }

    @Override
    public void search() {
        managerservice.ManageFind();
    }

    @Override
    public void addMovie() {
        managerservice.addMovie();
    }

    @Override
    public void delMovie() {
        managerservice.delMovie();
    }

    @Override
    public void addShow() {
        managerservice.addShow();
    }

    @Override
    public void delShow() {
        managerservice.delShow();
    }
}
