package User;

/*
 * @Author StuG
 * @Description 影院管理员权限，需具有查询,上/下架影片，新增/取消上映场次
 * @Date  2020-8-1
 **/

public interface _Manager {
    void Search();

    void AddMovie();

    void DelMovie();

    void AddShow();

    void DelShow();

}
