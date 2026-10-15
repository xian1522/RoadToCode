package sql;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

import java.util.List;

public class Main {

    public static void main(String[] args) throws JSQLParserException {

        String sql = "SELECT\n" +
                "\tCOMPONDEAL.*,\n" +
                "\tSV.SDPRICE,\n" +
                "\tCASE\n" +
                "\t\tWHEN COUNTERTYPE = 'VIRTUAL' THEN COMPONDEAL.AMT * NVL(SV.SDPRICE, 0)\n" +
                "\t\tELSE COMPONDEAL.FULLVALUE\n" +
                "\tEND FULLVALUE,\n" +
                "\tTS.ISSUESUBJECT1 SECTYPE,\n" +
                "\tSV.ENYEARS,\n" +
                "\tOSCM.CREDITCODE,\n" +
                "\tTS.SEQNO\n" +
                "FROM\n" +
                "\t(\n" +
                "\tSELECT\n" +
                "\t\tVIR.SECID,\n" +
                "\t\t'T01' TRADETYPE,\n" +
                "\t\t'VIRTUAL' COUNTERTYPE,\n" +
                "\t\tVIR.CDATE TRADEDATE,\n" +
                "\t\tVIR.SELFAMT AMT,\n" +
                "\t\tNULL DPRICE,\n" +
                "\t\tNULL FULLVALUE\n" +
                "\tFROM\n" +
                "\t\tV_SECPOSITION VIR\n" +
                "\tWHERE\n" +
                "\t\tVIR.CDATE IN (date'2026-01-01'-1, date'2026-02-01')\n" +
                "UNION ALL\n" +
                "\tSELECT\n" +
                "\t\tDEAL.SECID,\n" +
                "\t\tCASE\n" +
                "\t\t\tWHEN DEAL.PS = 'P' THEN 'T01'\n" +
                "\t\t\tELSE 'T02'\n" +
                "\t\tEND,\n" +
                "\t\t'REAL' COUNTERTYPE,\n" +
                "\t\tDEAL.CLRDATE,\n" +
                "\t\tDEAL.PRINAMT,\n" +
                "\t\tDEAL.DPRICE,\n" +
                "\t\tDEAL.CLRAMT FULLVALUE\n" +
                "\tFROM\n" +
                "\t\tODS_SEC_SECDEAL DEAL\n" +
                "\tWHERE\n" +
                "\t\tDEAL.CLRDATE >= date'2026-01-01'\n" +
                "\t\tAND DEAL.CLRDATE <= date'2026-01-01'\n" +
                "\t\tAND DEAL.EFFECTFLAG = 'E' )COMPONDEAL\n" +
                "LEFT JOIN ODS_TGT_SECINFO TS ON\n" +
                "\tCOMPONDEAL.SECID = TS.SECID\n" +
                "\tAND TS.EFFECTFLAG = 'E'\n" +
                "LEFT JOIN ODS_SEC_VALUATION SV ON\n" +
                "\tCOMPONDEAL.SECID = SV.SECID\n" +
                "\tAND COMPONDEAL.TRADEDATE = SV.CDATE\n" +
                "LEFT JOIN ODS_TGT_SECISSUE OTS ON\n" +
                "\tTS.SEQNO = OTS.SEQNO\n" +
                "LEFT JOIN ODS_SYS_CPY_MAIN_ORG OSCM ON\n" +
                "\tOTS.CPYID = OSCM.ORGCODE\n" +
                "\tAND OSCM.EFFECTFLAG = 'E'\n" +
                "UNION ALL\n" +
                "SELECT\n" +
                "\tREPAY.SECID,\n" +
                "\t'T08',\n" +
                "\t'REAL',\n" +
                "\tREPAY.IPAYDATE,\n" +
                "\tNULL,\n" +
                "\tNULL,\n" +
                "\tREPAY.RETURNAMT,\n" +
                "\tNULL,\n" +
                "\tNULL,\n" +
                "\tNULL,\n" +
                "\tNULL,\n" +
                "\tNULL,\n" +
                "\tNULL\n" +
                "FROM\n" +
                "\tODS_SEC_SCHEDULE REPAY\n" +
                "WHERE\n" +
                "\tREPAY.IPAYDATE >= date'2026-01-01'\n" +
                "\tAND REPAY.IPAYDATE <= date'2026-01-01'\n" +
                "\tAND REPAY.RETURNAMT > 0\n" +
                "UNION ALL\n" +
                "SELECT\n" +
                "\tREPAY.SECID,\n" +
                "\t'T09',\n" +
                "\t'REAL',\n" +
                "\tREPAY.IPAYDATE,\n" +
                "\tNULL,\n" +
                "\tNULL,\n" +
                "\tREPAY.ACTINTPAYAMT,\n" +
                "\tNULL,\n" +
                "\tNULL,\n" +
                "\tNULL,\n" +
                "\tNULL,\n" +
                "\tNULL,\n" +
                "\tNULL\n" +
                "FROM\n" +
                "\tODS_SEC_SCHEDULE REPAY\n" +
                "WHERE\n" +
                "\tREPAY.IPAYDATE >= date'2026-01-01'\n" +
                "\tAND REPAY.IPAYDATE <= date'2026-01-01'\n" +
                "\tAND REPAY.ACTINTPAYAMT > 0";

        FormatVisitor formatVisitor = new FormatVisitor(sql);
        List<SqlFormatter> sqlFormatters = formatVisitor.visitorSql();
        System.out.println(formatVisitor.getFormattedSql(sqlFormatters));
    }
}
