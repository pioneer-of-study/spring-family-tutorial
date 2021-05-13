import entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class BookTest {
    @Test
    public void testSave(){
        //1.创建一个Configuration对象，解析hibernate核心配置文件
        Configuration cfg = new Configuration().configure();
        //2.创建sessionFactory对象，解析映射信息并生成基本的sql
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        //3.创建session对象
        Session session = sessionFactory.openSession();
        //4.开启事务
        Transaction tx = session.beginTransaction();

        Book book = new Book();
        book.setName("朝花夕拾");
        book.setAuthor("鲁迅");
        book.setPrice(98);
        session.save(book);

        //提交事务
        tx.commit();
        //释放资源
        session.close();
    }
}
