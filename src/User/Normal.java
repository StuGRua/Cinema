package User;

/* *
 * @Author StuG
 * @Description 顾客权限  订票，退票，查询，改签
 * @Date  2020-8-1
 **/
public interface Normal {
    void Order();

    void Refund();

    void Search();

    void Change();
    //忘记密码整合至User
    //void forget();
}
