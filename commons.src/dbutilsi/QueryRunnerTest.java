/**
 * 
 */
package dbutilsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.PageArgument;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.ConnectionUtil;
import util.EntityUtil;

/**
 * 采用MapListHandler的方式即第一种方式性能要好的多
 * 采用BeanListHandler性能比较低可能是因为采用反射的缘故
 * 采用自实现Handler性能好 
 * 
 * @author yangwm in May 25, 2009 8:43:47 PM
 */
public class QueryRunnerTest {
    /**
     * Logger for this class
     */
    private static final Log logger = LogFactory.getLog(QueryRunnerTest.class);
        
    public static class DriverTester {
        public static void main(String[] args) {
            Connection conn = null;
            try {
                conn = ConnectionUtil.getConnection();
                
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
                conn = ConnectionUtil.getConnection();
                
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
         * 1:BOOKID:BOOKID:1-----2:BOOKTITLE:BOOKTITLE:C++ Primer Plus-----3:BOOKAUTHORS:BOOKAUTHORS:duck-----
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
                conn = ConnectionUtil.getConnection();
                
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
    public static class BeanListHandlerTester {
        public static void main(String[] args) {
            Connection conn = null;
            try {
                conn = ConnectionUtil.getConnection();
                
                List<Book> bookList = null;
                
                logger.debug("***Using BookListHandler***no PageArgument***no params");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                bookList = (List<Book>) new QueryRunner().query(conn, 
                        "select id, title, authors from book order by id", 
                        new BeanListHandler(Book.class));
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
                        new BeanListHandler(Book.class), new PageArgument(1, 5));
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
                        new BeanListHandler(Book.class), new PageArgument(1, 5), 5);
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
                        new BeanListHandler(Book.class), new PageArgument(5, 5), new Object[]{5});
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
    
    /**
     * 采用自实现Handler性能好--BookListHandler  
     * 
     * @author yangwm in May 25, 2009 9:07:25 PM
     */
    public static class BookListHandlerTester {
        public static void main(String[] args) {
            Connection conn = null;
            try {
                conn = ConnectionUtil.getConnection();
                
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

                System.out.println("***Using BookListHandler***PageArgument(1, 5)***no params");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                bookList = (List<Book>) new QueryRunner().query(conn, 
                        "select id, title, authors from book where title like '%Java%' order by id", 
                        new BookListHandler(), new PageArgument(1, 5));
                //以下是处理代码，可以抽取出来
                System.out.println("id ------------- title ------------- authors ");
                for (Book b : bookList) {
                    System.out.println(b.getId() + " ------------- " + 
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
    
    /**
     * 标量Handler处理 
     * 
     * @author yangwm in May 30, 2009 8:03:58 PM
     */
    public static class ScalarHandlerTester {
        public static void main(String[] args) {
            Connection conn = null;
            try {
                conn = ConnectionUtil.getConnection();
                
                String sql = "select book_seq.nextval from dual";
                long bookId = ((Number) new QueryRunner().query(conn, sql, new ScalarHandler()))
                    .longValue();
                logger.debug(bookId);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
    }
    
    /**
     * book增加测试 
     * 
     * @author yangwm in May 25, 2009 9:07:25 PM
     */
    public static class BookAddTester {
        public static void main(String[] args) {
            Connection conn = null;
            try {
                conn = ConnectionUtil.getConnection();
                
                Book book = new Book();
                book.setTitle("testTitle1");
                book.setAuthors("testAuthors1");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 300; i++) {
                    sb.append("testDescribe1");
                }
                book.setDescription(sb.toString());
                logger.debug("book.getDescribe().length()=" + book.getDescription().length());
                book.setCreateBy("yangwm");
                book.setCreateDate(new java.sql.Timestamp(new Date().getTime()));
                
                logger.debug(book.getCreateDate());
                
                String sql = "insert into BOOK (TITLE, AUTHORS, describe, create_by, create_date ) "
                    + "values (?, ?, ?, ?, ?)";
                
//                new QueryRunner().update(conn, sql, new Object[] {
//                    book.getTitle(),
//                    book.getAuthors(),
//                    book.getDescribe(),
//                    book.getCreateBy(),
//                    book.getCreateDate(),
//                });

                new BookAddTester().add(book, sql, conn);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
        
        public void add(Book book, String sql, Connection conn) {
            PreparedStatement pstm = null;
            try {
                
                pstm = conn.prepareStatement(sql);
                
                pstm.setString(1, book.getTitle());
                pstm.setString(2, book.getAuthors());
                
                /*byte[] messageBytes = book.getDescribe().toString().getBytes();
                int messageBytesLength = messageBytes.length;
                logger.debug("messageBytesLength)=" + messageBytesLength);
                InputStreamReader isr = new InputStreamReader(new java.io.ByteArrayInputStream(messageBytes));
                pstm.setCharacterStream(3, isr, messageBytesLength);*/
                
                pstm.setString(3, book.getDescription());
                pstm.setString(4, book.getCreateBy());
                pstm.setTimestamp(5, (Timestamp) book.getCreateDate());
                pstm.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstm != null) {
                        pstm.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }  
        }
        
    }
    
    /**
     * 时间测试 
     * 
     * @author yangwm in May 25, 2009 9:07:25 PM
     */
    public static class DateTester {
        public static void main(String[] args) {
            logger.debug(new java.util.Date(new java.sql.Date(new java.util.Date().getTime()).getTime()));
            logger.debug(new java.sql.Timestamp(0));
        }
        
    }
    
    /**
     * book批量增加测试 
     * 
     * @author yangwm in Jan 5, 2010 5:30:03 PM
     */
    public static class BookBatchAddTester {
        public static void main(String[] args) {
            Connection conn = null;
            try {
                conn = ConnectionUtil.getConnection();
                
                List<Book> bookList = new ArrayList<Book>();
                
                Book book = new Book();
                book.setTitle("testTitleBatch1");
                book.setAuthors("testAuthorsBatch1");
                book.setDescription("testDescribeBatch1");
                book.setCreateBy("yangwm");
                book.setCreateDate(new java.sql.Timestamp(new Date().getTime()));
                bookList.add(book);
                
                new BookBatchAddTester().add(conn, bookList);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
        
        public void add(Connection conn, List<Book> bookList) {
            logger.debug("add(" + bookList.size() + ")");
            
            StringBuilder sb = new StringBuilder();
            sb.append("insert into BOOK (TITLE, AUTHORS, describe, create_by, create_date ) ")
                    .append("values (?, ?, ?, ?, ?)");
            
            Object[][] params = new Object[bookList.size()][5];
            for (int i = 0; i < params.length; i++) {
                Book book = bookList.get(i);
                params[i][0] = book.getTitle();
                params[i][1] = book.getCreateBy();
                params[i][2] = book.getAuthors();
                params[i][3] = book.getCreateBy();
                params[i][4] = book.getCreateDate();

            }
            //logger.debug("EntityUtil.toString(params)=" + EntityUtil.toString(params));
            
            logger.debug(sb.toString());
            try {
                new QueryRunner().batch(conn, sb.toString(), params);
            } catch (Exception e) {
                e.printStackTrace();
            } 
            
        }
    }
    
    /**
     * book批量修改测试 
     * 
     * @author yangwm in Jan 5, 2010 5:30:03 PM
     */
    public static class BookBatchModifyTester {
        public static void main(String[] args) {
            Connection conn = null;
            try {
                conn = ConnectionUtil.getConnection();
                
                List<Book> bookList = new ArrayList<Book>();
                
                Book book = new Book();
                book.setId(18);
                book.setAuthors("p of authors modify");
                book.setUpdateBy("yangwm");
                book.setUpdateDate(new java.sql.Timestamp(new Date().getTime()));
                bookList.add(book);
                
                new BookBatchModifyTester().modify(conn, bookList);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
        
        public void modify(Connection conn, List<Book> bookList) {
            logger.debug("modify(" + bookList.size() + ")");
            
            StringBuilder sb = new StringBuilder () ;
            sb.append( "update BOOK set " )
            .append(" AUTHORS=?, update_by=?, update_date=? ")
            .append(" where id=?") ;
            
            Object[][] params = new Object[bookList.size()][4];
            for (int i = 0; i < params.length; i++) {
                Book book = bookList.get(i);
                params[i][0] = book.getAuthors();
                params[i][1] = book.getUpdateBy();
                params[i][2] = book.getUpdateDate();
    
                params[i][3] = book.getId();
            }
            //logger.debug("EntityUtil.toString(params)=" + EntityUtil.toString(params));
            
            logger.debug(sb.toString());
            try {
                new QueryRunner().batch(conn, sb.toString(), params);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }

    
    
    /**
     * 采用MapListHandler的方式性能比较好--CmdLogMapListHandler 
     * 
     * @author yangwm in Jan 26, 2010 10:24:59 AM
     */
    public static class CmdLogMapListHandler {
        public static void main(String[] args) {
            Connection conn = null;
            try {
                conn = ConnectionUtil.getConnection();
                
                List<Map<?, ?> > mapList = null;
                PageArgument pageArgument = null;

                pageArgument = new PageArgument(1, 10);
                logger.debug("***Using CmdLogMapListHandler***" + pageArgument + "***no params");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                mapList = (List<Map<?, ?> >) new QueryRunner().query(conn, 
                        "select id, access_origin_addr, cmd from command_detail_log order by id", 
                        new MapListHandler(), pageArgument);
                logger.debug(pageArgument);
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- access_origin_addr ------------- cmd ");
                for (Map<?, ?> vals : mapList) {
                    logger.debug(vals.get("id") + " ------------- " + 
                        vals.get("access_origin_addr") + " ------------- " + vals.get("cmd"));
                }
                
                pageArgument = new PageArgument(50000, 10);
                logger.debug("***Using CmdLogMapListHandler***" + pageArgument + "***no params");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                mapList = (List<Map<?, ?> >) new QueryRunner().query(conn, 
                        "select id, access_origin_addr, cmd from command_detail_log order by id", 
                        new MapListHandler(), pageArgument);
                logger.debug(pageArgument);
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- access_origin_addr ------------- cmd ");
                for (Map<?, ?> vals : mapList) {
                    logger.debug(vals.get("id") + " ------------- " + 
                        vals.get("access_origin_addr") + " ------------- " + vals.get("cmd"));
                }
                
                pageArgument = new PageArgument(100000, 10);
                logger.debug("***Using CmdLogMapListHandler***" + pageArgument + "***no params");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                mapList = (List<Map<?, ?> >) new QueryRunner().query(conn, 
                        "select id, access_origin_addr, cmd from command_detail_log order by id", 
                        new MapListHandler(), pageArgument);
                logger.debug(pageArgument);
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- access_origin_addr ------------- cmd ");
                for (Map<?, ?> vals : mapList) {
                    logger.debug(vals.get("id") + " ------------- " + 
                        vals.get("access_origin_addr") + " ------------- " + vals.get("cmd"));
                }
                
                pageArgument = new PageArgument(133668, 10);
                logger.debug("***Using CmdLogMapListHandler***" + pageArgument + "***no params");
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                mapList = (List<Map<?, ?> >) new QueryRunner().query(conn, 
                        "select id, access_origin_addr, cmd from command_detail_log order by id", 
                        new MapListHandler(), pageArgument);
                logger.debug(pageArgument);
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- access_origin_addr ------------- cmd ");
                for (Map<?, ?> vals : mapList) {
                    logger.debug(vals.get("id") + " ------------- " + 
                        vals.get("access_origin_addr") + " ------------- " + vals.get("cmd"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
    }
    
    /**
     * 采用MapListHandler的方式性能比较好--SystemLogMapListHandler 
     * 
     * @author yangwm in Jan 26, 2010 10:24:59 AM
     */
    public static class SystemLogMapListHandler {
        public static void main(String[] args) {
            Connection conn = null;
            try {
                conn = ConnectionUtil.getConnection();
                
                List<Map<?, ?> > mapList = null;
                PageArgument pageArgument = null;
                Object[] params = new Object[]{};
                
                // userid='888888' username='测试'  opertime='2009-12-24 16:09:19'
                pageArgument = new PageArgument(1, 10);
                params = new Object[]{"2009-12-22 13:04:04", "2009-12-23 13:04:04"};
                logger.debug("***Using SystemLogMapListHandler***" + pageArgument + "***" + EntityUtil.toString(params));
                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
                mapList = (List<Map<?, ?> >) new QueryRunner().query(conn, 
                        "select * from ams_system_log t where t.opertime >= ? and t.opertime <= ? order by t.opertime desc", 
                        new MapListHandler(), pageArgument, params);
                logger.debug(pageArgument);
                //以下是处理代码，可以抽取出来
                logger.debug("id ------------- username ------------- operation ");
                for (Map<?, ?> vals : mapList) {
                    logger.debug(vals.get("id") + " ------------- " + 
                        vals.get("username") + " ------------- " + vals.get("operation"));
                }

//                pageArgument = new PageArgument(1, 10);
//                logger.debug("***Using SystemLogMapListHandler***" + pageArgument + "***no params");
//                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
//                mapList = (List<Map<?, ?> >) new QueryRunner().query(conn, 
//                        "select id, username, operation from ams_system_log order by id", 
//                        new MapListHandler(), pageArgument);
//                logger.debug(pageArgument);
//                //以下是处理代码，可以抽取出来
//                logger.debug("id ------------- username ------------- operation ");
//                for (Map<?, ?> vals : mapList) {
//                    logger.debug(vals.get("id") + " ------------- " + 
//                        vals.get("username") + " ------------- " + vals.get("operation"));
//                }
//                
//                pageArgument = new PageArgument(1000000, 10);
//                logger.debug("***Using SystemLogMapListHandler***" + pageArgument + "***no params");
//                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
//                mapList = (List<Map<?, ?> >) new QueryRunner().query(conn, 
//                        "select id, username, operation from ams_system_log order by id", 
//                        new MapListHandler(), pageArgument);
//                logger.debug(pageArgument);
//                //以下是处理代码，可以抽取出来
//                logger.debug("id ------------- username ------------- operation ");
//                for (Map<?, ?> vals : mapList) {
//                    logger.debug(vals.get("id") + " ------------- " + 
//                        vals.get("username") + " ------------- " + vals.get("operation"));
//                }
//                
//                pageArgument = new PageArgument(2000000, 10);
//                logger.debug("***Using SystemLogMapListHandler***" + pageArgument + "***no params");
//                //以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理
//                mapList = (List<Map<?, ?> >) new QueryRunner().query(conn, 
//                        "select id, username, operation from ams_system_log order by id", 
//                        new MapListHandler(), pageArgument);
//                logger.debug(pageArgument);
//                //以下是处理代码，可以抽取出来
//                logger.debug("id ------------- username ------------- operation ");
//                for (Map<?, ?> vals : mapList) {
//                    logger.debug(vals.get("id") + " ------------- " + 
//                        vals.get("username") + " ------------- " + vals.get("operation"));
//                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
    }
}

/*
10:53:06,406 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(647)]  - ***Using SystemLogMapListHandler***PageArgument{curPage=1, pageSize=10, totalRow=0, totalPage=1}***[Ljava.lang.Object;[{888888,测试,2009-12-24%}]
10:53:06,859 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(652)]  - PageArgument{curPage=1, pageSize=10, totalRow=34, totalPage=4}
10:53:06,859 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(654)]  - id ------------- username ------------- operation 
10:53:06,859 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3288059 ------------- 测试 ------------- 登录系统
10:53:06,859 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3288075 ------------- 测试 ------------- 登录系统
10:53:06,859 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3288077 ------------- 测试 ------------- 登录系统
10:53:06,859 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3288095 ------------- 测试 ------------- 登录系统
10:53:06,859 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3288097 ------------- 测试 ------------- 登录系统
10:53:06,859 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3288143 ------------- 测试 ------------- 登录系统
10:53:06,859 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3288181 ------------- 测试 ------------- 登录系统
10:53:06,859 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3288182 ------------- 测试 ------------- 登录系统
10:53:06,859 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3288194 ------------- 测试 ------------- 登录系统
10:53:06,859 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3288218 ------------- 测试 ------------- 登录系统

*/

/*
11:03:29,093 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(647)]  - ***Using SystemLogMapListHandler***PageArgument{curPage=3, pageSize=10, totalRow=0, totalPage=1}***[Ljava.lang.Object;[{888888}]
11:04:42,234 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(652)]  - PageArgument{curPage=3, pageSize=10, totalRow=520, totalPage=52}
11:04:42,234 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(654)]  - id ------------- username ------------- operation 
11:04:42,234 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3287800 ------------- 测试 ------------- 登录系统
11:04:42,234 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3287805 ------------- 测试 ------------- 登录系统
11:04:42,234 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3287813 ------------- 测试 ------------- 登录系统
11:04:42,250 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3287817 ------------- 测试 ------------- 登录系统
11:04:42,250 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3287825 ------------- 测试 ------------- 登录系统
11:04:42,250 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3287854 ------------- 测试 ------------- 登录系统
11:04:42,250 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3287864 ------------- 测试 ------------- 登录系统
11:04:42,250 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3287873 ------------- 测试 ------------- 登录系统
11:04:42,250 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3287891 ------------- 测试 ------------- 登录系统
11:04:42,250 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3287920 ------------- 测试 ------------- 登录系统

*/

/*
10:34:48,953 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(643)]  - ***Using SystemLogMapListHandler***PageArgument{curPage=1, pageSize=10, totalRow=0, totalPage=1}***no params
10:35:31,937 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(648)]  - PageArgument{curPage=1, pageSize=10, totalRow=20273572, totalPage=2027358}
10:35:31,937 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(650)]  - id ------------- username ------------- operation 
10:35:31,937 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(652)]  - 3286659 ------------- sysadmin ------------- 修改
10:35:31,937 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(652)]  - 3286660 ------------- sysadmin ------------- 修改
10:35:31,937 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(652)]  - 3286661 ------------- sysadmin ------------- 修改
10:35:31,937 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(652)]  - 3286662 ------------- sysadmin ------------- 修改
10:35:31,937 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(652)]  - 3286663 ------------- sysadmin ------------- 修改
10:35:31,937 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(652)]  - 3286664 ------------- sysadmin ------------- 修改
10:35:31,937 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(652)]  - 3286665 ------------- sysadmin ------------- 修改
10:35:31,937 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(652)]  - 3286666 ------------- sysadmin ------------- 修改
10:35:31,937 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(652)]  - 3286667 ------------- sysadmin ------------- 修改
10:35:31,937 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(652)]  - 3286668 ------------- sysadmin ------------- 修改
10:35:31,937 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(657)]  - ***Using SystemLogMapListHandler***PageArgument{curPage=1000000, pageSize=10, totalRow=0, totalPage=1}***no params
10:36:40,515 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(662)]  - PageArgument{curPage=1000000, pageSize=10, totalRow=20273572, totalPage=2027358}
10:36:40,515 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(664)]  - id ------------- username ------------- operation 
10:36:40,515 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(666)]  - 13286649 ------------- test333 ------------- test333
10:36:40,515 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(666)]  - 13286650 ------------- test333 ------------- test333
10:36:40,515 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(666)]  - 13286651 ------------- test333 ------------- test333
10:36:40,515 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(666)]  - 13286652 ------------- test333 ------------- test333
10:36:40,515 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(666)]  - 13286653 ------------- test333 ------------- test333
10:36:40,515 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(666)]  - 13286654 ------------- test333 ------------- test333
10:36:40,515 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(666)]  - 13286655 ------------- test333 ------------- test333
10:36:40,515 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(666)]  - 13286656 ------------- test333 ------------- test333
10:36:40,515 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(666)]  - 13286657 ------------- test333 ------------- test333
10:36:40,515 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(666)]  - 13286658 ------------- test333 ------------- test333
10:36:40,515 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(671)]  - ***Using SystemLogMapListHandler***PageArgument{curPage=2000000, pageSize=10, totalRow=0, totalPage=1}***no params
10:38:02,546 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(676)]  - PageArgument{curPage=2000000, pageSize=10, totalRow=20273572, totalPage=2027358}
10:38:02,546 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(678)]  - id ------------- username ------------- operation 
10:38:02,546 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(680)]  - 23286649 ------------- test333 ------------- test333
10:38:02,546 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(680)]  - 23286650 ------------- test333 ------------- test333
10:38:02,562 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(680)]  - 23286651 ------------- test333 ------------- test333
10:38:02,562 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(680)]  - 23286652 ------------- test333 ------------- test333
10:38:02,562 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(680)]  - 23286653 ------------- test333 ------------- test333
10:38:02,562 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(680)]  - 23286654 ------------- test333 ------------- test333
10:38:02,562 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(680)]  - 23286655 ------------- test333 ------------- test333
10:38:02,562 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(680)]  - 23286656 ------------- test333 ------------- test333
10:38:02,562 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(680)]  - 23286657 ------------- test333 ------------- test333
10:38:02,562 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(680)]  - 23286658 ------------- test333 ------------- test333

*/

/*
19:14:52,843 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(566)]  - ***Using BookListHandler***PageArgument{curPage=1, pageSize=10, totalRow=0, totalPage=1}***no params
19:14:53,390 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(571)]  - PageArgument{curPage=1, pageSize=10, totalRow=1336677, totalPage=133668}
19:14:53,390 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(573)]  - id ------------- title ------------- authors 
19:14:53,406 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(575)]  - 1 ------------- 100.9.0.41 ------------- pwd 

19:14:53,406 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(575)]  - 2 ------------- 100.9.0.41 ------------- l s-l 

19:14:53,406 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(575)]  - 3 ------------- 100.9.0.41 ------------- ls -l 

19:14:53,406 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(575)]  - 4 ------------- 100.9.0.41 ------------- dbaccess 

19:14:53,406 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(575)]  - 5 ------------- 100.9.0.41 ------------- ls 

19:14:53,406 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(575)]  - 6 ------------- 100.9.0.41 ------------- ftp 11.0.14.104 

19:14:53,406 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(575)]  - 7 ------------- 100.9.0.41 ------------- resercs2 

19:14:53,406 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(575)]  - 8 ------------- 100.9.0.41 ------------- ls 

19:14:53,406 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(575)]  - 9 ------------- 100.9.0.41 ------------- ls 

19:14:53,406 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(575)]  - 10 ------------- 100.9.0.41 ------------- cd wangtao 

19:14:53,406 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(580)]  - ***Using BookListHandler***PageArgument{curPage=50000, pageSize=10, totalRow=0, totalPage=1}***no params
19:14:54,359 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(585)]  - PageArgument{curPage=50000, pageSize=10, totalRow=1336677, totalPage=133668}
19:14:54,359 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(587)]  - id ------------- title ------------- authors 
19:14:54,359 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(589)]  - 499991 ------------- 100.9.0.41 ------------- ls 

19:14:54,359 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(589)]  - 499992 ------------- 100.9.0.41 ------------- ll 

19:14:54,359 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(589)]  - 499993 ------------- 100.9.0.41 ------------- pwd 

19:14:54,359 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(589)]  - 499994 ------------- 100.9.0.41 ------------- ls 

19:14:54,359 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(589)]  - 499995 ------------- 100.9.0.41 ------------- ll 

19:14:54,359 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(589)]  - 499996 ------------- 100.9.0.41 ------------- cd /tmp 

19:14:54,359 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(589)]  - 499997 ------------- 100.9.0.41 ------------- ls -lt ylDisClaimCaseGetByCond* 

19:14:54,359 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(589)]  - 499998 ------------- 100.9.0.41 ------------- cat ylDisClaimCaseGetByCond6245 

19:14:54,359 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(589)]  - 499999 ------------- 100.9.0.41 ------------- cat ylDisClaimCaseGetByCond6246 

19:14:54,359 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(589)]  - 500000 ------------- 100.9.0.41 ------------- cat ylDisClaimCaseGetByCond6244 

19:14:54,359 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(594)]  - ***Using BookListHandler***PageArgument{curPage=100000, pageSize=10, totalRow=0, totalPage=1}***no params
19:14:58,578 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(599)]  - PageArgument{curPage=100000, pageSize=10, totalRow=1336677, totalPage=133668}
19:14:58,578 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(601)]  - id ------------- title ------------- authors 
19:14:58,578 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(603)]  - 999991 ------------- 100.9.0.41 ------------- rm libcbps_8605_stc.so 

19:14:58,578 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(603)]  - 999992 ------------- 100.9.0.41 ------------- tar xvf lib.tar 

19:14:58,578 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(603)]  - 999993 ------------- 100.9.0.41 ------------- chmod 755 * 

19:14:58,578 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(603)]  - 999994 ------------- 100.9.0.41 ------------- cd ../bin 

19:14:58,578 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(603)]  - 999995 ------------- 100.9.0.41 ------------- chmod 755 * 

19:14:58,578 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(603)]  - 999996 ------------- 100.9.0.41 ------------- tmboot -s cbps_8601 

19:14:58,578 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(603)]  - 999997 ------------- 100.9.0.41 ------------- tmshutdown -y 

19:14:58,578 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(603)]  - 999998 ------------- 100.9.0.41 ------------- ^ *Interrupt* Force to shutdown the process? (y/n): ^ CMDTUX_CAT:790: INFO: *Interrupt* Want to Continue? (y/n):^CMDTUX_CAT:945: WARN: Server was shutdown for migration  tmshutdown: WARN: internal error: CMDTUX_CAT:947: WARN: Can't shutdown server (DB_BIZ1/8950)   CMDTUX_CAT:932: INFO: System Shutdown Canceled 105 processes stopped. CMDTUX_CAT:777: INFO: See ULOG for complete process status $ $ $ $ $ tmipcrm -y 

19:14:58,578 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(603)]  - 999999 ------------- 100.9.0.41 ------------- pwd 

19:14:58,578 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(603)]  - 1000000 ------------- 100.9.0.41 ------------- uncompress *.Z 

19:14:58,578 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(608)]  - ***Using BookListHandler***PageArgument{curPage=133668, pageSize=10, totalRow=0, totalPage=1}***no params
19:15:04,062 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(613)]  - PageArgument{curPage=133668, pageSize=10, totalRow=1336677, totalPage=133668}
19:15:04,062 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(615)]  - id ------------- title ------------- authors 
19:15:04,062 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(617)]  - 1336671 ------------- 100.9.0.41 ------------- cd 

19:15:04,062 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(617)]  - 1336672 ------------- 100.9.0.41 ------------- telnet 100.10.0.109 22 

19:15:04,062 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(617)]  - 1336673 ------------- 100.9.0.41 ------------- quit 

19:15:04,062 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(617)]  - 1336674 ------------- 100.9.0.41 ------------- exit 

19:15:04,062 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(617)]  - 1336675 ------------- 100.9.0.41 ------------- vgdisplay|more 

19:15:04,062 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(617)]  - 1336676 ------------- 100.9.0.41 ------------- vgdisplay -v /dev/vgsxmirr|more 

19:15:04,062 [main] DEBUG [dbutilsi.QueryRunnerTest$LogListHandlerTester.main(617)]  - 1336677 ------------- 100.9.0.41 ------------- vgdisplay -v /dev/vgsxmirr 

*/


/*
1. String length not big than 4000 BookUpdateTester setString(): 
14:09:15,671 [main] DEBUG [ConfigUtil.getConfigName(35)]  - /D:/yworkspace/ra_zgrs/ra/WEB-INF/classes/com/ultrapower/ra/config/db.properties
book.getDescribe().length()=3900
2009-07-28 14:09:16


2. String length big than 4000 BookUpdateTester setString() : 
14:07:53,015 [main] DEBUG [ConfigUtil.getConfigName(35)]  - /D:/yworkspace/ra_zgrs/ra/WEB-INF/classes/com/ultrapower/ra/config/db.properties
book.getDescribe().length()=5200
2009-07-28 14:07:54
com.ultrapower.ra.dao.DaoException: java.sql.SQLException: ORA-01461: 仅能绑定要插入 LONG 列的 LONG 值

    at com.ultrapower.ra.common.db.QueryRunnerTest$BookUpdateTester.add(QueryRunnerTest.java:389)
    at com.ultrapower.ra.common.db.QueryRunnerTest$BookUpdateTester.main(QueryRunnerTest.java:357)
Caused by: java.sql.SQLException: ORA-01461: 仅能绑定要插入 LONG 列的 LONG 值

    at oracle.jdbc.driver.SQLStateMapping.newSQLException(SQLStateMapping.java:70)
    at oracle.jdbc.driver.DatabaseError.newSQLException(DatabaseError.java:110)
    at oracle.jdbc.driver.DatabaseError.throwSqlException(DatabaseError.java:171)
    at oracle.jdbc.driver.T4CTTIoer.processError(T4CTTIoer.java:455)
    at oracle.jdbc.driver.T4CTTIoer.processError(T4CTTIoer.java:413)
    at oracle.jdbc.driver.T4C8Oall.receive(T4C8Oall.java:1030)
    at oracle.jdbc.driver.T4CPreparedStatement.doOall8(T4CPreparedStatement.java:194)
    at oracle.jdbc.driver.T4CPreparedStatement.executeForRows(T4CPreparedStatement.java:947)
    at oracle.jdbc.driver.OracleStatement.doExecuteWithTimeout(OracleStatement.java:1222)
    at oracle.jdbc.driver.OraclePreparedStatement.executeInternal(OraclePreparedStatement.java:3381)
    at oracle.jdbc.driver.OraclePreparedStatement.executeUpdate(OraclePreparedStatement.java:3462)
    at oracle.jdbc.driver.OraclePreparedStatementWrapper.executeUpdate(OraclePreparedStatementWrapper.java:1061)
    at com.ultrapower.ra.common.db.QueryRunnerTest$BookUpdateTester.add(QueryRunnerTest.java:387)
    ... 1 more


3. String length big than 4000 BookUpdateTester setCharacterStream() : 
14:01:30,546 [main] DEBUG [ConfigUtil.getConfigName(35)]  - /D:/yworkspace/ra_zgrs/ra/WEB-INF/classes/com/ultrapower/ra/config/db.properties
book.getDescribe().length()=5200
2009-07-28 14:01:32
messageBytesLength)=5200
com.ultrapower.ra.dao.DaoException: java.sql.SQLException: ORA-01461: 仅能绑定要插入 LONG 列的 LONG 值

    at com.ultrapower.ra.common.db.QueryRunnerTest$BookUpdateTester.add(QueryRunnerTest.java:389)
    at com.ultrapower.ra.common.db.QueryRunnerTest$BookUpdateTester.main(QueryRunnerTest.java:357)
Caused by: java.sql.SQLException: ORA-01461: 仅能绑定要插入 LONG 列的 LONG 值

    at oracle.jdbc.driver.SQLStateMapping.newSQLException(SQLStateMapping.java:70)
    at oracle.jdbc.driver.DatabaseError.newSQLException(DatabaseError.java:110)
    at oracle.jdbc.driver.DatabaseError.throwSqlException(DatabaseError.java:171)
    at oracle.jdbc.driver.T4CTTIoer.processError(T4CTTIoer.java:455)
    at oracle.jdbc.driver.T4CTTIoer.processError(T4CTTIoer.java:413)
    at oracle.jdbc.driver.T4C8Oall.receive(T4C8Oall.java:1030)
    at oracle.jdbc.driver.T4CPreparedStatement.doOall8(T4CPreparedStatement.java:194)
    at oracle.jdbc.driver.T4CPreparedStatement.executeForRows(T4CPreparedStatement.java:947)
    at oracle.jdbc.driver.OracleStatement.doExecuteWithTimeout(OracleStatement.java:1222)
    at oracle.jdbc.driver.OraclePreparedStatement.executeInternal(OraclePreparedStatement.java:3381)
    at oracle.jdbc.driver.OraclePreparedStatement.executeUpdate(OraclePreparedStatement.java:3462)
    at oracle.jdbc.driver.OraclePreparedStatementWrapper.executeUpdate(OraclePreparedStatementWrapper.java:1061)
    at com.ultrapower.ra.common.db.QueryRunnerTest$BookUpdateTester.add(QueryRunnerTest.java:387)
    ... 1 more

*/

/*
BookListHandler

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
18 ------------- p ------------- p of authors modify
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
>>>generateCountSql sql=select id, title, authors from book order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver
<<<generateCountSql result.toString()=select count(*) from book order by id
>>>generatePageSql sql=select id, title, authors from book order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver, pageArg=PageArgument{curPage=1, pageSize=5, totalRow=28, totalPage=6}
<<<generatePageSql pageSql=select *
from (select temp.*, rownum row_num 
      from (select id, title, authors from book order by id) temp where rownum <= 5
) where row_num >= 1
id ------------- title ------------- authors 
1 ------------- c++ primer plus ------------- duck
2 ------------- thinking in java ------------- bruce eckel
3 ------------- a ------------- a of authors
4 ------------- b ------------- b of authors
5 ------------- c ------------- c of authors
***Using BookListHandler***PageArgument(1, 5)***params{5}
>>>generateCountSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver
<<<generateCountSql result.toString()=select count(*) from book where id>? order by id
>>>generatePageSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver, pageArg=PageArgument{curPage=1, pageSize=5, totalRow=23, totalPage=5}
<<<generatePageSql pageSql=select *
from (select temp.*, rownum row_num 
      from (select id, title, authors from book where id>? order by id) temp where rownum <= 5
) where row_num >= 1
id ------------- title ------------- authors 
6 ------------- d ------------- d of authors
7 ------------- e ------------- e of authors
8 ------------- f ------------- f of authors
9 ------------- g ------------- g of authors
10 ------------- h ------------- h of authors
***Using BookListHandler***PageArgument(1, 5)***params{5555}
>>>generateCountSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver
<<<generateCountSql result.toString()=select count(*) from book where id>? order by id
>>>generatePageSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver, pageArg=PageArgument{curPage=1, pageSize=5, totalRow=0, totalPage=1}
<<<generatePageSql pageSql=select *
from (select temp.*, rownum row_num 
      from (select id, title, authors from book where id>? order by id) temp where rownum <= 5
) where row_num >= 1
id ------------- title ------------- authors 
***Using BookListHandler***PageArgument(5, 5)***params{5}
>>>generateCountSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver
<<<generateCountSql result.toString()=select count(*) from book where id>? order by id
>>>generatePageSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver, pageArg=PageArgument{curPage=5, pageSize=5, totalRow=23, totalPage=5}
<<<generatePageSql pageSql=select *
from (select temp.*, rownum row_num 
      from (select id, title, authors from book where id>? order by id) temp where rownum <= 25
) where row_num >= 21
id ------------- title ------------- authors 
26 ------------- x ------------- x of authors
27 ------------- y ------------- y of authors
28 ------------- z ------------- z of authors
***Using BookListHandler***PageArgument(50, 5)***params{5}
>>>generateCountSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver
<<<generateCountSql result.toString()=select count(*) from book where id>? order by id
>>>generatePageSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver, pageArg=PageArgument{curPage=5, pageSize=5, totalRow=23, totalPage=5}
<<<generatePageSql pageSql=select *
from (select temp.*, rownum row_num 
      from (select id, title, authors from book where id>? order by id) temp where rownum <= 25
) where row_num >= 21
id ------------- title ------------- authors 
26 ------------- x ------------- x of authors
27 ------------- y ------------- y of authors
28 ------------- z ------------- z of authors
***Using BookListHandler***PageArgument(50, 5)***params{23}
>>>generateCountSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver
<<<generateCountSql result.toString()=select count(*) from book where id>? order by id
>>>generatePageSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver, pageArg=PageArgument{curPage=1, pageSize=5, totalRow=5, totalPage=1}
<<<generatePageSql pageSql=select *
from (select temp.*, rownum row_num 
      from (select id, title, authors from book where id>? order by id) temp where rownum <= 5
) where row_num >= 1
id ------------- title ------------- authors 
24 ------------- v ------------- v of authors
25 ------------- w ------------- w of authors
26 ------------- x ------------- x of authors
27 ------------- y ------------- y of authors
28 ------------- z ------------- z of authors
***Using BookListHandler***PageArgument(50, 5)***params{27}
>>>generateCountSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver
<<<generateCountSql result.toString()=select count(*) from book where id>? order by id
>>>generatePageSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver, pageArg=PageArgument{curPage=1, pageSize=5, totalRow=1, totalPage=1}
<<<generatePageSql pageSql=select *
from (select temp.*, rownum row_num 
      from (select id, title, authors from book where id>? order by id) temp where rownum <= 5
) where row_num >= 1
id ------------- title ------------- authors 
28 ------------- z ------------- z of authors
***Using BookListHandler***PageArgument(50, 5)***params{5555}
>>>generateCountSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver
<<<generateCountSql result.toString()=select count(*) from book where id>? order by id
>>>generatePageSql sql=select id, title, authors from book where id>? order by id, conn.getMetaData().getDriverName()=Oracle JDBC driver, pageArg=PageArgument{curPage=1, pageSize=5, totalRow=0, totalPage=1}
<<<generatePageSql pageSql=select *
from (select temp.*, rownum row_num 
      from (select id, title, authors from book where id>? order by id) temp where rownum <= 5
) where row_num >= 1
id ------------- title ------------- authors 

*/

/*
14:48:48,031 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(647)]  - ***Using SystemLogMapListHandler***PageArgument{curPage=1, pageSize=10, totalRow=0, totalPage=1}***[Ljava.lang.Object;[{2009-12-22 13:04:04,2009-12-23 13:04:04}]
>>>generateCountSql sql=select * from ams_system_log t where t.opertime >= ? and t.opertime <= ? order by t.opertime desc, conn.getMetaData().getDriverName()=Oracle JDBC driver
<<<generateCountSql result.toString()=select count(*) from ams_system_log t where t.opertime >= ? and t.opertime <= ? 
>>>generatePageSql sql=select * from ams_system_log t where t.opertime >= ? and t.opertime <= ? order by t.opertime desc, conn.getMetaData().getDriverName()=Oracle JDBC driver, pageArg=PageArgument{curPage=1, pageSize=10, totalRow=344, totalPage=35}
<<<generatePageSql pageSql=select *
from (select temp.*, rownum row_num 
      from (select * from ams_system_log t where t.opertime >= ? and t.opertime <= ? order by t.opertime desc) temp where rownum <= 10
) where row_num >= 1
14:48:48,390 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(652)]  - PageArgument{curPage=1, pageSize=10, totalRow=344, totalPage=35}
14:48:48,390 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(654)]  - id ------------- username ------------- operation 
14:48:48,390 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 7428153 ------------- 鄂鹏 ------------- 登录系统
14:48:48,390 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3287890 ------------- 鄂鹏 ------------- 登录系统
14:48:48,390 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 7467014 ------------- 刘锦淼 ------------- 登录系统
14:48:48,390 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3287889 ------------- 刘锦淼 ------------- 登录系统
14:48:48,390 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 7428152 ------------- 刘锦淼 ------------- 登录系统
14:48:48,390 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3287888 ------------- 刘锦淼 ------------- 登录系统
14:48:48,390 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3287886 ------------- sysadmin ------------- 修改
14:48:48,390 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 7432401 ------------- sysadmin ------------- 修改
14:48:48,390 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 3287885 ------------- sysadmin ------------- 修改
14:48:48,390 [main] DEBUG [dbutilsi.QueryRunnerTest$SystemLogMapListHandler.main(656)]  - 7432400 ------------- sysadmin ------------- 修改


*/
