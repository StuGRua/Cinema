package User.impl;

import User.Normal;
import Service.impl.CustomerService;

/* *
 * @Author StuG
 * @Description 用户功能类
 * @Date  2020-8-1
 **/

public class Customer implements Normal {
    private int Aud_id;
    String Aud_type="normal";
    public CustomerService customerservice = new CustomerService(Aud_id,Aud_type);

    public Customer(int aud_id) {
        Aud_id = aud_id;
        customerservice.setAud_id(aud_id);
    }

    @Override
    public void Search() {
        customerservice.Find();
    }

    @Override
    public void Order() {
        customerservice.Order();
    }

    @Override
    public void Refund() {
        customerservice.Refund();
    }

    @Override
    public void Change() {
        customerservice.Change();
    }

    //忘记密码功能整合至User模块
    /*@Override
    public void forget()
    {

    }*/
}