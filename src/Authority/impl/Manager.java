package Authority.impl;

import Authority._Manager;
import Service.impl.ManagerService;


public class Manager implements _Manager {
    private int Aud_id;
    public ManagerService managerservice = new ManagerService(Aud_id);

    public Manager(int aud_id) {
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
