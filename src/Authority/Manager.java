package Authority;

/**
 * 影院管理员权限
 * 需具有查询,上/下架影片，新增/取消上映场次
 */
public interface Manager {
     void search();
     void addMovie();
     void delMovie();
     void addShow();
     void delShow();
}
