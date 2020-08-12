package com.wisdomconstruction.wisdomConstruction.dbconf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@Slf4j
public class DBInitializeConfig {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initialize() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            initTableUserCommunicationInfo(statement);
            initTableUserBankCardInfo(statement);
            initTableCreditApply(statement);
        } catch (SQLException e) {
            log.warn("db connection exception.", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                }catch (SQLException e1){
                    log.warn("db statement close exception.");
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                }catch (SQLException e2){
                    log.warn("db connection close exception.");
                    throw e2;
                }
            }
        }
    }

    private void initTableUserCommunicationInfo(Statement stmt) throws SQLException {
        stmt.execute("DROP TABLE IF EXISTS user_communication_info");
        stmt.executeUpdate(
                "create table user_communication_info(" + "   id                   bigint auto_increment primary key," + "   subuser_no           bigint not null ," + "   enterprise_code      bigint not null ," + "   user_commuinfo_type  tinyint ," + "   user_commuinfo_status tinyint ," + "   user_commuinfo_detail varchar(2000) ," + "   is_default           tinyint ," + "   is_delete            tinyint ," + "   gmt_create           timestamp ," + "   gmt_modified         timestamp ," + "   creator              varchar(30) ," + "   modifier             varchar(30) ) ");

    }

    private void initTableUserBankCardInfo(Statement stmt) throws SQLException {
        stmt.execute("DROP TABLE IF EXISTS user_bankcard_info");
        stmt.executeUpdate(
                "create table user_bankcard_info(" + "id                   bigint auto_increment primary key," + "subuser_no           bigint not null ," + "enterprise_code      bigint not null ," + "bk_code              varchar(10) ," + "bk_name              varchar(60) ," + "bk_acc_name          varchar(120) ," + "bk_acc_no            varchar(120) ," + "deadline             varchar(18) ," + "bk_bind_mobile_no    varchar(18) ," + "is_default           tinyint ," + "balance              decimal(18,6) ," + "bind_type            tinyint ," + "bk_card_type         tinyint ," + "account_category     tinyint ," + "is_delete            tinyint ," + "gmt_create           timestamp ," + "gmt_modified         timestamp ," + "creator              varchar(30) ," + "modifier             varchar(30) )");
    }

    private void initTableCreditApply(Statement stmt) throws SQLException {
        stmt.execute("DROP TABLE IF EXISTS credit_apply");
        stmt.executeUpdate(
                "CREATE TABLE credit_apply(" + " `id` bigint(20) NOT NULL," + " `enterprise_code` bigint(20) NOT NULL , " + " `subuser_no` bigint(20) NOT NULL , " + " `prod_id` bigint(20) NOT NULL , " + " `channel_serial_id` VARCHAR(30) NOT NULL , " + " `credit_id` bigint(20) , " + " `apply_date` timestamp NOT NULL , " + " `credit_status` tinyint(2) DEFAULT '1' , " + " `compensation_number` tinyint(2) DEFAULT '0' , " + " `creator`              varchar(30) , " + " `modifier`             varchar(30) , " + " `is_delete` tinyint(4) DEFAULT '0' , " + " `gmt_create` timestamp  , " + " `gmt_modified` timestamp  , " + " PRIMARY KEY (`id`) )");
    }
}
