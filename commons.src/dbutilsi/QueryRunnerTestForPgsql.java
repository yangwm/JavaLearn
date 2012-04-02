/**
 * 
 */
package dbutilsi;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.PageArgument;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.ConnectionUtil;

/**
 * @author yangwm in Jan 15, 2010 4:27:38 PM
 */
public class QueryRunnerTestForPgsql {
    /**
     * Logger for this class
     */
    private static final Log logger = LogFactory.getLog(QueryRunnerTestForPgsql.class);
        
    public static class DriverTester {
        public static void main(String[] args) {
            Connection conn = null;
            try {
                conn = ConnectionUtil.getConnectionForPgsql();
                
                logger.debug(conn.getMetaData().getDriverName()
                        + ", " + conn.getMetaData().getDriverVersion()
                        + ", " + conn.getMetaData().getDatabaseProductName()
                        + ", " + conn.getMetaData().getDatabaseProductVersion()
                        + ", " + conn.getMetaData().getDefaultTransactionIsolation());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
    }
    
    public static class ResultSetMetaDataTester {
        public static void main(String[] args) {
            Connection conn = null;
            try {
                conn = ConnectionUtil.getConnectionForPgsql();
                
                new QueryRunner().query(conn, 
                        "select id as bookId, title as bookTitle, authors as bookAuthors from book order by id", 
                        new ResultSetMetaDataHandler());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
        /*
         * 1:bookid:bookid:1-----2:booktitle:booktitle:C++ Primer Plus-----3:bookauthors:bookauthors:duck-----
         */
    }
    
    /**
     * 采用MapListHandler的方式性能比较好 
     * 
     * @author yangwm in May 25, 2009 9:07:12 PM
     */
    public static class MapListHandlerTester {
        public static void main(String[] args) {
            Connection conn = null;
            try {
                conn = ConnectionUtil.getConnectionForPgsql();
                
                List<Map<?, ?> > mapList = null;
                
                logger.debug("***Using MapListHandler***no PageArgument***no params");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                mapList = (List<Map<?, ?> >) new QueryRunner().query(conn, 
                        "select id as bookId, title as bookTitle, authors as bookAuthors from book order by id", 
                        new MapListHandler());
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- title ------------- authors ");
                for (Map<?, ?> vals : mapList) {
                    StringBuilder valsStr = new StringBuilder();
                    for (Object valKey : vals.keySet()) {
                        valsStr.append(valKey);
                        valsStr.append(":");
                        valsStr.append(vals.get(valKey));
                        valsStr.append("------");
                    }
                    logger.debug(valsStr);
                }
                
                logger.debug("***Using MapListHandler***PageArgument(1, 5)***no params");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                mapList = (List<Map<?, ?> >) new QueryRunner().query(conn, 
                        "select id, title, authors from book order by id", 
                        new MapListHandler(), new PageArgument(1, 5));
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- title ------------- authors ");
                for (Map<?, ?> vals : mapList) {
                    logger.debug(vals.get("id") + " ------------- " + 
                        vals.get("title") + " ------------- " + vals.get("authors"));
                }
                
                logger.debug("***Using MapListHandler***PageArgument(1, 5)***params{5}");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                mapList = (List<Map<?, ?> >) new QueryRunner().query(conn, 
                        "select id, title, authors from book where id>? order by id", 
                        new MapListHandler(), new PageArgument(1, 5), 5);
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- title ------------- authors ");
                for (Map<?, ?> vals : mapList) {
                    logger.debug(vals.get("id") + " ------------- " + 
                        vals.get("title") + " ------------- " + vals.get("authors"));
                }
                
                logger.debug("***Using MapListHandler***PageArgument(5, 5)***params{5}");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                mapList = (List<Map<?, ?> >) new QueryRunner().query(conn, 
                        "select id, title, authors from book where id>? order by id", 
                        new MapListHandler(), new PageArgument(5, 5), new Object[]{5});
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- title ------------- authors ");
                for (Map<?, ?> vals : mapList) {
                    logger.debug(vals.get("id") + " ------------- " + 
                        vals.get("title") + " ------------- " + vals.get("authors"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
    }                

    /**
     * 采用BeanListHandler性能比较低可能是因为采用反射的缘故
     * 
     * @author yangwm in May 25, 2009 9:07:25 PM
     */
    public static class BookListHandlerTester {
        public static void main(String[] args) {
            Connection conn = null;
            try {
                conn = ConnectionUtil.getConnectionForPgsql();
                
                List<Book> bookList = null;
                
                logger.debug("***Using BookListHandler***no PageArgument***no params");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                bookList = (List<Book>) new QueryRunner().query(conn, 
                        "select id, title, authors from book order by id", 
                        new BookListHandler());
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- title ------------- authors ");
                for (Book b : bookList) {
                    logger.debug(b.getId() + " ------------- " + 
                        b.getTitle() + " ------------- " + b.getAuthors());
                }
                
                logger.debug("***Using BookListHandler***PageArgument(1, 5)***no params");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                bookList = (List<Book>) new QueryRunner().query(conn, 
                        "select id, title, authors from book order by id", 
                        new BookListHandler(), new PageArgument(1, 5));
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- title ------------- authors ");
                for (Book b : bookList) {
                    logger.debug(b.getId() + " ------------- " + 
                        b.getTitle() + " ------------- " + b.getAuthors());
                }

                logger.debug("***Using BookListHandler***PageArgument(1, 5)***no params");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                bookList = (List<Book>) new QueryRunner().query(conn, 
                        "select id, title, authors from book where title like '%Java%' order by id", 
                        new BookListHandler(), new PageArgument(1, 5));
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- title ------------- authors ");
                for (Book b : bookList) {
                    logger.debug(b.getId() + " ------------- " + 
                        b.getTitle() + " ------------- " + b.getAuthors());
                }
                
                logger.debug("***Using BookListHandler***PageArgument(1, 5)***params{5}");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                bookList = (List<Book>) new QueryRunner().query(conn, 
                        "select id, title, authors from book where id>? order by id", 
                        new BookListHandler(), new PageArgument(1, 5), 5);
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- title ------------- authors ");
                for (Book b : bookList) {
                    logger.debug(b.getId() + " ------------- " + 
                        b.getTitle() + " ------------- " + b.getAuthors());
                }
                
                logger.debug("***Using BookListHandler***PageArgument(1, 5)***params{5555}");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                bookList = (List<Book>) new QueryRunner().query(conn, 
                        "select id, title, authors from book where id>? order by id", 
                        new BookListHandler(), new PageArgument(1, 5), 5555);
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- title ------------- authors ");
                for (Book b : bookList) {
                    logger.debug(b.getId() + " ------------- " + 
                        b.getTitle() + " ------------- " + b.getAuthors());
                }

                logger.debug("***Using BookListHandler***PageArgument(5, 5)***params{5}");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                bookList = (List<Book>) new QueryRunner().query(conn, 
                        "select id, title, authors from book where id>? order by id", 
                        new BookListHandler(), new PageArgument(5, 5), new Object[]{5});
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- title ------------- authors ");
                for (Book b : bookList) {
                    logger.debug(b.getId() + " ------------- " + 
                        b.getTitle() + " ------------- " + b.getAuthors());
                }
                
                logger.debug("***Using BookListHandler***PageArgument(50, 5)***params{5}");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                bookList = (List<Book>) new QueryRunner().query(conn, 
                        "select id, title, authors from book where id>? order by id", 
                        new BookListHandler(), new PageArgument(50, 5), new Object[]{5});
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- title ------------- authors ");
                for (Book b : bookList) {
                    logger.debug(b.getId() + " ------------- " + 
                        b.getTitle() + " ------------- " + b.getAuthors());
                }
                
                logger.debug("***Using BookListHandler***PageArgument(50, 5)***params{23}");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                bookList = (List<Book>) new QueryRunner().query(conn, 
                        "select id, title, authors from book where id>? order by id", 
                        new BookListHandler(), new PageArgument(50, 5), new Object[]{23});
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- title ------------- authors ");
                for (Book b : bookList) {
                    logger.debug(b.getId() + " ------------- " + 
                        b.getTitle() + " ------------- " + b.getAuthors());
                }
                
                logger.debug("***Using BookListHandler***PageArgument(50, 5)***params{27}");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                bookList = (List<Book>) new QueryRunner().query(conn, 
                        "select id, title, authors from book where id>? order by id", 
                        new BookListHandler(), new PageArgument(50, 5), new Object[]{27});
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- title ------------- authors ");
                for (Book b : bookList) {
                    logger.debug(b.getId() + " ------------- " + 
                        b.getTitle() + " ------------- " + b.getAuthors());
                }
                
                logger.debug("***Using BookListHandler***PageArgument(50, 5)***params{5555}");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                bookList = (List<Book>) new QueryRunner().query(conn, 
                        "select id, title, authors from book where id>? order by id", 
                        new BookListHandler(), new PageArgument(50, 5), new Object[]{5555});
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- title ------------- authors ");
                for (Book b : bookList) {
                    logger.debug(b.getId() + " ------------- " + 
                        b.getTitle() + " ------------- " + b.getAuthors());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
    }
    
}

/*
BookListHandlerTester

***Using BookListHandler***no PageArgument***no params
id ------------- title ------------- authors 
1 ------------- c++ primer plus ------------- duck
2 ------------- thinking in java ------------- bruce eckel
3 ------------- a ------------- a of authors
4 ------------- b ------------- b of authors
5 ------------- c ------------- c of authors
6 ------------- d ------------- d of authors
7 ------------- e ------------- e of authors
8 ------------- f ------------- f of authors
9 ------------- g ------------- g of authors
10 ------------- h ------------- h of authors
11 ------------- i ------------- i of authors
12 ------------- j ------------- j of authors
13 ------------- k ------------- k of authors
14 ------------- l ------------- l of authors
15 ------------- m ------------- m of authors
16 ------------- n ------------- n of authors
17 ------------- o ------------- o of authors
18 ------------- p ------------- p of authors
19 ------------- q ------------- q of authors
20 ------------- r ------------- r of authors
21 ------------- s ------------- s of authors
22 ------------- t ------------- t of authors
23 ------------- u ------------- u of authors
24 ------------- v ------------- v of authors
25 ------------- w ------------- w of authors
26 ------------- x ------------- x of authors
27 ------------- y ------------- y of authors
28 ------------- z ------------- z of authors
***Using BookListHandler***PageArgument(1, 5)***no params
>>>generateCountSql sql=select id, title, authors from book order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver
<<<generateCountSql result.toString()=select count(*) from book 
>>>generatePageSql sql=select id, title, authors from book order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver, pageArg=PageArgument{curPage=1, pageSize=5, totalRow=28, totalPage=6}
<<<generatePageSql pageSql=select id, title, authors from book order by id limit 5 offset 0
id ------------- title ------------- authors 
1 ------------- c++ primer plus ------------- duck
2 ------------- thinking in java ------------- bruce eckel
3 ------------- a ------------- a of authors
4 ------------- b ------------- b of authors
5 ------------- c ------------- c of authors
***Using BookListHandler***PageArgument(1, 5)***params{5}
>>>generateCountSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver
<<<generateCountSql result.toString()=select count(*) from book where id>? 
>>>generatePageSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver, pageArg=PageArgument{curPage=1, pageSize=5, totalRow=23, totalPage=5}
<<<generatePageSql pageSql=select id, title, authors from book where id>? order by id limit 5 offset 0
id ------------- title ------------- authors 
6 ------------- d ------------- d of authors
7 ------------- e ------------- e of authors
8 ------------- f ------------- f of authors
9 ------------- g ------------- g of authors
10 ------------- h ------------- h of authors
***Using BookListHandler***PageArgument(1, 5)***params{5555}
>>>generateCountSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver
<<<generateCountSql result.toString()=select count(*) from book where id>? 
>>>generatePageSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver, pageArg=PageArgument{curPage=1, pageSize=5, totalRow=0, totalPage=1}
<<<generatePageSql pageSql=select id, title, authors from book where id>? order by id limit 5 offset 0
id ------------- title ------------- authors 
***Using BookListHandler***PageArgument(5, 5)***params{5}
>>>generateCountSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver
<<<generateCountSql result.toString()=select count(*) from book where id>? 
>>>generatePageSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver, pageArg=PageArgument{curPage=5, pageSize=5, totalRow=23, totalPage=5}
<<<generatePageSql pageSql=select id, title, authors from book where id>? order by id limit 5 offset 20
id ------------- title ------------- authors 
26 ------------- x ------------- x of authors
27 ------------- y ------------- y of authors
28 ------------- z ------------- z of authors
***Using BookListHandler***PageArgument(50, 5)***params{5}
>>>generateCountSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver
<<<generateCountSql result.toString()=select count(*) from book where id>? 
>>>generatePageSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver, pageArg=PageArgument{curPage=5, pageSize=5, totalRow=23, totalPage=5}
<<<generatePageSql pageSql=select id, title, authors from book where id>? order by id limit 5 offset 20
id ------------- title ------------- authors 
26 ------------- x ------------- x of authors
27 ------------- y ------------- y of authors
28 ------------- z ------------- z of authors
***Using BookListHandler***PageArgument(50, 5)***params{23}
>>>generateCountSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver
<<<generateCountSql result.toString()=select count(*) from book where id>? 
>>>generatePageSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver, pageArg=PageArgument{curPage=1, pageSize=5, totalRow=5, totalPage=1}
<<<generatePageSql pageSql=select id, title, authors from book where id>? order by id limit 5 offset 0
id ------------- title ------------- authors 
24 ------------- v ------------- v of authors
25 ------------- w ------------- w of authors
26 ------------- x ------------- x of authors
27 ------------- y ------------- y of authors
28 ------------- z ------------- z of authors
***Using BookListHandler***PageArgument(50, 5)***params{27}
>>>generateCountSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver
<<<generateCountSql result.toString()=select count(*) from book where id>? 
>>>generatePageSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver, pageArg=PageArgument{curPage=1, pageSize=5, totalRow=1, totalPage=1}
<<<generatePageSql pageSql=select id, title, authors from book where id>? order by id limit 5 offset 0
id ------------- title ------------- authors 
28 ------------- z ------------- z of authors
***Using BookListHandler***PageArgument(50, 5)***params{5555}
>>>generateCountSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver
<<<generateCountSql result.toString()=select count(*) from book where id>? 
>>>generatePageSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=PostgreSQL Native Driver, pageArg=PageArgument{curPage=1, pageSize=5, totalRow=0, totalPage=1}
<<<generatePageSql pageSql=select id, title, authors from book where id>? order by id limit 5 offset 0
id ------------- title ------------- authors 

*/
