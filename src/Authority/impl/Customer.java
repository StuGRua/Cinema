package Authority.impl;

import Authority.Normal;
import Service.impl.CustomerService;


public class Customer implements Normal {
    private int Aud_id;
    public CustomerService customerservice = new CustomerService(Aud_id);

    public Customer(int aud_id) {
        Aud_id = aud_id;
        customerservice.setAud_id(aud_id);
    }

    @Override
    public void search() {
        customerservice.find();
    }

    @Override
    public void order() {
        customerservice.order();
    }

    @Override
    public void refund() {
        customerservice.refund();
    }

    @Override
    public void change() {
        customerservice.change();
    }
}
