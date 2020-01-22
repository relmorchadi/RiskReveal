package com.scor.rr.util;

public class EmbeddedQueries {

    public static final String TIV_QUERY = "SELECT\tRegionPerilCode as RegionPerilCode\n" +
            "\t\t\t,\tSUM( tiv.LocCount ) AS LocCount\n" +
            "\t\t\t,\tSUM( tiv.TotalTiv ) AS TotalTIV\n" +
            "\t\tFROM\t(\n" +
            "\t\t\t\tSELECT\tDISTINCT\n" +
            "\t\t\t\t\t\tcoalesce( map.RegionPerilCode , map2.RegionPerilCode) as RegionPerilCode\n" +
            "\t\t\t\t\t,\ttiv.*\n" +
            "\t\t\t\tFROM\t(\n" +
            "\t\t\t\t\t\t----------------------------------------------------------------------\n" +
            "\t\t\t\t\t\t-- NEW RAW REGIION PERIL LEVEL TIV & LOC COUNT EXTRACT\n" +
            "\t\t\t\t\t\t----------------------------------------------------------------------\n" +
            "\t\t\t\t\t\tSELECT  CONVERT( VARCHAR( 1000 ), t.CountryCode )   AS CountryCode\n" +
            "\t\t\t\t\t\t\t ,  CONVERT( VARCHAR( 1000 ), t.AdminCode1 )    AS Admin1Code\n" +
            "\t\t\t\t\t\t\t ,  CONVERT( VARCHAR( 1000 ), t.Peril )         AS Peril\n" +
            "\t\t\t\t\t\t\t ,  SUM( t.TIV_LIMITCCY )                       AS TotalTIV\n" +
            "\t\t\t\t\t\t\t ,  COUNT( DISTINCT LOCNUM )                    AS LocCount\n" +
            "\t\t\t\t\t\tFROM    (\n" +
            "\t\t\t\t\t\t\t\tSELECT CASE WHEN adr.CountryRMSCode = 'CB'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t THEN adr.CountryCode\n" +
            "\t\t\t\t\t\t\t\t\t\t\t ELSE adr.CountryRMSCode\n" +
            "\t\t\t\t\t\t\t\t\t\tEND\t\t\t\t\t\t\t\t\tAS CountryCode\n" +
            "\t\t\t\t\t\t\t\t\t ,\tadr.Admin1Code\t\t\t\t\t\tAS AdminCode1\n" +
            "\t\t\t\t\t\t\t\t\t ,  lcv.PERIL\n" +
            "\t\t\t\t\t\t\t\t\t ,  ppt.LOCNUM\n" +
            "\t\t\t\t\t\t\t\t\t ,  ppt.LOCNAME\n" +
            "\t\t\t\t\t\t\t\t\t ,  limfx.CODE                          AS CcyCode\n" +
            "\t\t\t\t\t\t\t\t\t ,\n" +
            "\t\t\t\t\t\t\t\t\t  MAX\n" +
            "\t\t\t\t\t\t\t\t\t\t( lcv.VALUEAMT\n" +
            "\t\t\t\t\t\t\t\t\t\t   * (LocFX.XFACTOR * (1/LimFX.XFactor))\n" +
            "\t\t\t\t\t\t\t\t\t\t   )\t\t\t\t\t\t\t\tAS TIV_LIMITCCY\n" +
            "\t\t\t\t\t\t\t\tFROM    :edm:.dbo.PortInfo poi\n" +
            "\t\t\t\t\t\t\t\t\t\tINNER JOIN :edm:.dbo.PortAcct pra\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tON  pra.PORTINFOID = poi.PORTINFOID\n" +
            "\t\t\t\t\t\t\t\t\t\tINNER JOIN :edm:.dbo.Property ppt\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tON  ppt.ACCGRPID   = pra.ACCGRPID\n" +
            "\t\t\t\t\t\t\t\t\t\tINNER JOIN :edm:.dbo.Address adr\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tON  adr.AddressID  = ppt.AddressID\n" +
            "\t\t\t\t\t\t\t\t\t\tINNER JOIN (\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t   SELECT  lcv.LocID , lcv.Peril, lcv.ValueCur\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t,  SUM( lcv.VALUEAMT )     AS VALUEAMT\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t   FROM    :edm:.dbo.loccvg lcv\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t   GROUP BY\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t   lcv.LOCID , lcv.PERIL, lcv.ValueCur\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t  ) lcv\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tON lcv.LOCID   = ppt.LOCID\n" +
            "\t\t\t\t\t\t\t\t\t\tINNER JOIN rms_userconfig.dbo.currfx LocFX\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tON  LocFX.CODE     = lcv.VALUECUR\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tAND LocFX.XDATE    = (\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t SELECT  MAX( XDATE )\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t FROM    rms_userconfig.dbo.currfx LocFX\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t WHERE   CODE = 'USD'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t )\n" +
            "\t\t\t\t\t\t\t\t\t\tINNER JOIN rms_userconfig.dbo.currfx LimFX\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tON  LimFX.CODE     = ?\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tAND LimFX.XDATE    = (\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t SELECT  MAX( XDATE )\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t FROM    rms_userconfig.dbo.currfx LocFX\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t WHERE   CODE = 'USD'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t )\n" +
            "\t\t\t\t\t\t\t\tWHERE   poi.PORTNUM = ?\n" +
            "\t\t\t\t\t\t\t\tAND\t\tlcv.PERIL < 5\n" +
            "\t\t\t\t\t\t\t\tGROUP BY\n" +
            "\t\t\t\t\t\t\t\t\t\tCASE WHEN adr.CountryRMSCode = 'CB'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t THEN adr.CountryCode\n" +
            "\t\t\t\t\t\t\t\t\t\t\t ELSE adr.CountryRMSCode\n" +
            "\t\t\t\t\t\t\t\t\t\tEND\n" +
            "\t\t\t\t\t\t\t\t\t,\tadr.Admin1Code\n" +
            "\t\t\t\t\t\t\t\t\t,   lcv.PERIL\n" +
            "\t\t\t\t\t\t\t\t\t,   ppt.LOCNUM\n" +
            "\t\t\t\t\t\t\t\t\t,   ppt.LOCNAME\n" +
            "\t\t\t\t\t\t\t\t\t,   limfx.CODE\n" +
            "\t\t\t\t\t\t\t\t) t\n" +
            "\t\t\t\t\t\tGROUP BY\n" +
            "\t\t\t\t\t\t\t\tCONVERT( VARCHAR( 1000 ),  t.CountryCode  )\n" +
            "\t\t\t\t\t\t\t,\tCONVERT( VARCHAR( 1000 ),  t.AdminCode1  )\n" +
            "\t\t\t\t\t\t\t,   CONVERT( VARCHAR( 1000 ), t.Peril )\n" +
            "\t\t\t\t\t\t) tiv\n" +
            "\t\t\t\t\tLEFT  JOIN SCOR_REFERENCE.dbo.RegionPerilMapping map\n" +
            "\t\t\t\t\t\t\tON\tmap.SourceVendor\t\t= 'RMS'\n" +
            "\t\t\t\t\t\t\tAND\tmap.PerilCode\t\t\t= tiv.Peril\n" +
            "\t\t\t\t\t\t\tAND RTRIM(map.CountryCode)  = RTRIM(tiv.CountryCode)\n" +
            "\t\t\t\t\t\t\tAND RTRIM(map.Admin1Code)   = RTRIM(tiv.Admin1Code)\n" +
            "\t\t\t\t\tLEFT  JOIN SCOR_REFERENCE.dbo.RegionPerilMapping map2\n" +
            "\t\t\t\t\t\t\tON\tmap2.SourceVendor\t\t= 'RMS'\n" +
            "\t\t\t\t\t\t\tAND\tmap2.PerilCode\t\t\t= RTRIM(Convert(Varchar(10),tiv.Peril))\n" +
            "\t\t\t\t\t\t\tAND RTRIM(map2.CountryCode) = RTRIM(tiv.CountryCode)\n" +
            "\t\t\t\t\t\t\tAND RTRIM(map2.Admin1Code)  = ''\n" +
            "\t\t\t\t) tiv\n" +
            "\t\tGROUP BY\n" +
            "\t\t\t\tRegionPerilCode";


    public static final String REGION_PERIL_QUERY = "SELECT   an.ID              AS ANALYSIS_ID\n" +
            "    ,    av.ANALYSIS_COUNT  AS ANALYSIS_VERSION\n" +
            "    ,    an.NAME            AS PORTFOLIO_NUM\n" +
            "    ,    an.EXPOSUREID      AS PORTFOLIO_ID\n" +
            "    ,    an.DESCRIPTION     AS DLM_PROFILE_NAME\n" +
            "    ,    an.CURR            AS CURRENCY\n" +
            "    ,    an.RUNDATE         AS ANALYSIS_RUN_DATE\n" +
            "    ,    an.PERIL           AS PERIL\n" +
            "    ,    an.REGION          AS REGION\n" +
            "    ,    rap.Scope\n" +
            "    ,    rap.MutuallyExclusiveID\n" +
            "FROM     :rdm:.dbo.rdm_analysis an\n" +
            "         INNER JOIN SCOR_REFERENCE.dbo.RiskAnalysisProfileInfo rap\n" +
            "\t           ON  rap.ProfileName  \t= an.Description\n" +
            "\t   \t     AND rap.Scope   \t \t= 'Account'\n" +
            "\t   INNER JOIN  (\n" +
            "\t\t\t    SELECT\ta.NAME\n" +
            "\t\t\t\t  ,\ta.DESCRIPTION\n" +
            "\t\t\t\t  ,\tMAX(a.RUNDATE)\tAS LATEST_RUN_DATE\n" +
            "\t\t\t\t  ,\tCOUNT(1)\t\tAS ANALYSIS_COUNT\n" +
            "\t\t\t    FROM\t:rdm:.dbo.rdm_analysis a\n" +
            "\t\t\t    WHERE\ta.TYPE = 102 -- EP\n" +
            "\t\t\t    AND\ta.MODE = 2 -- Distributed\n" +
            "\t\t\t    AND\ta.STATUS = 102 -- Analyzed\n" +
            "\t\t\t    AND\ta.EXPOSURETYPE = 8017 -- Portfolio or group\n" +
            "\t\t\t    AND\ta.GROUPTYPE = 'ANLS' -- Analysis\n" +
            "\t\t\t    AND\ta.ENGINETYPE = 100 -- DLM\n" +
            "                      GROUP BY\n" +
            "                             a.NAME\n" +
            "                         ,   a.DESCRIPTION\n" +
            "\t              ) av\n" +
            "\t\t    ON  av.NAME = an.NAME\n" +
            "\t\t    AND av.LATEST_RUN_DATE = an.RUNDATE\n" +
            "\t\t    AND av.DESCRIPTION = an.DESCRIPTION\n" +
            "WHERE\t  an.ID = ? -- Analysis Id.\n" +
            "AND\t  an.TYPE = 102 -- EP\n" +
            "AND\t  an.MODE = 2 -- Distributed\n" +
            "AND\t  an.STATUS = 102 -- Analyzed\n" +
            "AND\t  an.EXPOSURETYPE = 8017 -- Portfolio or group\n" +
            "AND\t  an.GROUPTYPE = 'ANLS' -- Analysis\n" +
            "AND\t  an.ENGINETYPE = 100 -- DLM\n" +
            "AND\t  EXISTS (\n" +
            "\t     \t    SELECT\t1\n" +
            "\t\t    FROM\t:rdm:.dbo.rdm_portstats ps\n" +
            "\t\t    WHERE\tps.ANLSID = an.ID\n" +
            "\t\t    AND     ps.PERSPCODE = 'GU'\n" +
            "\t         )\n" +
            "ORDER BY an.ID";

    public static final String RL_ACC_QUERY_PROC = "Exec @database.dbo.RR_RL_GetEdmExtractACCFile @edm_name= ?, @portnum= ?";
    public static final String RL_LOC_QUERY_PROC = "Exec @database.dbo.RR_RL_GetEdmExtractLOCFile @edm_name= ?, @rdm_name= ?, @portnum= ?, @ccy_code= ?";
}
