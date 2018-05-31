package ap.com.text.base;

/**
 * 类描述：返回数据解析基类
 * 创建人：swallow.li
 * 创建时间：
 * Email: swallow.li@kemai.cn
 * 修改备注：
 */
public class BaseEntry {

    public int stats;
    public String message;
    public int totalPage;
    public String others;
    public String totalPageList;

    public int getStats() {
        return stats;
    }

    public void setStats(int stats) {
        this.stats = stats;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getTotalPageList() {
        return totalPageList;
    }

    public void setTotalPageList(String totalPageList) {
        this.totalPageList = totalPageList;
    }
}
