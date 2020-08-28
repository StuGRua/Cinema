package Authority;

/**
 * 顾客权限  订票，退票，查询，改签
 */
public interface Normal {
    void order();
    void refund();
    void search();
    void change();
}
