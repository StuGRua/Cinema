package Authority.impl;

import Authority.Normal;
import Service.impl.CustomService;


public class DefaultCustomer implements Normal {
    private int Aud_id;
    public CustomService customservice = new CustomService(Aud_id);

    public DefaultCustomer(int aud_id) {
        Aud_id = aud_id;
        customservice.setAud_id(aud_id);
    }

    @Override
    public void search() {
        customservice.find();
    }

    @Override
    public void order() {
        customservice.order();
    }

    @Override
    public void refund() {
        customservice.refund();
    }

    @Override
    public void change() {
        customservice.change();
    }
}
